package com.example.sailhub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class ListSeries extends AppCompatActivity {

    RecyclerView rvListOfSeries;
    Button addSeries;
    DBHelper DB;
    ArrayList<String> seriesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_series);
        addSeries = (Button) findViewById(R.id.btnAddSeries);
        rvListOfSeries = findViewById(R.id.rvSeriesList);

        DB = DBHelper.getInstance(this);
        seriesList = new ArrayList<>();

        storeDataInArrays();

        SeriesListAdapter myAdapter = new SeriesListAdapter(this,seriesList);

        rvListOfSeries.setAdapter(myAdapter);
        rvListOfSeries.setLayoutManager(new LinearLayoutManager(this));

        addSeries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addSeriesIntent = new Intent(ListSeries.this, SeriesDetailsForm.class);
                startActivity(addSeriesIntent);
            }
        });

    }

    void storeDataInArrays() {
        Cursor cursor = DB.readSeriesName();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No Data.", Toast.LENGTH_SHORT).show();

        } else {
            while (cursor.moveToNext()) {
                seriesList.add(cursor.getString(1));
            }//while
        }//else
    }
}