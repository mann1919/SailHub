package com.example.sailhub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

public class DisplayRaceResult extends AppCompatActivity {


    String sName;
    RecyclerView rvRaceList;
    TextView seriesName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_race_result);

        seriesName = findViewById(R.id.tvNameOfSeries);
        sName = getIntent().getExtras().getString("nameOfSeries");
        seriesName.setText(sName);

        DisplayRaceResultAdapter myAdapter = new DisplayRaceResultAdapter(this, sName);

        rvRaceList = findViewById(R.id.rvRace);
        rvRaceList.setAdapter(myAdapter);
        rvRaceList.setLayoutManager(new LinearLayoutManager(this));


    }
}