package hu.webandmore.androidmocapclient.app.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

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
    private static String MOCKUP_ADDRESS = "192.168.1.74";
    private static String MOCKUP_AP_ADDRESS = "192.168.4.1";

    private static final String AP_MODE = "AP_MODE";

    public static <S> S createService(Context ctx, Class<S> serviceClass){


        OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();

        httpBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder requestBuilder = chain.request().newBuilder()
                        .header("Content-Type", "application/json")
                        .header("Accept", "application/json");

                return chain.proceed(requestBuilder.build());
            }
        });

        String url = "http://" + MOCKUP_ADDRESS + "/api/";
        /*if(getApMode(ctx)) {
            url = "http://" + MOCKUP_AP_ADDRESS + "/api/";
        }*/


        Retrofit retrofit = new Retrofit.Builder().baseUrl(url).client(httpBuilder.build())
                .addConverterFactory(GsonConverterFactory.create()).build();

        return retrofit.create(serviceClass);
    }

    private static boolean getApMode(Context ctx)
    {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(ctx);
        return pref.getBoolean(AP_MODE, false);
    }

    public static void isApMode(Context ctx, boolean val)
    {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(ctx);
        SharedPreferences.Editor edit = pref.edit();

        edit.putBoolean(AP_MODE, val);
        edit.apply();
    }

}
