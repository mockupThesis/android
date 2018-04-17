package hu.webandmore.androidmocapclient.app.api.services;

import hu.webandmore.androidmocapclient.app.api.model.WiFiModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface WiFiService {

    @GET("Status")
    Call<WiFiModel> getWiFiStatus();

    @POST("wifi")
    Call<Void> setWiFi(@Body WiFiModel wiFiModel);

    @POST("save")
    Call<Void> saveWiFi();

    @POST("reconnect")
    Call<Void> reconnnectToWiFi();

    @POST("reboot")
    Call<Void> rebootSensor();

}
