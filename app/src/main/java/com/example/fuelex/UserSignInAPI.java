package com.example.fuelex;

import com.example.fuelex.Models.UserSignInModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserSignInAPI {
    @POST("signup")

    Call<UserSignInModel> userSignUp(@Body UserSignInModel userSignInModel);
}
