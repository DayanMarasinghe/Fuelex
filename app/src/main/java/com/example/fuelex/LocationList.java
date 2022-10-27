package com.example.fuelex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class LocationList extends AppCompatActivity {

    SharedPreferences pref;
    ImageButton imgBtn;
    TextView txtView;
    String recivedVType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_list);

        imgBtn = findViewById(R.id.btn_GoToStation);
        imgBtn.setClickable(true);
        txtView = findViewById(R.id.id_LocationName);

        //get user vehicle type from sessions
        pref = getSharedPreferences("user_vehicledetails",MODE_PRIVATE);
        recivedVType = pref.getString("user_vtype",null);


    }

    public void doThisOnClick(View view){
        //set selected location as a session details on the click
        pref = getSharedPreferences("user_vehicledetails",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("user_location",txtView.getText().toString());
        editor.commit();

        //Go to the fuel list view by an intent and pass the vehicle type and selected location
        Intent sendToFuelView = new Intent(LocationList.this, OneStationView.class);
        sendToFuelView.putExtra("USER_VEHICLE_TYPE", recivedVType);
        sendToFuelView.putExtra("USER_SELECTED_LOCATION", txtView.getText().toString());
        startActivity(sendToFuelView);
    }
}