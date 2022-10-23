package com.example.fuelex;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class StationView extends AppCompatActivity {
    ListView locationList;
    String locList[] = {"Kandy", "Colombo", "wer","WERWRE","WERWRWRE","WERWRWER","WWERWRWRE","WERWRE"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fuel_station_find_view);

        //get vehicle type from intent
        Intent receiveVType = getIntent();
        String receivedVType = receiveVType.getStringExtra("USER_VEHICLE_TYPE");

        Toast.makeText(StationView.this,"Vehicle Type " + receivedVType, Toast.LENGTH_SHORT).show();

        //set the lis component id
        locationList = (ListView) findViewById(R.id.id_locationList);

        //set the content for the list
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.location_list, R.id.id_LocationName,locList);
        locationList.setAdapter(arrayAdapter);

    }
}
