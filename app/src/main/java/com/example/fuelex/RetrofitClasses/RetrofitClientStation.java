package com.example.fuelex.RetrofitClasses;

import com.example.fuelex.API.StationApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientStation {
    private static RetrofitClientStation instance = null;
    private StationApi statonApi;

    private RetrofitClientStation(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(statonApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        statonApi = retrofit.create(StationApi.class);
    }

    public static synchronized RetrofitClientStation getInstance(){
        if (instance == null){
            instance = new RetrofitClientStation();
        }
        return instance;
    }

    public StationApi getStatonApi(){
        return statonApi;
    }
}
