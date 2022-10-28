package com.example.fuelex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.logging.Logger;

public class OneStationView extends AppCompatActivity {
    String URL,receivedVType, receivedLocation;
    RequestQueue requestQueue;
    ListView fuelTypeList;
    ArrayList<String> ftList = new ArrayList<String>();
    ArrayList<String> avaQtyList = new ArrayList<String>();
    ArrayList<String> arrTimeList = new ArrayList<String>();
    Logger logger = Logger.getLogger(StationView.class.getName());
    TextView locName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.one_station_view);

        locName = findViewById(R.id.id_LocationStringInput);

        //get vehicle type from intent
        Intent recievedIntent = getIntent();
        receivedVType = recievedIntent.getStringExtra("USER_VEHICLE_TYPE");
        receivedLocation = recievedIntent.getStringExtra("USER_SELECTED_LOCATION");

        URL ="http://192.168.8.101:8081/api/FuelType/"+receivedLocation;

        locName.setText(receivedLocation);

        //set the list component id
        fuelTypeList = (ListView) findViewById(R.id.id_ListFuelTypes);

        getFuelList();

    }

    //get the available fuel types for a station
    private void getFuelList(){
        requestQueue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                logger.info("--------------------------------------------------------------------------------------");
                logger.info(response);
                try {
                    JSONArray array =new JSONArray(response);
                    for (int j = 0,i=0;j< array.length();j++,i++) {
                        JSONObject obj = array.getJSONObject(j);
                        ftList.add(obj.getString("type"));
                        avaQtyList.add(obj.getString("quantity"));
                        arrTimeList.add(obj.getString("arrivalTime"));
                    }

                    //convert array list to arrays
                    String[] passFuelList = ftList.toArray(new String[0]);
                    String[] passQtyList = avaQtyList.toArray(new String[0]);
                    String[] passTimeList = arrTimeList.toArray(new String[0]);

                    //set the content to the text view and the list
                    fuelTypeList.setAdapter(new FuelListCusAdapter(OneStationView.this,passFuelList,passQtyList,passTimeList));

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

    public void eventOnGoToQueue(View view){
        TextView txtView = findViewById(R.id.id_FuelNameInput);
        //Go to the fuel list view by an intent and pass the vehicle type on button click
        Intent sendToQueueView = new Intent(OneStationView.this, QueueView.class);
        sendToQueueView.putExtra("USER_VEHICLE_TYPE", receivedVType);
        sendToQueueView.putExtra("USER_SELECTED_LOCATION", receivedLocation);
        sendToQueueView.putExtra("USER_SELECT_FUEL_TYPE",txtView.getText().toString());
        startActivity(sendToQueueView);
    }
}