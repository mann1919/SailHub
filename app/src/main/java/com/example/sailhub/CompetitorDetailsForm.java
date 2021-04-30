package com.example.sailhub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/*
This class creates the form to take input for the competitors details
 */
public class CompetitorDetailsForm extends AppCompatActivity {

    // declaring variables
    String sName;
    TextView seriesName;
    Button addCompetitor;
    int noOfComp;
    int sNoOfRaces;
    RecyclerView rvCompetitors;
    DBHelper DB;
    public ArrayList<EditModel> competitorList;
    public ArrayList<Integer> raceIds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competitor_details_form);

        // inform users to fill data
        Toast.makeText(CompetitorDetailsForm.this, "Fill in competitor details", Toast.LENGTH_SHORT).show();
        //get series name
        seriesName = findViewById(R.id.tvSeriesName);
        sName = getIntent().getExtras().getString("seriesName");
        seriesName.setText(sName);

        // get no of races
        sNoOfRaces = getIntent().getExtras().getInt("raceNo");

        // get instance of DB
        DB = DBHelper.getInstance(this);

        //
        raceIds = new ArrayList<>();

        // populate competitor list
        competitorList = populateList();
        // create object of the adapter
        CompetitorDetailAdapter myAdapter = new CompetitorDetailAdapter(this,competitorList);

        // link variable to XML object
        addCompetitor = (Button) findViewById(R.id.btnAddCompetitors);
        rvCompetitors = findViewById(R.id.rvCompetitors);
        rvCompetitors.setAdapter(myAdapter);
        rvCompetitors.setLayoutManager(new LinearLayoutManager(this));

        // onClick listener for button
        addCompetitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    DB.insertSeriesData(sName, sNoOfRaces, noOfComp);
                    for (int i = 0; i < sNoOfRaces; i++) {
                        Boolean checkRaceData = DB.insertRaceEntries(sName);
                        if (!checkRaceData) {
                            Toast.makeText(CompetitorDetailsForm.this, "Enter Series details again", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), SeriesDetailsForm.class);
                            startActivity(intent);
                        }//if
                    }
                    // get all race ids for the series
                    getRId(sName);
                    for (int raceId : raceIds) {
                        for (int i = 0; i < noOfComp; i++) {

                            // link variable to XML object
                            View rowView = rvCompetitors.getChildAt(i);
                            EditText bClass = (EditText)rowView.findViewById(R.id.etClass);
                            EditText PY = (EditText)rowView.findViewById(R.id.etPY);
                            EditText sailNo = (EditText)rowView.findViewById(R.id.etSailNo);
                            EditText helmName = (EditText)rowView.findViewById(R.id.etHelmName);
                            EditText crewName = (EditText)rowView.findViewById(R.id.etCrewName);


                            // get value from edit text
                            String cClass = bClass.getText().toString();
                            int cPY = PY.getText().toString().equals("") ? 0 : Integer.parseInt(PY.getText().toString());
                            int cSailNo = sailNo.getText().toString().equals("") ? 0 : Integer.parseInt(sailNo.getText().toString());
                            String cHelmName = helmName.getText().toString();
                            String cCrewName = crewName.getText().toString();
                            // validation for input
                            if(cClass.equals("")){
                                Toast.makeText(CompetitorDetailsForm.this, "Enter Class", Toast.LENGTH_SHORT).show();
                                return;
                            }//if
                            else if(cPY == 0){
                                Toast.makeText(CompetitorDetailsForm.this, "Enter PY", Toast.LENGTH_SHORT).show();
                                return;
                            }//if
                            else if(cSailNo == 0){
                                Toast.makeText(CompetitorDetailsForm.this, "Enter SailNo", Toast.LENGTH_SHORT).show();
                                return;
                            }//if
                            else if(cHelmName.equals("")){
                                Toast.makeText(CompetitorDetailsForm.this, "Enter Helm Name", Toast.LENGTH_SHORT).show();
                                return;
                            }//if

                            // entering data in database.
                            Boolean checkInsertData = DB.insertCompetitorData(raceId, cClass, cPY, cSailNo, cHelmName,cCrewName);

                            // catch error
                            if (!checkInsertData)
                                throw new Exception("Error while inserting new entry");

                        }// for every competitor
                    }// for every race id

                    // toast to inform users about task completion
                    Toast.makeText(CompetitorDetailsForm.this, "Series created with competitors", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), ListSeries.class);
                    startActivity(intent);
                }
                catch (Exception ex) {
                    Toast.makeText(CompetitorDetailsForm.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    // method to get all race ids for the series name given
    void getRId(String sName) {

        Cursor cursor = DB.getRaceIds(sName);
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "Error occurred", Toast.LENGTH_SHORT).show();

        } else {
            while (cursor.moveToNext()) {
                raceIds.add(cursor.getInt(0));
            }//while
        }//else
    }

    // populate the competitors list
    private ArrayList<EditModel> populateList(){

        ArrayList<EditModel> list = new ArrayList<>();
        noOfComp = getIntent().getIntExtra("compNo",0);

        for(int i = 1; i <=noOfComp; i++){

            EditModel competitor = new EditModel();
            competitor.setTvIndexValue(String.valueOf(i));
            list.add(competitor);
        }

        return list;
    }
}