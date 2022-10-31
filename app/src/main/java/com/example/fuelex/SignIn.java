package com.example.fuelex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

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
                postData(fullNameET.getText().toString(), userNameET.getText().toString(), passwordET.getText().toString(), nicET.getText().toString(), vehicleTypeET.getText().toString(), vehicleNoET.getText().toString());

            }
        });
    }

    //create new user account
    private void postData(String fullName, String userName, String password, String nic, String vehiceType, String vehicleNo){
        String url ="http://192.168.8.129:8081/signup";

        HashMap<String, String> body = new HashMap<String, String>();

        body.put("userName",userName);
        body.put("password",password);
        body.put("fullName",fullName);
        body.put("nic",nic);
        body.put("vehicleType",vehiceType);
        body.put("vehicleNumber",vehicleNo);

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,url, new JSONObject(body),
                response -> {
                    try {
                        VolleyLog.v("Response:%n %s", response.toString(4));
                    } catch (JSONException e){
                        e.printStackTrace();
                    }
                    Toast.makeText(SignIn.this,"Signed in", Toast.LENGTH_SHORT).show();
                    //Go to the fuel find view by an intent and pass the vehicle type
                    Intent sendToLocationView = new Intent(SignIn.this, StationView.class);
                    sendToLocationView.putExtra("USER_VEHICLE_TYPE", vehiceType);
                    startActivity(sendToLocationView);
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SignIn.this,"Error when signing", Toast.LENGTH_SHORT).show();
                VolleyLog.e("Error : ", error.getMessage());
            }
        });

        if (request != null){
            requestQueue.add(request);
        }
    }
}