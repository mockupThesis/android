package hu.webandmore.androidmocapclient.app.api;

import android.content.Context;

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
    private static String MOCKUP_ADDRESS = "192.168.4.1";

    public static <S> S createService(Context ctx, Class<S> serviceClass){
        OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();

        httpBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder requestBuilder = chain.request().newBuilder();

                return chain.proceed(requestBuilder.build());
            }
        });

        String url = "http://" + MOCKUP_ADDRESS + "/api/v1/";

        Retrofit retrofit = new Retrofit.Builder().baseUrl(url).client(httpBuilder.build())
                .addConverterFactory(GsonConverterFactory.create()).build();

        return retrofit.create(serviceClass);
    }

}
