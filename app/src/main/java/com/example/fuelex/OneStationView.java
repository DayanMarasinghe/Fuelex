package com.example.fuelex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

public class OneStationView extends AppCompatActivity {
    String URL = "";
    RequestQueue requestQueue;
    ListView fuelTypeList, avaialableQtyList ,arrivleTimeList;
    String ftList[] = {"Diesel", "Petrol","Octane 92", "Octane 95","Oil 1","Oil 2"};
    Integer avaQtyList[] = {10,20,30,40,100,90};
    String arrTimeList[] = {"10AM", "7PM","3:30PM","6AM","9PM","12AM"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.one_station_view);

        //get vehicle type from intent
        Intent recievedIntent = getIntent();
        String receivedVType = recievedIntent.getStringExtra("USER_VEHICLE_TYPE");
        String receivedLocation = recievedIntent.getStringExtra("USER_SELECTED_LOCATION");

        URL ="http://192.168.8.108:45455/api/FuelType/"+receivedLocation;

        //set the list component id
        fuelTypeList = (ListView) findViewById(R.id.id_ListFuelTypes);

        getFuelList();

        //Go to the fuel list view by an intent and pass the vehicle type
        Intent sendToQueueView = new Intent(OneStationView.this, StationView.class);
        sendToQueueView.putExtra("USER_VEHICLE_TYPE", receivedVType);
        sendToQueueView.putExtra("USER_SELECTED_LOCATION", receivedLocation);
        sendToQueueView.putExtra("USER_SELECT_FUEL_TYPE",ftList[1]);
        startActivity(sendToQueueView);

        //set content for fuel type list
        fuelTypeList.setAdapter(new FuelListCusAdapter(this,ftList,avaQtyList,arrTimeList));
    }

    private void getFuelList(){
        requestQueue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object =new JSONObject(response);
                    JSONArray arrayType=object.getJSONArray("type");
                    for(int i=0;i<arrayType.length();i++) {
                        JSONObject object1=arrayType.getJSONObject(i);
                        ftList[i] = object1.toString();
                    }
                    JSONArray arrayquantity=object.getJSONArray("quantity");
                    for(int i=0;i<arrayquantity.length();i++) {
                        JSONObject object1=arrayquantity.getJSONObject(i);
                        avaQtyList[i] = object1.getInt(object1.toString());
                    }
                    JSONArray arrayarrivalTime=object.getJSONArray("arrivalTime");
                    for(int i=0;i<arrayarrivalTime.length();i++) {
                        JSONObject object1=arrayarrivalTime.getJSONObject(i);
                        arrTimeList[i] = object1.toString();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(OneStationView.this,error.getMessage(),Toast.LENGTH_LONG).show();
                Log.d("error",error.toString());
            }
        });
        requestQueue.add(request);
    }
}