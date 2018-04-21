package hu.webandmore.androidmocapclient.app.api.services;

import hu.webandmore.androidmocapclient.app.api.model.WiFiModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface WiFiService {

    @GET("status")
    Call<WiFiModel> getWiFiStatus();

    @POST("wifi")
    Call<Void> setWiFi(@Query("body") String wiFiModel);

    @POST("save")
    Call<Void> saveWiFi();

    @POST("reconnect")
    Call<Void> reconnnectToWiFi();

}
