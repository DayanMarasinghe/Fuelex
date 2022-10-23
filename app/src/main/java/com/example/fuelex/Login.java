package com.example.fuelex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    EditText usernameTxt;
    EditText passwordTxt;
    ImageView loginbtn;
    String username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        usernameTxt = findViewById(R.id.ownerun);
        passwordTxt = findViewById(R.id.ownerpw);
        loginbtn = (ImageView) findViewById(R.id.loginbutton);

        username = usernameTxt.getText().toString();
        password = passwordTxt.getText().toString();

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // if(username.equals("")){
                   // Toast.makeText(getApplicationContext(), "Please enter username", Toast.LENGTH_SHORT).show();
               // }else if(password.equals("")){
                  //  Toast.makeText(getApplicationContext(), "Please enter password", Toast.LENGTH_SHORT).show();
               // }else{
                    LoginRequest loginRequest = new LoginRequest();
                    loginRequest.setUsername(username);
                    loginRequest.setPassword(password);

                FindCusomter(loginRequest);

               // }
            }
        });

    }

    public void FindCusomter(LoginRequest loginRequest){
        Call<LoginResponse> loginResponseCall = ApiClient.getService().FindCusomter(loginRequest);
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                if(response.isSuccessful()){
                    LoginResponse loginResponse = response.body();
                    startActivity(new Intent(Login.this,StationView.class).putExtra("data",loginResponse));
                    finish();

                }else{
                    String message = "An error occured please try again later...";
                    Toast.makeText(Login.this,message,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

                String message = t.getLocalizedMessage();
                Toast.makeText(Login.this,message,Toast.LENGTH_LONG).show();
            }
        });
    }
}