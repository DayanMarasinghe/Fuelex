package com.example.fuelex.API;

import com.example.fuelex.Models.StationModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface StationApi {
    String BASE_URL = "https://192.168.8.108:45455/api/";
    @GET("FuelStation")
    Call<List<StationModel>> getStations();
}
