package com.example.fuelex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.fuelex.Models.UserSignInModel;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignIn extends AppCompatActivity {

    private ProgressBar progressPB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        //set content type
        EditText fullNameET = findViewById(R.id.id_FullName);
        EditText userNameET = findViewById(R.id.id_UserName);
        EditText passwordET = findViewById(R.id.id_Password);
        EditText nicET = findViewById(R.id.id_NIC);
        EditText vehicleTypeET = findViewById(R.id.id_VehicleType);
        EditText vehicleNoET = findViewById(R.id.id_VehicleNo);
        Button signInBtn = findViewById(R.id.id_BtnSignIn);
        //progressPB = findViewById(R.id.id_ProgressBarSignIn);

        //progressPB.setVisibility(View.GONE);

        //configure the on click listenenr
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //input validation
                if(fullNameET.getText().toString().isEmpty() || userNameET.getText().toString().isEmpty() || passwordET.getText().toString().isEmpty() || nicET.getText().toString().isEmpty() || vehicleNoET.getText().toString().isEmpty() || vehicleTypeET.getText().toString().isEmpty()){
                    Toast.makeText(SignIn.this,"Please fill out all the fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                //call the post method
                postData(fullNameET.getText().toString(), userNameET.getText().toString(), passwordET.getText().toString(), nicET.getText().toString(), vehicleNoET.getText().toString(), vehicleTypeET.getText().toString());

            }
        });
    }

    private void postData(String fullName, String userName, String password, String nic, String vehiceType, String vehicleNo){
        //display the progress bar
        //progressPB.setVisibility(View.VISIBLE);

        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.connectTimeout(10, TimeUnit.SECONDS);
        client.readTimeout(10, TimeUnit.SECONDS);
        client.writeTimeout(10, TimeUnit.SECONDS);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://192.168.8.108:45455/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build();

        UserSignInAPI api = retrofit.create(UserSignInAPI.class);

        UserSignInModel userModel = new UserSignInModel(fullName,userName,password,nic,vehiceType,vehicleNo);

        Call<UserSignInModel> call = api.userSignUp(userModel);

        call.enqueue(new Callback<UserSignInModel>() {
            @Override
            public void onResponse(Call<UserSignInModel> call, Response<UserSignInModel> response) {
                Toast.makeText(SignIn.this, "Successfully logged in",Toast.LENGTH_SHORT).show();

                //progressPB.setVisibility(View.GONE);

                //set the global variables vehicle type

                //Go to the fuel find view by an intent and pass the vehicle type
                Intent sendToLocationView = new Intent(SignIn.this, StationView.class);
                sendToLocationView.putExtra("USER_VEHICLE_TYPE", vehiceType);
                startActivity(sendToLocationView);
            }

            @Override
            public void onFailure(Call<UserSignInModel> call, Throwable t) {
                //progressPB.setVisibility(View.GONE);
                Logger logger = Logger.getLogger(SignIn.class.getName());
                logger.info("--------------------------------------------------------------------------------------");
                logger.info(t.toString());
                logger.info(call.toString());
                logger.info("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                Toast.makeText(SignIn.this, "Sign in failed, please try again", Toast.LENGTH_SHORT).show();
            }
        });
    }
}