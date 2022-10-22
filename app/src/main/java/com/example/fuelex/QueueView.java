package com.example.fuelex;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class QueueView extends AppCompatActivity {
    String location = "Kandy";
    String fuelType = "Petrol";
    double avgTime = 12.0;
    int currCount = 10;
    int beforeCount = 2;
    int afterCount = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.queue_view);

        //set the view elements
        TextView avgTimeInput = findViewById(R.id.id_AvgTimeInput);
        TextView locationInput = findViewById(R.id.id_QueueViewHeadLocationInput);
        TextView fuelInput = findViewById(R.id.id_QueueViewHeadFuelTypeInput);
        TextView currCountInput = findViewById(R.id.id_CurCountInput);
        TextView beforeCountInput = findViewById(R.id.id_BeforeLeaveInput);
        TextView afterCountInput = findViewById(R.id.id_AfterLeaveInput);
        TextView statusText = findViewById(R.id.id_QueueStatusInput);

        //setting values
        avgTimeInput.setText(Integer.toString((int) avgTime));
        locationInput.setText(location);
        fuelInput.setText(fuelType);
        currCountInput.setText(Integer.toString(currCount));
        beforeCountInput.setText(Integer.toString(beforeCount));
        afterCountInput.setText(Integer.toString(afterCount));

        //check the conditions before setting the status
        if (afterCount < beforeCount){
            statusText.setText("Slow moving queue");
            statusText.setBackgroundResource(R.color.red);
        } else if(afterCount > beforeCount){
            statusText.setText("Fast moving queue");
            statusText.setBackgroundResource(R.color.green);
        } else{
            statusText.setText("Moderate");
            statusText.setBackgroundResource(R.color.yellow);
        }
    }
}