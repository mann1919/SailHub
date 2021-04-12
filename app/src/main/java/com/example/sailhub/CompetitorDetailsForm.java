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

public class CompetitorDetailsForm extends AppCompatActivity {
    String sName;
    TextView seriesName;
    Button addCompetitor;
    int noOfComp;
    RecyclerView rvCompetitors;
    DBHelper DB;
    public ArrayList<EditModel> editModelArrayList;
    public ArrayList<Integer> raceIds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competitor_details_form);

        //get series name
        seriesName = findViewById(R.id.tvSeriesName);
        sName = getIntent().getExtras().getString("value");
        seriesName.setText(sName);

        DB = DBHelper.getInstance(this);
        raceIds = new ArrayList<>();

        addCompetitor = (Button) findViewById(R.id.btnAddCompetitors);

        editModelArrayList = populateList();
        CompetitorDetailAdapter myAdapter = new CompetitorDetailAdapter(this,editModelArrayList);

        rvCompetitors = findViewById(R.id.rvCompetitors);
        rvCompetitors.setAdapter(myAdapter);
        rvCompetitors.setLayoutManager(new LinearLayoutManager(this));

        addCompetitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    getRId(sName);

                    for (int raceId : raceIds) {
                        for (int i = 0; i < noOfComp; i++) {

                            View rowView = rvCompetitors.getChildAt(i);
                            EditText bClass = (EditText)rowView.findViewById(R.id.etClass);
                            EditText PY = (EditText)rowView.findViewById(R.id.etPY);
                            EditText sailNo = (EditText)rowView.findViewById(R.id.etSailNo);
                            EditText helmName = (EditText)rowView.findViewById(R.id.etHelmName);
                            EditText crewName = (EditText)rowView.findViewById(R.id.etCrewName);


                            String cClass = bClass.getText().toString();
                            int cPY = Integer.parseInt(PY.getText().toString());
                            int cSailNo = Integer.parseInt(sailNo.getText().toString());
                            String cHelmName = helmName.getText().toString();
                            String cCrewName = crewName.getText().toString();
                            if(cClass.equals("")){
                                Toast.makeText(CompetitorDetailsForm.this, "Enter Class", Toast.LENGTH_SHORT).show();
                                return;
                            }//if
                            else if(PY.length()==0){
                                Toast.makeText(CompetitorDetailsForm.this, "Enter PY", Toast.LENGTH_SHORT).show();
                                return;
                            }//if
                            else if(sailNo.length()==0){
                                Toast.makeText(CompetitorDetailsForm.this, "Enter PY", Toast.LENGTH_SHORT).show();
                                return;
                            }//if
                            else if(cHelmName.equals("")){
                                Toast.makeText(CompetitorDetailsForm.this, "Enter Helm Name", Toast.LENGTH_SHORT).show();
                                return;
                            }//if

                            Boolean checkInsertData = DB.insertCompetitorData(raceId, cClass, cPY, cSailNo, cHelmName, cCrewName);

                            if (!checkInsertData)
                                throw new Exception("Error while inserting new entry");

                        }
                    }

                    Toast.makeText(CompetitorDetailsForm.this, "New Entry Inserted", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), ListSeries.class);
                    startActivity(intent);
                }
                catch (Exception ex) {
                    Toast.makeText(CompetitorDetailsForm.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

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
    private ArrayList<EditModel> populateList(){

        ArrayList<EditModel> list = new ArrayList<>();
        noOfComp = getIntent().getIntExtra("compNo",0);

        for(int i = 1; i <=noOfComp; i++){

            EditModel editModel = new EditModel();
            editModel.setTvIndexValue(String.valueOf(i));
            list.add(editModel);
        }

        return list;
    }
}