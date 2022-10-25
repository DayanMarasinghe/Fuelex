package com.example.fuelex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Pivot extends AppCompatActivity {

    ImageView owner,cus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pivot);

        cus = findViewById(R.id.cusPivot);
        owner = findViewById(R.id.ownpivot);

        cus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Pivot.this, Login.class);
                startActivity(intent);
            }
        });

        owner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Pivot.this, OwnerLogin.class);
                startActivity(intent);
            }
        });
    }
}