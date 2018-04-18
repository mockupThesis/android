package hu.webandmore.androidmocapclient.app.interactors;

import android.content.Context;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import hu.webandmore.androidmocapclient.app.api.ServiceGenerator;
import hu.webandmore.androidmocapclient.app.api.model.WiFiModel;
import hu.webandmore.androidmocapclient.app.api.services.WiFiService;
import hu.webandmore.androidmocapclient.app.interactors.events.WiFiEvent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WiFiInteractor {

    private WiFiService wiFiService;
    private Context context;
    private final WiFiEvent wiFiEvent;

    public WiFiInteractor(Context _context) {
        context = _context;
        wiFiEvent = new WiFiEvent();
        this.wiFiService = ServiceGenerator.createService(context, WiFiService.class);
    }

    public void getWiFiStatus() {
        Call<WiFiModel> call = wiFiService.getWiFiStatus();

        call.enqueue(new Callback<WiFiModel>() {
            @Override
            public void onResponse(Call<WiFiModel> call, Response<WiFiModel> response) {
                if(response.isSuccessful()) {
                    wiFiEvent.setCode(response.code());
                    wiFiEvent.setWiFi(response.body());
                } else {
                    wiFiEvent.setCode(response.code());
                    try {
                        wiFiEvent.setErrorMessage(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                EventBus.getDefault().post(wiFiEvent);
            }

            @Override
            public void onFailure(Call<WiFiModel> call, Throwable t) {
                if(t != null) {
                    wiFiEvent.setErrorMessage(t.getLocalizedMessage());
                    wiFiEvent.setThrowable(t);
                    EventBus.getDefault().post(wiFiEvent);
                }
            }
        });
    }

    public void setWiFiSettings(WiFiModel wiFi) {
        Call<Void> call = wiFiService.setWiFi(wiFi);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()) {
                    wiFiEvent.setCode(response.code());
                } else {
                    wiFiEvent.setCode(response.code());
                    try {
                        wiFiEvent.setErrorMessage(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                EventBus.getDefault().post(wiFiEvent);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                if(t != null) {
                    wiFiEvent.setErrorMessage(t.getLocalizedMessage());
                    wiFiEvent.setThrowable(t);
                    EventBus.getDefault().post(wiFiEvent);
                }
            }
        });
    }

    public void saveWiFiSettings() {
        Call<Void> call = wiFiService.saveWiFi();

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()) {
                    wiFiEvent.setCode(response.code());
                } else {
                    wiFiEvent.setCode(response.code());
                    try {
                        wiFiEvent.setErrorMessage(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                EventBus.getDefault().post(wiFiEvent);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                if(t != null) {
                    wiFiEvent.setErrorMessage(t.getLocalizedMessage());
                    wiFiEvent.setThrowable(t);
                    EventBus.getDefault().post(wiFiEvent);
                }
            }
        });
    }

}
