package com.example.fuelex;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {

    @POST("api/customer/")
    Call<LoginResponse> FindCusomter(@Body LoginRequest loginRequest);
    //Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);


}
