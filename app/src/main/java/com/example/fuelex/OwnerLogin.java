package com.example.fuelex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class OwnerLogin extends AppCompatActivity {

    EditText usernameTxt;
    EditText passwordTxt;
    Button loginbtn;
    String location, userName, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.owner_login);

        usernameTxt = findViewById(R.id.ownerUN);
        passwordTxt = findViewById(R.id.ownerPW);
        loginbtn =  findViewById(R.id.ownlogintbn);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 userName = usernameTxt.getText().toString();
                 password = passwordTxt.getText().toString();

                if(userName.isEmpty()||password.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please enter credentials", Toast.LENGTH_SHORT).show();
                }else{
                    ownerLogin();
                }
            }
        });
    }

    private void ownerLogin(){
        String LOGIN_URL = "http://192.168.8.103:8081/api/FuelStation";

        HashMap<String, String> body = new HashMap<String, String>();

        body.put("userName",userName);
        body.put("password",password);

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, LOGIN_URL,new JSONObject(body),

                response -> {
                    try {
                        VolleyLog.v("Response:%n %s", response.toString(4));

                        Logger logger = Logger.getLogger(Login.class.getName());
                        logger.info("--------------------------------------------------------------------------------------");
                        logger.info(response.toString());

                        location = response.getString("location");

                    } catch (JSONException e){
                        e.printStackTrace();
                    }

                    //vehicleType = response.vehicleType.toString();
                    Toast.makeText(OwnerLogin.this,"Login Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(OwnerLogin.this, EnterFuelStatus.class);
                    intent.putExtra("OWNER_LOCATION", location);
                    startActivity(intent);
                },


                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(OwnerLogin.this, "Error !!!", Toast.LENGTH_LONG).show();
                        VolleyLog.e("Error : ", error.getMessage());
                    }
                });

        if (stringRequest != null) {
            requestQueue.add(stringRequest);
        }
    }
}