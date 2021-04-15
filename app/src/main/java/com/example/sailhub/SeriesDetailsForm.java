package com.example.sailhub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SeriesDetailsForm extends AppCompatActivity {

    EditText seriesName,noOfRaces,noOfCompetitors;
    Button submit;
    DBHelper DB;
    ArrayList<Integer> seriesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_series_details_form);

        seriesName = (EditText) findViewById(R.id.etSeriesName);
        noOfRaces = (EditText) findViewById(R.id.etNoOfRaces);
        noOfCompetitors = (EditText) findViewById(R.id.etNoOfCompetitors);
        submit = (Button) findViewById(R.id.btnSubmit);

        DB = DBHelper.getInstance(this);
        seriesList = new ArrayList<>();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String sName = seriesName.getText().toString();
                    int sNoOfCompetitors = noOfCompetitors.getText().toString().equals("") ? 0 : Integer.parseInt(noOfCompetitors.getText().toString());
                    int sNoOfRaces = noOfRaces.getText().toString().equals("") ? 0 : Integer.parseInt(noOfRaces.getText().toString());

                    if (sName.equals("")) {
                        Toast.makeText(SeriesDetailsForm.this, "Please enter series name", Toast.LENGTH_SHORT).show();
                        return;
                    }//if

                    else if (sNoOfRaces == 0) {
                        Toast.makeText(SeriesDetailsForm.this, "Please enter number of races", Toast.LENGTH_SHORT).show();
                        return;
                    }//if

                    else if (sNoOfCompetitors == 0) {
                        Toast.makeText(SeriesDetailsForm.this, "Please enter number of competitors", Toast.LENGTH_SHORT).show();
                        return;
                    }//if

                    Boolean checkInsertData = DB.insertSeriesData(sName, sNoOfRaces, sNoOfCompetitors);

                    for (int i = 0; i < sNoOfRaces; i++) {
                        Boolean checkRaceData = DB.insertRaceEntries(sName);
                        if (!checkRaceData) {
                            Toast.makeText(SeriesDetailsForm.this, "Error please try again", Toast.LENGTH_SHORT).show();
                            return;
                        }//if
                    }
                    if (checkInsertData) {
                        Toast.makeText(SeriesDetailsForm.this, "New Entry Inserted", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), CompetitorDetailsForm.class);
                        intent.putExtra("value", sName);
                        intent.putExtra("compNo", sNoOfCompetitors);
                        startActivity(intent);
                    }//if
                    else
                        Toast.makeText(SeriesDetailsForm.this, "New Entry Not Inserted, try again", Toast.LENGTH_SHORT).show();
                    return;
                }catch (Exception ex) {
                    Toast.makeText(SeriesDetailsForm.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


}