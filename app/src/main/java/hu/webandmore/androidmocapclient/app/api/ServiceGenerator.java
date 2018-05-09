package hu.webandmore.androidmocapclient.app.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    /***
     * Mockup Suit Address on the WiFi network
     */
    private static String MOCKUP_ADDRESS = "http://192.168.1.74/api/";
    private static String MOCKUP_AP_ADDRESS = "http://192.168.4.1/api/";

    private static Retrofit retrofit;

    private static OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder()
            .addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request.Builder requestBuilder = chain.request().newBuilder()
                            .header("Content-Type", "application/json")
                            .header("Accept", "application/json");

                    return chain.proceed(requestBuilder.build());
                }
            });

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .client(httpBuilder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(MOCKUP_ADDRESS);

    // No need to instantiate this class.
    private ServiceGenerator() {

    }

    public static <S> S createService(Context ctx, Class<S> serviceClass){
        return builder.build().create(serviceClass);
    }

    public static void changeApiBaseUrl(boolean isApMode) {
        if(isApMode) {
            builder = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(MOCKUP_AP_ADDRESS);
        } else {
            builder = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(MOCKUP_ADDRESS);
        }

    }

}
