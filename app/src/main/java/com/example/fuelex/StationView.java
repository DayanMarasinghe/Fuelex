package com.example.fuelex;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fuelex.Models.StationModel;
import com.example.fuelex.RetrofitClasses.RetrofitClientStation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;

public class StationView extends AppCompatActivity {
    String URL = "";
    RequestQueue requestQueue;
    ListView locationList;
    ArrayList<String> locList = new ArrayList<String>();
    Logger logger = Logger.getLogger(StationView.class.getName());
    SharedPreferences pref;
    String receivedVType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fuel_station_find_view);

        //get vehicle type from intent
        Intent receiveVType = getIntent();
        receivedVType = receiveVType.getStringExtra("USER_VEHICLE_TYPE");

        URL = "http://192.168.8.108:8081/api/FuelStation";

        Toast.makeText(StationView.this,"Vehicle Type " + receivedVType, Toast.LENGTH_SHORT).show();

        //set the lis component id
        locationList = (ListView) findViewById(R.id.id_locationList);

        //calling the endpoint
        getStationList();

        //Set vehicle type as a session variable
        pref = getSharedPreferences("user_vehicledetails",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("user_vtype",receivedVType);
        editor.commit();


    }
    public void doThisOnClick(View view){
        TextView txtView = findViewById(R.id.id_LocationName);
        //set selected location as a session details on the click
        pref = getSharedPreferences("user_vehicledetails",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("user_location",txtView.getText().toString());
        editor.commit();

        //Go to the fuel list view by an intent and pass the vehicle type and selected location
        Intent sendToFuelView = new Intent(StationView.this, OneStationView.class);
        sendToFuelView.putExtra("USER_VEHICLE_TYPE",receivedVType );
        sendToFuelView.putExtra("USER_SELECTED_LOCATION", txtView.getText().toString());
        startActivity(sendToFuelView);
    }

    //calling the backend and getting all the available stations
    private void getStationList(){

        requestQueue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                logger.info("--------------------------------------------------------------------------------------");
                logger.info(response);
                try {
                    JSONArray array =new JSONArray(response);
                    for (int j = 0,i=0;j< array.length();j++,i++){
                        JSONObject obj =array.getJSONObject(j);
                        locList.add(obj.getString("location"));
                    }
                    //set the content for the list
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(StationView.this, R.layout.location_list, R.id.id_LocationName,locList);
                    locationList.setAdapter(arrayAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(StationView.this,error.getMessage(),Toast.LENGTH_LONG).show();
                Log.d("error",error.toString());
                logger.info(error.toString());
            }
        });

        requestQueue.add(request);


    }
}
