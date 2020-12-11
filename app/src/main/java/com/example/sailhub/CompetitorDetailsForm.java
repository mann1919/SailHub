package com.example.sailhub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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
    EditText Class,PY,sailNo,helmName,crewName;
    Button addCompetitor;
    RecyclerView rvCompetitors;
    DBHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competitor_details_form);


        //get series name
        seriesName = findViewById(R.id.tvSeriesName);
        sName = getIntent().getExtras().getString("value");
        seriesName.setText(sName);

        //the inputs
        Class = (EditText)findViewById(R.id.etClass);
        PY = (EditText)findViewById(R.id.etPY);
        sailNo = (EditText) findViewById(R.id.etSailNo);
        helmName = (EditText) findViewById(R.id.etHelmName);
        crewName = (EditText) findViewById(R.id.etCrewName);

        rvCompetitors = findViewById(R.id.rvCompetitors);

        DB = DBHelper.getInstance(this);

        addCompetitor = (Button) findViewById(R.id.btnAddCompetitors);

        CompetitorDetailAdapter myAdapter = new CompetitorDetailAdapter(this);

        rvCompetitors.setAdapter(myAdapter);
        rvCompetitors.setLayoutManager(new LinearLayoutManager(this));

        addCompetitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cClass = Class.getText().toString();
                int cPY = Integer.parseInt(PY.getText().toString());
                int cSailNo = Integer.parseInt(sailNo.getText().toString());
                String cHelmName = helmName.getText().toString();
                String cCrewName = crewName.getText().toString();

                Boolean checkInsertData = DB.insertCompetitorData(cClass, cPY, cSailNo, cHelmName, cCrewName);

                if(checkInsertData==true) {
                    Toast.makeText(CompetitorDetailsForm.this, "New Entry Inserted", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), ListSeries.class);

                    startActivity(intent);
                }//if
                else
                    Toast.makeText(CompetitorDetailsForm.this, "New Entry Not Inserted, try again", Toast.LENGTH_SHORT).show();
            }
        });


    }
}