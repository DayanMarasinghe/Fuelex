package com.example.fuelex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class OwnerLogin extends AppCompatActivity {

    EditText usernameTxt;
    EditText passwordTxt;
    Button loginbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.owner_login);

        usernameTxt = findViewById(R.id.ownerun);
        passwordTxt = findViewById(R.id.ownerpw);
        loginbtn =  findViewById(R.id.ownlogintbn);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = usernameTxt.getText().toString();
                String password = passwordTxt.getText().toString();

                if(userName.equals("") || password.equals("")){
                    Toast.makeText(getApplicationContext(), "Please enter credentials", Toast.LENGTH_SHORT).show();
                }else{
                    ownerLogin();
                }
            }
        });
    }

    public static final String SHARED_PREF_NAME = "com.example.fuelex.userLogin";
    public static final String ROLL_SHARED_PREF = "userName";

    private void ownerLogin(){
        String userName = usernameTxt.getText().toString();
        String password = passwordTxt.getText().toString();
        String LOGIN_URL = "";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", "" + response);

                        if (response.equals("success")) {
                            SharedPreferences sp = OwnerLogin.this.getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString(ROLL_SHARED_PREF, userName);

                            editor.apply();

                            Intent intent = new Intent(OwnerLogin.this, EnterFuelStatus.class);
                            startActivity(intent);
                            Toast.makeText(OwnerLogin.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        } else if (response.equals("failure")) {
                            Toast.makeText(OwnerLogin.this, "Username or Password is not valid", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(OwnerLogin.this, "Invalid", Toast.LENGTH_LONG).show();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(OwnerLogin.this, "Error !!!", Toast.LENGTH_LONG).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("userName", userName);
                params.put("password", password);

                //returning parameter
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}