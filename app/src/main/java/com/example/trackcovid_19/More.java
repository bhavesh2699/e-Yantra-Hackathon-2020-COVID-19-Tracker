package com.example.trackcovid_19;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class More extends AppCompatActivity {

    private Button mPrecautionsBtn;
    private Button mSymptoms;
    private Button mindiawebview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);

        mPrecautionsBtn = findViewById(R.id.mPrecautionsBtn);
        mSymptoms = findViewById(R.id.mSymptoms);

       // mindiawebview = findViewById(R.id.mindiawebview);
        /*mindiawebview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tommindiawebview = new Intent(More.this, IndiaCovidCases.class);
                startActivity(tommindiawebview);
            }
        });*/

        mSymptoms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tomPrecautionsBtn = new Intent(More.this, SymptomChecker.class);
                startActivity(tomPrecautionsBtn);
            }
        });

        mPrecautionsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tomPrecautionsBtn = new Intent(More.this, Precautions.class);
                startActivity(tomPrecautionsBtn);
            }
        });

    }
}
