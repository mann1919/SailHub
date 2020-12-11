package com.example.sailhub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SeriesDetailsForm extends AppCompatActivity {

    EditText seriesName,noOfRaces,noOfCompetitors;
    Button submit;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_series_details_form);

        seriesName = (EditText) findViewById(R.id.etSeriesName);
        noOfRaces = (EditText) findViewById(R.id.etNoOfRaces);
        noOfCompetitors = (EditText) findViewById(R.id.etNoOfCompetitors);
        submit = (Button) findViewById(R.id.btnSubmit);

        DB = DBHelper.getInstance(this);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sName = seriesName.getText().toString();
                int sNoOfRaces = Integer.parseInt(noOfRaces.getText().toString());
                int sNoOfCompetitors = Integer.parseInt(noOfCompetitors.getText().toString());


                if(sName.equals("")||noOfRaces.length()==0||noOfCompetitors.length()==0){
                    Toast.makeText(SeriesDetailsForm.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                }//if

                if(sNoOfRaces==0){
                    Toast.makeText(SeriesDetailsForm.this, "Number of races cannot be 0", Toast.LENGTH_SHORT).show();
                }//if
                if(sNoOfCompetitors==0){
                    Toast.makeText(SeriesDetailsForm.this, "Number of competitors cannot be 0", Toast.LENGTH_SHORT).show();
                }//if

                Boolean checkInsertData = DB.insertSeriesData(sName, sNoOfRaces, sNoOfCompetitors);

                if(checkInsertData==true) {
                    Toast.makeText(SeriesDetailsForm.this, "New Entry Inserted", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), CompetitorDetailsForm.class);
                    intent.putExtra("value",sName);
                    startActivity(intent);
                }//if
                else
                    Toast.makeText(SeriesDetailsForm.this, "New Entry Not Inserted, try again", Toast.LENGTH_SHORT).show();

            }
        });
    }
}