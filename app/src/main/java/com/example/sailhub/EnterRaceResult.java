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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_race_result);

        DB = DBHelper.getInstance(this);

        Bundle extras=getIntent().getExtras();
        raceId = extras.getInt("raceId");;
        //get series name
        seriesName = findViewById(R.id.tvSeriesNameTwo);
        setSeriesName(raceId);
        seriesName.setText(sName);
        //get no_of_competitors
        setSeriesName(sName);

        context = this;

        addRaceResult = (Button) findViewById(R.id.btnSubmitResult);

        CompetitorData = populateList();
        EnterRaceResultAdapter myAdapter = new EnterRaceResultAdapter(this,CompetitorData);

        rvEnterResult = findViewById(R.id.rvEnterResult);
        rvEnterResult.setAdapter(myAdapter);
        rvEnterResult.setLayoutManager(new LinearLayoutManager(this));

        addRaceResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int maxLaps = 0;

                    for (int i = 0; i < noOfComp; i++) {
                        View rowView = rvEnterResult.getChildAt(i);
                        EditText laps = (EditText)rowView.findViewById(R.id.etLaps);
                        int cLaps = Integer.parseInt(laps.getText().toString());
                        if (cLaps > maxLaps)
                            maxLaps = cLaps;
                    }

                    for (int i = 0; i < noOfComp; i++) {

                        View rowView = rvEnterResult.getChildAt(i);
                        TextView bClass = (TextView)rowView.findViewById(R.id.tvDisplayClass);
                        TextView SailNo = (TextView)rowView.findViewById(R.id.tvDisplaySailNo);
                        TextView helmName = (TextView)rowView.findViewById(R.id.tvDisplayHelmName);
//                        TextView PY = (TextView)rowView.findViewById(R.id.tvDisplayPY);
                        EditText elapsed = (EditText)rowView.findViewById(R.id.etElapsed);

                        elapsed.setFilters(new InputFilter[] {
                                new RegexInputFilter(context, "^(?:(?:([01]?d|2[0-3]):)?([0-5]?d):)?([0-5]?d)$")
                        });
                        EditText laps = (EditText)rowView.findViewById(R.id.etLaps);


                        String cClass = bClass.getText().toString();
                        int cSailNo = Integer.parseInt(SailNo.getText().toString());
                        String cHelmName = helmName.getText().toString();
                        int cLaps = Integer.parseInt(laps.getText().toString());
                        double cPY = Double.parseDouble(CompetitorData.get(i).getTvPYValue());
                        String tempElapsed = elapsed.getText().toString();
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
                    Toast.makeText(EnterRaceResult.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });


    }


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


    private String calculateElapsed(String elapsed, int laps,int maxLaps){

        String[] elapsedTokens = elapsed.split(":");
        int hours = Integer.parseInt(elapsedTokens[0])*3600;
        int minutes = Integer.parseInt(elapsedTokens[1])*60;
        int seconds = Integer.parseInt(elapsedTokens[2]);
        double calcInSeconds = ((hours + minutes + seconds)/laps)* maxLaps;
        int correctHours = (int) (calcInSeconds/3600);
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

    private String calculateCorrected(String elapsed, double PY){

        String[] elapsedTokens = elapsed.split(":");
        int hours = Integer.parseInt(elapsedTokens[0])*3600;
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
        //String correctedTime =  String.valueOf(correctHours) +":"+ String.valueOf(correctMinutes) +":"+ String.valueOf(correctSeconds);
        String correctedTime = finalHours + ":" + finalMins + ":"+ finalSecs;
        return correctedTime;
    }

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

    void setSeriesName(String sName) {
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


// regex to fix input type
