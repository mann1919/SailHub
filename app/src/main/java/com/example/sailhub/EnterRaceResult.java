package com.example.sailhub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
this class is used to take user input for
elaped time and laps for each competitor
 */
public class EnterRaceResult extends AppCompatActivity {

    String sName;
    TextView seriesName;
    Button addRaceResult;
    int raceId;
    RecyclerView rvEnterResult;
    DBHelper DB;
    public ArrayList<EditModel> CompetitorData;
    int noOfComp;
    Context context;

    // pattern to filter elapsed input
    private static final String ELAPSED_PATTERN = "(?:[01]\\d|2[0-3]):(?:[0-5]\\d):(?:[0-5]\\d)";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_race_result);

        // get instance of DB
        DB = DBHelper.getInstance(this);

        // get race id
        Bundle extras=getIntent().getExtras();
        raceId = extras.getInt("raceId");;
        //get and set series name
        seriesName = findViewById(R.id.tvSeriesNameTwo);
        setSeriesName(raceId);
        seriesName.setText(sName);
        //set no_of_competitors
        setNoOFCompetitors(sName);

        context = this;

        // populate competitors list
        CompetitorData = populateList();
        // create adapter object
        EnterRaceResultAdapter myAdapter = new EnterRaceResultAdapter(this,CompetitorData);

        // link variable to XML
        addRaceResult = (Button) findViewById(R.id.btnSubmitResult);
        rvEnterResult = findViewById(R.id.rvEnterResult);
        rvEnterResult.setAdapter(myAdapter);
        rvEnterResult.setLayoutManager(new LinearLayoutManager(this));

        Toast.makeText(EnterRaceResult.this, "Fill in elapsed and laps", Toast.LENGTH_SHORT).show();

        // onclick listener for submitting details
        addRaceResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int maxLaps = 0;

                    //calculate max laps
                    for (int i = 0; i < noOfComp; i++) {
                        View rowView = rvEnterResult.getChildAt(i);
                        EditText laps = (EditText)rowView.findViewById(R.id.etLaps);
                        if (laps.getText().toString().equals("")|| Integer.parseInt(laps.getText().toString())<=0) {
                            Toast.makeText(EnterRaceResult.this, "Enter laps greater than 0", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        int cLaps = Integer.parseInt(laps.getText().toString());
                        if (cLaps > maxLaps)
                            maxLaps = cLaps;
                    }

                    for (int i = 0; i < noOfComp; i++) {

                        // link variable to XML object
                        View rowView = rvEnterResult.getChildAt(i);
                        TextView bClass = (TextView)rowView.findViewById(R.id.tvDisplayClass);
                        TextView SailNo = (TextView)rowView.findViewById(R.id.tvDisplaySailNo);
                        TextView helmName = (TextView)rowView.findViewById(R.id.tvDisplayHelmName);
                        EditText elapsed = (EditText)rowView.findViewById(R.id.etElapsed);
                        EditText laps = (EditText)rowView.findViewById(R.id.etLaps);

                        // get values form edit text
                        String cClass = bClass.getText().toString();
                        int cSailNo = Integer.parseInt(SailNo.getText().toString());
                        String cHelmName = helmName.getText().toString();
                        int cLaps = Integer.parseInt(laps.getText().toString());
                        double cPY = Double.parseDouble(CompetitorData.get(i).getTvPYValue());
                        String tempElapsed = elapsed.getText().toString();

                        // pattern check for elapsed
                        if (!tempElapsed.matches(ELAPSED_PATTERN)) {
                            throw new Exception("Enter Elapsed in the format HH:MM:SS");
                        }//if

                        String cElapsed = calculateElapsed(tempElapsed,cLaps,maxLaps);

                        String cCorrected =  calculateCorrected(cElapsed,cPY);

                        Boolean checkInsertData = DB.insertRaceData(raceId, cClass, cSailNo, cHelmName, cElapsed, cLaps,cCorrected);

                        if (!checkInsertData)
                            throw new Exception("Error while inserting new entry");
                    }

                    Toast.makeText(EnterRaceResult.this, "New Entry Inserted", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), ListSeries.class);
                    startActivity(intent);
                }
                catch (Exception ex) {
                    Toast.makeText(EnterRaceResult.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

// method to populate competitors list
    private ArrayList<EditModel> populateList(){

        ArrayList<EditModel> list = new ArrayList<>();
        Cursor cursor = DB.getCompetitors(raceId);

        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No Data.", Toast.LENGTH_SHORT).show();

        } else {
            int i = 1;
            while (cursor.moveToNext()) {
                EditModel editModel = new EditModel();
                editModel.setTvIndexTwoValue(String.valueOf(i));
                editModel.setTvClassValue(cursor.getString(0));
                editModel.setTvSailNoValue(String.valueOf(cursor.getString(1)));
                editModel.setTvHelmNameValue(cursor.getString(2));
                editModel.setTvPYValue(String.valueOf(cursor.getString(3)));
                list.add(editModel);
                i++;
            }//while
        }//else
        return list;
    }

    // method to calculate elapsed time
    protected String calculateElapsed(String elapsed, int laps,int maxLaps){

        String[] elapsedTokens = elapsed.split(":");
        double hours = Integer.parseInt(elapsedTokens[0])*3600;
        int minutes = Integer.parseInt(elapsedTokens[1])*60;
        int seconds = Integer.parseInt(elapsedTokens[2]);
        double calcInSeconds = ((hours + minutes + seconds)/laps)* maxLaps;
        int correctHours =  (int)(calcInSeconds/3600);

        int remainder1 = (int) (calcInSeconds - (correctHours * 3600));
        int correctMinutes = remainder1/60;
        int remainder2 = remainder1 - (correctMinutes *60);
        int correctSeconds = remainder2;
        String finalHours = String.format("%02d", correctHours);
        String finalMins = String.format("%02d", correctMinutes);
        String finalSecs = String.format("%02d", correctSeconds);
        String elapsedTime = finalHours + ":" + finalMins + ":"+ finalSecs;
        return elapsedTime;
    }

    // method toc calculate corrected time
    protected String calculateCorrected(String elapsed, double PY){

        String[] elapsedTokens = elapsed.split(":");
        double hours = Integer.parseInt(elapsedTokens[0])*3600;
        int minutes = Integer.parseInt(elapsedTokens[1])*60;
        int seconds = Integer.parseInt(elapsedTokens[2]);
        double calcInSeconds = ((hours + minutes + seconds)/PY)*1000;
        int correctHours = (int) (calcInSeconds/3600);
        int remainder1 = (int) (calcInSeconds - (correctHours * 3600));
        int correctMinutes = remainder1/60;
        int remainder2 = remainder1 - (correctMinutes *60);
        int correctSeconds = remainder2;
        String finalHours = String.format("%02d", correctHours);
        String finalMins = String.format("%02d", correctMinutes);
        String finalSecs = String.format("%02d", correctSeconds);
        String correctedTime = finalHours + ":" + finalMins + ":"+ finalSecs;
        return correctedTime;
    }

    // set series name
    void setSeriesName(int raceId) {
        Cursor cursor = DB.getSeriesName(raceId);
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No Data.", Toast.LENGTH_SHORT).show();

        } else {
            while (cursor.moveToNext()) {
                sName = cursor.getString(0);
            }//while
        }//else
    }

    // set no of competitors
    void setNoOFCompetitors(String sName) {
        Cursor cursor = DB.getNoOfCompetitors(sName);
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No Data.", Toast.LENGTH_SHORT).show();

        } else {
            while (cursor.moveToNext()) {
                noOfComp = cursor.getInt(0);
            }//while
        }//else
    }
}
