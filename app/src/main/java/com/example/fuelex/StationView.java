package com.example.fuelex;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class StationView extends AppCompatActivity {
    ListView locationList;
    String locList[] = {"Kandy", "Colombo", "wer","WERWRE","WERWRWRE","WERWRWER","WWERWRWRE","WERWRE"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fuel_station_find_view);

        //set the lis component id
        locationList = (ListView) findViewById(R.id.id_locationList);

        //set the content for the list
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.location_list, R.id.id_LocationName,locList);
        locationList.setAdapter(arrayAdapter);

    }
}
