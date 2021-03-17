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

public class EnterRaceResult extends AppCompatActivity {

    String sName;
    TextView seriesName;
    Button addRaceResult;
    int raceId;
    RecyclerView rvEnterResult;
    DBHelper DB;
    public ArrayList<EditModel> CompetitorData;
    int noOfComp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_race_result);
        //get series name
        seriesName = findViewById(R.id.tvSeriesNameTwo);
        sName = getIntent().getExtras().getString("value");
        seriesName.setText(sName);

        Bundle extras=getIntent().getExtras();
        raceId = extras.getInt("raceId");;

        DB = DBHelper.getInstance(this);

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

                        for (int i = 0; i < noOfComp; i++) {

                            View rowView = rvEnterResult.getChildAt(i);
                            TextView bClass = (TextView)rowView.findViewById(R.id.tvDisplayClass);
                            TextView SailNo = (TextView)rowView.findViewById(R.id.tvDisplaySailNo);
                            TextView helmName = (TextView)rowView.findViewById(R.id.tvDisplayHelmName);
                            EditText elapsed = (EditText)rowView.findViewById(R.id.etElapsed);
                            EditText laps = (EditText)rowView.findViewById(R.id.etLaps);


                            String cClass = bClass.getText().toString();
                            int cSailNo = Integer.parseInt(SailNo.getText().toString());
                            String cHelmName = helmName.getText().toString();
                            int cElapsed = Integer.parseInt(elapsed.getText().toString());
                            int cLaps = Integer.parseInt(laps.getText().toString());



                            Boolean checkInsertData = DB.insertRaceData(raceId, cClass, cSailNo, cHelmName, cElapsed, cLaps);

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


    private ArrayList<EditModel> populateList(){

        ArrayList<EditModel> list = new ArrayList<>();
        noOfComp = 3;
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
                list.add(editModel);
                i++;
            }//while
        }//else
        return list;
    }
}
