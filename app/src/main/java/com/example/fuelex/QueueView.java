package com.example.fuelex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.logging.Logger;

public class QueueView extends AppCompatActivity {
    String URL = "";
    RequestQueue requestQueue;
    String location;
    String fuelType, vehicleType;
    double avgTime;
    int currCount;
    int beforeCount;
    int afterCount;
    Button backBtn, waitBtn;
    TextView avgTimeInput,locationInput,fuelInput,currCountInput,beforeCountInput,afterCountInput;
    ImageView imgStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.queue_view);

        Intent recievedIntent = getIntent();
        String receivedVType = recievedIntent.getStringExtra("USER_VEHICLE_TYPE");
        String receivedLocation = recievedIntent.getStringExtra("USER_SELECTED_LOCATION");
        String receivedFuelType = recievedIntent.getStringExtra("USER_SELECT_FUEL_TYPE");
        waitBtn = findViewById(R.id.id_BtnStayInQueue);
        backBtn = findViewById(R.id.id_btnQueueBack);

        //set URL string
        URL ="http://192.168.8.101:8081/api/Queue/Colombo/Bus/Diesel";//+receivedLocation+"/"+receivedVType+"/"+receivedFuelType;



        //set the view elements
         avgTimeInput = findViewById(R.id.id_AvgTimeInput);
         locationInput = findViewById(R.id.id_QueueViewHeadLocationInput);
         fuelInput = findViewById(R.id.id_QueueViewHeadFuelTypeInput);
         currCountInput = findViewById(R.id.id_CurCountInput);
         beforeCountInput = findViewById(R.id.id_BeforeLeaveInput);
         afterCountInput = findViewById(R.id.id_AfterLeaveInput);
         imgStatus = (ImageView) findViewById(R.id.id_StatusImg);

        getQueueDetails();


        waitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent sendToUpCusTime = new Intent(QueueView.this, UpdateCustomerTime.class);
//                sendToUpCusTime.putExtra("USER_VEHICLE_TYPE", receivedVType);
//                sendToUpCusTime.putExtra("USER_SELECTED_LOCATION",receivedLocation);
//                sendToUpCusTime.putExtra("USER_SELECT_FUEL_TYPE",receivedFuelType);
//                startActivity(sendToUpCusTime);
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendToUpCusTime = new Intent(QueueView.this, StationView.class);
                startActivity(sendToUpCusTime);
            }
        });

    }

    //get the queue details for the fuel type
    private void getQueueDetails(){
        requestQueue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Logger logger = Logger.getLogger(QueueView.class.getName());
                    logger.info("--------------------------------------------------------------------------------------");
                    logger.info(response);

                    JSONObject object =new JSONObject(response);
                    location = object.getString("location");
                    vehicleType = object.getString("vehicleType");
                    fuelType = object.getString("fuelType");
                    currCount = object.getInt("currentCount");
                    afterCount = object.getInt("afterCount");
                    beforeCount = object.getInt("beforeCount");


                    //setting values
                    avgTimeInput.setText(Integer.toString((int) avgTime));
                    locationInput.setText(location);
                    fuelInput.setText(fuelType);
                    currCountInput.setText(Integer.toString(currCount));
                    beforeCountInput.setText(Integer.toString(beforeCount));
                    afterCountInput.setText(Integer.toString(afterCount));

                    //check the conditions before setting the status
                    if (afterCount < beforeCount){
                        imgStatus.setImageResource(R.drawable.slow_grp);
                    } else if(afterCount > beforeCount){
                        imgStatus.setImageResource(R.drawable.fast_grp);
                    } else{
                        imgStatus.setImageResource(R.drawable.moderate_grp);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(QueueView.this,error.getMessage(),Toast.LENGTH_LONG).show();
                Log.d("error",error.toString());
            }
        });
        requestQueue.add(request);
    }
}