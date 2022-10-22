package com.example.fuelex;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class OneStationView extends AppCompatActivity {

    ListView fuelTypeList, avaialableQtyList ,arrivleTimeList;
    String ftList[] = {"Diesel", "Petrol","Octane 92", "Octane 95","Oil 1","Oil 2"};
    Integer avaQtyList[] = {10,20,30,40,100,90};
    String arrTimeList[] = {"10AM", "7PM","3:30PM","6AM","9PM","12AM"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.one_station_view);

        //set the list component id
        fuelTypeList = (ListView) findViewById(R.id.id_ListFuelTypes);

        //set content for fuel type list
        fuelTypeList.setAdapter(new FuelListCusAdapter(this,ftList,avaQtyList,arrTimeList));
    }
}