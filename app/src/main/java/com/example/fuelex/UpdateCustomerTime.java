package com.example.fuelex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class UpdateCustomerTime extends AppCompatActivity {

    Button joined, leftBefore, leftAfter;
    String URL="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_customer_time);

        joined = findViewById(R.id.joinbtn);
        leftAfter = findViewById(R.id.leftafterbtn);
        leftBefore = findViewById(R.id.leftbeforebtn);

        Intent receivedIntent = getIntent();
        String receivedVType = receivedIntent.getStringExtra("USER_VEHICLE_TYPE");
        String receivedLocation = receivedIntent.getStringExtra("USER_SELECTED_LOCATION");
        String receivedFuelType = receivedIntent.getStringExtra("USER_SELECT_FUEL_TYPE");

        URL = "https://192.168.8.102:45457/api/Queue/"+receivedLocation+"/"+receivedVType+"/"+receivedFuelType;

        joined.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                joinedQueue();
            }
        });

        leftAfter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                leftAfterPumping();
            }
        });

        leftBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                leftBeforePumping();
            }
        });
    }

    //update number of vehicles in queue
    private void joinedQueue(){
        StringRequest putRequest = new StringRequest(Request.Method.PUT, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(UpdateCustomerTime.this, "Error !!!", Toast.LENGTH_LONG).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Status", "Arrival");

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(putRequest);
    }

    //update count of vehicles left after pumping fuel
    private void leftAfterPumping(){
        StringRequest putRequest = new StringRequest(Request.Method.PUT, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(UpdateCustomerTime.this, "Error !!!", Toast.LENGTH_LONG).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Status", "AfterLeave");

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(putRequest);
    }

    //update count of vehicles left without pumping fuel
    private void leftBeforePumping(){
        StringRequest putRequest = new StringRequest(Request.Method.PUT, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(UpdateCustomerTime.this, "Error !!!", Toast.LENGTH_LONG).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Status", "BeforeLeave");

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(putRequest);
    }
}