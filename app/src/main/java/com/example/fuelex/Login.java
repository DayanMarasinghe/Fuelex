package com.example.fuelex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.logging.Logger;

public class Login extends AppCompatActivity {

    EditText usernameTxt;
    EditText passwordTxt;
    Button loginbtn;
    TextView signinbtn;
    String userName, password,vehicleType, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        usernameTxt = findViewById(R.id.ownerUN);
        passwordTxt = findViewById(R.id.ownerPW);
        loginbtn =  findViewById(R.id.cusloginbtn);
        signinbtn = findViewById(R.id.cussignins);



        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userName = usernameTxt.getText().toString();
                password = passwordTxt.getText().toString();

                if(userName.isEmpty()||password.isEmpty()){
                    Toast.makeText(Login.this,"Please give credentials", Toast.LENGTH_SHORT).show();
                }else{
                    //calling the login function
                    login();
                }
            }
        });

        signinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, SignIn.class);
                startActivity(intent);
            }
        });
    }

    //customer login method
    private void login(){

        String url ="http://192.168.8.129:8081/api/Customer";

        HashMap<String, String> body = new HashMap<String, String>();

        body.put("userName",userName);
        body.put("password",password);

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url,new JSONObject(body),

                response -> {
                    try {
                        VolleyLog.v("Response:%n %s", response.toString(4));

                        Logger logger = Logger.getLogger(Login.class.getName());
                        logger.info("--------------------------------------------------------------------------------------");
                        logger.info(response.toString());

                        vehicleType = response.getString("vehicleType");
                        name = response.getString("fullName");
                    } catch (JSONException e){
                        e.printStackTrace();
                    }


                    Toast.makeText(Login.this,"Welcome "+name, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login.this, StationView.class);
                    intent.putExtra("USER_VEHICLE_TYPE", vehicleType);
                    startActivity(intent);
                },


                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Login.this, "Error !!!", Toast.LENGTH_LONG).show();
                        VolleyLog.e("Error : ", error.getMessage());
                        Logger logger = Logger.getLogger(Login.class.getName());
                        logger.info("--------------------------------------------------------------------------------------");
                        logger.info(error.toString());
                    }
                });

        if (stringRequest != null) {
            requestQueue.add(stringRequest);
        }
    }
}
