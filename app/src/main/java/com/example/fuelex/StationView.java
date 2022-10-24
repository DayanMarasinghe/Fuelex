package com.example.fuelex;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fuelex.Models.StationModel;
import com.example.fuelex.RetrofitClasses.RetrofitClientStation;

import java.util.List;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StationView extends AppCompatActivity {
    ListView locationList;
    //String locList[] = {"Kandy", "Colombo", "wer","WERWRE","WERWRWRE","WERWRWER","WWERWRWRE","WERWRE"};

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

        //calling the endpoint
        getStationList();

        //set the content for the list
        //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.location_list, R.id.id_LocationName,locList);
        //locationList.setAdapter(arrayAdapter);

    }

    private void getStationList(){
        Call<List<StationModel>> call = RetrofitClientStation.getInstance().getStatonApi().getStations();
        call.enqueue(new Callback<List<StationModel>>() {
            @Override
            public void onResponse(Call<List<StationModel>> call, Response<List<StationModel>> response) {
                List<StationModel> stationList = response.body();
                String[] locList = new String[stationList.size()];

                for (int i = 0; i < stationList.size(); i++){
                    locList[i] = stationList.get(i).getLocation();
                }

                locationList.setAdapter(new ArrayAdapter<String>(getApplicationContext(),R.layout.location_list,R.id.id_LocationName,locList));
            }

            @Override
            public void onFailure(Call<List<StationModel>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Could not get the location list", Toast.LENGTH_LONG).show();
                Logger logger = Logger.getLogger(SignIn.class.getName());
                logger.info("--------------------------------------------------------------------------------------");
                logger.info(t.toString());
                logger.info(call.toString());
                logger.info("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            }
        });
    }
}
