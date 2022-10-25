package com.example.fuelex;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class EnterFuelStatus extends AppCompatActivity {

    CheckBox diesel,petrol92,petrol95;
    EditText arrivalTime, finishTime;
    Button submitbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter_fuel_status);

        diesel = findViewById(R.id.checkBox1);
        petrol92 = findViewById(R.id.checkBox2);
        petrol95 = findViewById(R.id.checkBox3);
        arrivalTime = findViewById(R.id.fuelarrivaltime);
        finishTime = findViewById(R.id.fuelfinishtime);
        submitbtn = findViewById(R.id.fuelstbtn);

        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                updateFuelStatus();
            }
        });
    }

    private void updateFuelStatus(){
        String aTime = arrivalTime.getText().toString();
        String fTime = finishTime.getText().toString();
        String fuelType, Diesel = "", Petrol92 = "", Petrol95 = "";

        if(diesel.isChecked()){
            fuelType = Diesel;
        }else if(petrol92.isChecked()){
            fuelType = Petrol92;
        }else{
            fuelType = Petrol95;
        }

        String url = "http://192.168.8.102:45456/api/FuelType/"+fuelType+"/location";

        StringRequest putRequest = new StringRequest(Request.Method.PUT, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(EnterFuelStatus.this, "Error !!!", Toast.LENGTH_LONG).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("arrivalTime", aTime);
                params.put("finishTime", fTime);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(putRequest);

    }
}