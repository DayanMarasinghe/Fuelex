package com.example.fuelex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class UpdateCustomerTime extends AppCompatActivity {

    Button joined, leftBefore, leftAfter;
    String URL;
    String receivedLocation;
    String receivedFuelType;
    ImageButton logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_customer_time);

        joined = findViewById(R.id.joinbtn);
        leftAfter = findViewById(R.id.leftafterbtn);
        leftBefore = findViewById(R.id.leftbeforebtn);
        logout = findViewById(R.id.cusLogout);

        Intent receivedIntent = getIntent();
        String receivedVType = receivedIntent.getStringExtra("USER_VEHICLE_TYPE");
        receivedLocation = receivedIntent.getStringExtra("USER_SELECTED_LOCATION");
        receivedFuelType = receivedIntent.getStringExtra("USER_SELECT_FUEL_TYPE");

        URL = "http://192.168.8.129:8081/api/Queue/"+receivedLocation+"/"+receivedVType+"/"+receivedFuelType;

        joined.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                joinedQueue();
                Toast.makeText(UpdateCustomerTime.this,"Joined the "+receivedLocation+" "+receivedFuelType+" queue", Toast.LENGTH_LONG).show();

            }
        });

        leftAfter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                leftAfterPumping();
                Toast.makeText(UpdateCustomerTime.this,"Leaving the "+receivedLocation+" "+receivedFuelType+" queue", Toast.LENGTH_LONG).show();
            }
        });

        leftBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                leftBeforePumping();
                Toast.makeText(UpdateCustomerTime.this,"Leaving the "+receivedLocation+" "+receivedFuelType+" queue", Toast.LENGTH_LONG).show();

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpdateCustomerTime.this, Pivot.class);
                startActivity(intent);
            }
        });
    }

    //update number of vehicles in queue
    private void joinedQueue(){

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        HashMap<String, String> body = new HashMap<String, String>();
        body.put("Status", "Arrival");

        JsonObjectRequest putRequest = new JsonObjectRequest(Request.Method.PUT, URL,new JSONObject(body),

                response -> {
                    VolleyLog.v("Response:%n %s", response.toString());

                    Logger logger = Logger.getLogger(Login.class.getName());
                    logger.info("--------------------------------------------------------------------------------------");
                    logger.info(response.toString());
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(UpdateCustomerTime.this, "Error !!!", Toast.LENGTH_LONG).show();
                    }
                });

        requestQueue.add(putRequest);

    }

    //update count of vehicles left after pumping fuel
    private void leftAfterPumping(){

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        HashMap<String, String> body = new HashMap<String, String>();
//        body.put("Id","");
        body.put("Status", "AfterLeave");

        JsonObjectRequest putRequest = new JsonObjectRequest(Request.Method.PUT, URL,new JSONObject(body),

                response -> {
                    VolleyLog.v("Response:%n %s", response.toString());

                    Logger logger = Logger.getLogger(Login.class.getName());
                    logger.info("--------------------------------------------------------------------------------------");
                    logger.info(response.toString());
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(UpdateCustomerTime.this, "Error !!!", Toast.LENGTH_LONG).show();
                    }
                });

        requestQueue.add(putRequest);
    }

    //update count of vehicles left without pumping fuel
    private void leftBeforePumping(){

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        HashMap<String, String> body = new HashMap<String, String>();
        body.put("Status", "BeforeLeave");

        JsonObjectRequest putRequest = new JsonObjectRequest(Request.Method.PUT, URL,new JSONObject(body),

                response -> {
                    VolleyLog.v("Response:%n %s", response.toString());

                    Logger logger = Logger.getLogger(Login.class.getName());
                    logger.info("--------------------------------------------------------------------------------------");
                    logger.info(response.toString());
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(UpdateCustomerTime.this, "Error !!!", Toast.LENGTH_LONG).show();
                    }
                });

        requestQueue.add(putRequest);
    }
}