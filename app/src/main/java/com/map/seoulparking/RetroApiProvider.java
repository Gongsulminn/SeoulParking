package com.map.seoulparking;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by user on 2018-12-06.
 */

public class RetroApiProvider {

    public static ParkRetrofit provideParkApi(){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                                    .addInterceptor(loggingInterceptor)
                                    .readTimeout(60 , TimeUnit.SECONDS)
                                    .build();

        return new Retrofit.Builder()
                .baseUrl(ParkRetrofit.AWS_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
                .create(ParkRetrofit.class);
    }

    public static ParkRetrofit provideSearchApi(){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();

        return new Retrofit.Builder()
                .baseUrl(ParkRetrofit.SEARCH_URL)
                .addConverterFactory(GsonConverterFactory.create())
                //.client(okHttpClient)
                .build()
                .create(ParkRetrofit.class);
    }

    public static ParkRetrofit provideLocationApi(){
        return new Retrofit.Builder()
                .baseUrl(ParkRetrofit.AWS_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ParkRetrofit.class);
    }
}
