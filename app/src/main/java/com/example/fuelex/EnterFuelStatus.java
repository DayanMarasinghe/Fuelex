package com.example.fuelex;

import androidx.annotation.AnyRes;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class EnterFuelStatus extends AppCompatActivity {

    CheckBox diesel,petrol92,petrol95;
    EditText arrivalTime, finishTime;
    Button submitbtn;
    String id,location,type,arrTime,finTime,quantity,status;
    ImageButton logout;
    String fuelType, receivedLocation;
    String url_GET,url;

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
        logout = findViewById(R.id.ownLogout);

        Intent receivedIntent = getIntent();
        receivedLocation = receivedIntent.getStringExtra("OWNER_LOCATION");


        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(diesel.isChecked()){
                    fuelType = "Diesel";
                    url_GET = "http://192.168.8.129:8081/api/FuelType/"+receivedLocation+"/Diesel";
                    url = "http://192.168.8.129:8081/api/FuelType/Diesel/"+receivedLocation;
                }else if(petrol92.isChecked()){
                    fuelType = "Petrol92";
                    url_GET = "http://192.168.8.129:8081/api/FuelType/"+receivedLocation+"/Petrol92";
                    url = "http://192.168.8.129:8081/api/FuelType/Petrol92/"+receivedLocation;
                }else{
                    fuelType = "Petrol95";
                    url_GET = "http://192.168.8.129:8081/api/FuelType/"+receivedLocation+"/Petrol95";
                    url = "http://192.168.8.129:8081/api/FuelType/Petrol95/"+receivedLocation;
                }

//                url_GET = "http://192.168.8.101:8081/api/FuelType/"+receivedLocation+"/"+fuelType;
//                url = "http://192.168.8.101:8081/api/FuelType/"+fuelType+"/"+receivedLocation;

                getFuelStatus();
                Toast.makeText(EnterFuelStatus.this, "Submitted Successfully", Toast.LENGTH_LONG).show();

            }
        });
            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(EnterFuelStatus.this, Pivot.class);
                    startActivity(intent);
                }
            });

    }

    private void getFuelStatus(){
        //get request


        RequestQueue getRequestQueue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.GET, url_GET, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Logger logger = Logger.getLogger(EnterFuelStatus.class.getName());
                logger.info("--------------------------------------------------------------------------------------");
                logger.info(response.toString());
                try {
                    JSONObject object =new JSONObject(response);
                    id = object.getString("id");
                    location = object.getString("location");
                    type = object.getString("type");
                    status = object.getString("status");
                    quantity = object.getString("quantity");
                    arrTime = object.getString("arrivalTime");
                    finTime = object.getString("finishTime");

                    updateFuelStatus(id,status,quantity);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error",error.toString());
            }
        });
        getRequestQueue.add(request);
    }

    //update the fuel status
    private void updateFuelStatus(String id,String status,String quantity){
        String aTime = arrivalTime.getText().toString();
        String fTime = finishTime.getText().toString();

        //update request


        HashMap<String, String> body = new HashMap<String, String>();

        body.put("id",id);
        body.put("location",receivedLocation);
        body.put("type",fuelType);
        body.put("status",status);
        body.put("quantity",quantity);
        body.put("arrivalTime",aTime);
        body.put("finishTime",fTime);

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest putRequest = new JsonObjectRequest(Request.Method.PUT, url,new JSONObject(body),
                response -> {
                    VolleyLog.v("Response:%n %s", response.toString());

                    Logger logger = Logger.getLogger(EnterFuelStatus.class.getName());
                    logger.info("--------------------------------------------------------------------------------------");
                    logger.info(response.toString());
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(EnterFuelStatus.this, "Error !!!", Toast.LENGTH_LONG).show();
                        Logger logger = Logger.getLogger(EnterFuelStatus.class.getName());
                        logger.info(error.toString());
                    }
                });

        requestQueue.add(putRequest);
    }
}