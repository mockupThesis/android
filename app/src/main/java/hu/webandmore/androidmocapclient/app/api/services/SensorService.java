package hu.webandmore.androidmocapclient.app.api.services;

import retrofit2.Call;
import retrofit2.http.POST;

public interface SensorService {

    @POST("reboot")
    Call<Void> rebootSensor();

}
