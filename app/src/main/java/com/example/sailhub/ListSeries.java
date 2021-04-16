package com.example.sailhub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/*
This class is used to display the list of series
and give options to add or delete series
 */
public class ListSeries extends AppCompatActivity implements SeriesListAdapter.OnSeriesListener {

    RecyclerView rvListOfSeries;
    Button addSeries;
    DBHelper DB;
    ArrayList<String> seriesList;
    SeriesListAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_series);

        // link variable ti XML object
        addSeries = (Button) findViewById(R.id.btnAddSeries);
        rvListOfSeries = findViewById(R.id.rvSeriesList);
        // get instance of DB
        DB = DBHelper.getInstance(this);
        seriesList = new ArrayList<>();

        // populate serieslist
        storeSeriesNameInArray();

        // create adapter object
        myAdapter = new SeriesListAdapter(this,seriesList, this);

        // set adapter
        rvListOfSeries.setAdapter(myAdapter);
        rvListOfSeries.setLayoutManager(new LinearLayoutManager(this));

        // onclick listener for add series
        addSeries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addSeriesIntent = new Intent(ListSeries.this, SeriesDetailsForm.class);
                startActivity(addSeriesIntent);
            }
        });

    }

    // method to populate series list
    void storeSeriesNameInArray() {
        Cursor cursor = DB.readSeriesName();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No Data.", Toast.LENGTH_SHORT).show();

        } else {
            while (cursor.moveToNext()) {
                seriesList.add(cursor.getString(0));
            }//while
        }//else
    }


    @Override
    public void onSeriesClick(int position) {
        Intent intent = new Intent(ListSeries.this, DisplayRaceResult.class);
        intent.putExtra("nameOfSeries",seriesList.get(position));

        startActivity(intent);
    }

    // method that calls remove series
    @Override
    public void onDeleteClick(int position) {
        removeItem(position);
    }

    // method to remove remove series
    public void removeItem(int position) {
        String series_name = seriesList.get(position);
        DB.deleteSeriesData(series_name);
        seriesList.remove(position);
        myAdapter.notifyItemRemoved(position);
    }

}