package com.example.android.architecture.blueprints.todoapp.network;

import com.example.android.architecture.blueprints.todoapp.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Sahar Almohamady on 6/15/2017.
 */

public class NetworkManager {
    private static Retrofit retrofit;

    private static OkHttpClient getClient(String baseUrl) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(BuildConfig.TIMEOUT_DEFAULT_VALUE, TimeUnit.SECONDS)
                .readTimeout(BuildConfig.TIMEOUT_DEFAULT_VALUE, TimeUnit.SECONDS)
                .writeTimeout(BuildConfig.TIMEOUT_DEFAULT_VALUE, TimeUnit.SECONDS);

        return builder.build();
    }

    public static Retrofit getInstance(String baseUrl) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .client(getClient(baseUrl))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}
