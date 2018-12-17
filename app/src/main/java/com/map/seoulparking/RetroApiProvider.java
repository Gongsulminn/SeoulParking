package com.map.seoulparking;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by user on 2018-12-06.
 */

public class RetroApiProvider {

    public static ParkRetrofit provideTestApi(){
        return new Retrofit.Builder()
                .baseUrl(ParkRetrofit.URL)
                .build()
                .create(ParkRetrofit.class);
    }

    public static ParkRetrofit provideParkApi(){
        return new Retrofit.Builder()
                .baseUrl(ParkRetrofit.AWS_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ParkRetrofit.class);
    }
}
