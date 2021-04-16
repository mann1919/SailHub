package com.example.sailhub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/*
This class displays the race results and gives and option
to add race data to generate results.
 */
public class DisplayRaceResult extends AppCompatActivity {

    String sName;
    RecyclerView rvRaceList;
    TextView seriesName;
    Button GenerateResults;
    ImageView imgHome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_race_result);

        // get and set series name
        seriesName = findViewById(R.id.tvNameOfSeries);
        sName = getIntent().getExtras().getString("nameOfSeries");
        seriesName.setText(sName);

        // link variable to XML object
        imgHome = findViewById(R.id.imgHome);
        GenerateResults = (Button) findViewById(R.id.btnGenerateResult);

        // create adapter object
        DisplayRaceResultAdapter myAdapter = new DisplayRaceResultAdapter(this, sName);

        // link variable to XML object
        rvRaceList = findViewById(R.id.rvRace);
        // set adapter
        rvRaceList.setAdapter(myAdapter);
        rvRaceList.setLayoutManager(new LinearLayoutManager(this));

        //listener for home button
        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisplayRaceResult.this, ListSeries.class);
                startActivity(intent);
            }
        });
        //listener for generate result button
        GenerateResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisplayRaceResult.this, DisplaySeriesResult.class);
                intent.putExtra("seriesName",sName);
                startActivity(intent);
            }
        });

    }
}