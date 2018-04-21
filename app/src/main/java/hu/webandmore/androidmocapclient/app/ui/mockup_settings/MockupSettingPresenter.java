package hu.webandmore.androidmocapclient.app.ui.mockup_settings;

import android.content.Context;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import hu.webandmore.androidmocapclient.R;
import hu.webandmore.androidmocapclient.app.api.ServiceGenerator;
import hu.webandmore.androidmocapclient.app.api.model.WiFiModel;
import hu.webandmore.androidmocapclient.app.interactors.WiFiInteractor;
import hu.webandmore.androidmocapclient.app.interactors.events.WiFiEvent;
import hu.webandmore.androidmocapclient.app.ui.Presenter;
import hu.webandmore.androidmocapclient.app.utils.WiFiEventType;

public class MockupSettingPresenter extends Presenter<MockupSettingsScreen> {

    private static String TAG = "MockupSettingPresenter";

    private Executor networkExecutor;
    private Context context;

    private WiFiInteractor wiFiInteractor;

    public MockupSettingPresenter(Context context) {
        this.context = context;
        networkExecutor = Executors.newFixedThreadPool(1);
        wiFiInteractor = new WiFiInteractor(context);
    }

    @Override
    public void attachScreen(MockupSettingsScreen screen) {
        Log.i(TAG, "attach Screen");
        super.attachScreen(screen);
    }

    @Override
    public void detachScreen() {
        super.detachScreen();
    }

    public void registerWiFiEvent() {
        EventBus.getDefault().register(this);
    }

    public void unRegisterWiFiEvent() {
        EventBus.getDefault().unregister(this);
    }

    public WiFiModel CreateWiFi(String _ssid, String _password) {
        return new WiFiModel(_ssid, _password, false);
    }

    void getWiFiTask() {
        Log.i(TAG, "getWiFiTask");
        screen.showProgressBar();
        networkExecutor.execute(new Runnable() {
            @Override
            public void run() {
                wiFiInteractor.getWiFiStatus();
            }
        });
    }

    void setWiFiTask(final WiFiModel wiFi) {
        Log.i(TAG, "setWiFiTask");
        screen.showProgressBar();
        networkExecutor.execute(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "setWiFiTask run");
                wiFiInteractor.setWiFiSettings(wiFi);
            }
        });
    }

    void saveWifiTask() {
        screen.showProgressBar();
        networkExecutor.execute(new Runnable() {
            @Override
            public void run() {
                wiFiInteractor.saveWiFiSettings();
            }
        });
    }

    void reconnectWiFiTask() {
        screen.showProgressBar();
        networkExecutor.execute(new Runnable() {
            @Override
            public void run() {
                wiFiInteractor.reconnectWiFi();
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(final WiFiEvent event) {
        Log.i(TAG, "onEvent");
        screen.hideProgressBar();
        if (event.getThrowable() != null) {
            Log.i(TAG, "onEvent getThrowable not null");
            event.getThrowable().printStackTrace();
            if (screen != null) {
                Log.i(TAG, "onEvent screen is not null");
                screen.showError(event.getThrowable().getMessage());
                screen.showWiFiFeedback(event.getThrowable().getMessage(), true);
            } else {
                Log.i(TAG, "onEvent screen is null");
            }
        } else {
            Log.i(TAG, "onEvent getThrowable null");
            if (screen != null) {
                System.out.println("STATUSZKÓD: " + event.getCode());
                if (event.getCode() == 200 ) {
                    if(event.getWiFiEventType() == WiFiEventType.GET) {
                        screen.fillWiFiSettings(event.getWiFi());
                        screen.showWiFiFeedback(context.getString(R.string.connected),
                                false);
                    } else if(event.getWiFiEventType() == WiFiEventType.SET) {
                        screen.showWiFiFeedback(context.getString(R.string.wifi_changed),
                                false);
                        screen.saveWiFiSetting();
                    } else if(event.getWiFiEventType() == WiFiEventType.SAVE) {
                        screen.showWiFiFeedback(context.getString(R.string.saved_successfully),
                                false);
                        screen.reconnectWiFi();
                    } else {
                        ServiceGenerator.isApMode(context, false);
                        screen.showWiFiFeedback(context.getString(R.string.saved_successfully),
                                false);
                    }

                } else {
                    Log.i(TAG, "onEvent status code not 200");
                    screen.showError(event.getErrorMessage());
                }
            }
        }
    }

}
