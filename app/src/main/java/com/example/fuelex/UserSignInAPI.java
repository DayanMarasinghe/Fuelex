package com.example.fuelex;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserSignInAPI {
    @POST("signup")

    Call<UserSignInModel> userSignUp(@Body UserSignInModel userSignInModel);
}
