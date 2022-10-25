package com.example.fuelex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class QueueView extends AppCompatActivity {
    String URL = "";
    RequestQueue requestQueue;
    String location = "Kandy";
    String fuelType = "Petrol";
    double avgTime = 12.0;
    int currCount = 10;
    int beforeCount = 2;
    int afterCount = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.queue_view);

        Intent recievedIntent = getIntent();
        String receivedVType = recievedIntent.getStringExtra("USER_VEHICLE_TYPE");
        String receivedLocation = recievedIntent.getStringExtra("USER_SELECTED_LOCATION");
        String receivedFuelType = recievedIntent.getStringExtra("USER_SELECT_FUEL_TYPE");

        //set URL string
        URL ="http://192.168.8.108:45455/api/Queue/"+receivedLocation+"/"+receivedVType+"/"+receivedFuelType;


        //set the view elements
        TextView avgTimeInput = findViewById(R.id.id_AvgTimeInput);
        TextView locationInput = findViewById(R.id.id_QueueViewHeadLocationInput);
        TextView fuelInput = findViewById(R.id.id_QueueViewHeadFuelTypeInput);
        TextView currCountInput = findViewById(R.id.id_CurCountInput);
        TextView beforeCountInput = findViewById(R.id.id_BeforeLeaveInput);
        TextView afterCountInput = findViewById(R.id.id_AfterLeaveInput);
        ImageView imgStatus = (ImageView) findViewById(R.id.id_StatusImg);

        //setting values
        avgTimeInput.setText(Integer.toString((int) avgTime));
        locationInput.setText(receivedLocation);
        fuelInput.setText(receivedFuelType);
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
    }

    private void getQueueDetails(){
        requestQueue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object =new JSONObject(response);
                    location = object.getString("location");
                    fuelType = object.getString("type");
                    currCount = object.getInt("currentCount");
                    afterCount = object.getInt("afterCount");
                    beforeCount = object.getInt("beforeCount");
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