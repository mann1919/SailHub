package com.example.sailhub;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;


import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.listeners.TableDataClickListener;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

public class DisplayRaceResult extends AppCompatActivity {

    String[] columnHeaders={"Rank","Class","SailNo","HelmName","CrewName","PY","Elapsed","Laps","Corrected","Points"};
    String[][] records;
    String sName;
    TextView seriesName;
    DBHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_race_result);

        seriesName = findViewById(R.id.tvNameOfSeries);
        sName = getIntent().getExtras().getString("nameOfSeries");
        seriesName.setText(sName);
        final TableView<String[]> tb = (TableView<String[]>) findViewById(R.id.tableView);
        tb.setColumnCount(columnHeaders.length);
        tb.setHeaderBackgroundColor(Color.parseColor("#E4D5B3"));

        DB = DBHelper.getInstance(this);
        //POPULATE
        populateData();

        //ADAPTERS
        tb.setHeaderAdapter(new SimpleTableHeaderAdapter(this,columnHeaders));
        tb.setDataAdapter(new SimpleTableDataAdapter(this, records));

            /*
            tb.addDataClickListener(new TableDataClickListener() {
                @Override
                public void onDataClicked(int rowIndex, Object clickedData) {
                    Toast.makeText(DisplayRaceResult.this, ((String[])clickedData)[1], Toast.LENGTH_SHORT).show();
                }
            });
             */

        }
        private void populateData()
        {
            //CompetitorData competitor= new CompetitorData();
            //ArrayList<RaceRecord> competitorsList=new ArrayList<>();

            ArrayList<CompetitorData> competitorsList = new ArrayList<>();

            Cursor cursor = DB.readRaceResult(sName,1);

                while (cursor.moveToNext()) {

                    int rank = 0;
                    String bClass = cursor.getString(0);
                    int sailNo = Integer.parseInt(cursor.getString(1));
                    String helmName = cursor.getString(2);
                    String crewName = cursor.getString(3);
                    int PY = Integer.parseInt(cursor.getString(4));
                    int elapsed = cursor.getString(5) == null ? -1 : Integer.parseInt(cursor.getString(5));
                    int laps = cursor.getString(6) == null ? -1 : Integer.parseInt(cursor.getString(6));
                    String corrected = "correct";
                    int points = 0;
                    CompetitorData competitor = new CompetitorData(rank, bClass, sailNo, helmName, crewName, PY, elapsed, laps, corrected, points);
                    competitorsList.add(competitor);
                }

                records = new String[competitorsList.size()][columnHeaders.length];

                for (int i = 0; i < competitorsList.size(); i++) {

                    CompetitorData s = competitorsList.get(i);


                    records[i][0] = String.valueOf(s.getRank());
                    records[i][1] = s.getBoatClass();
                    records[i][2] = String.valueOf(s.getSailNo());
                    records[i][3] = s.getHelmName();
                    records[i][4] = s.getCrewName();
                    records[i][5] = String.valueOf(s.getPY());
                    records[i][6] = s.getElapsed() == -1 ? "--" : String.valueOf(s.getElapsed());
                    records[i][7] = s.getLaps() == -1 ? "--" : String.valueOf(s.getElapsed());
                    records[i][8] = s.getCorrected();
                    records[i][9] = String.valueOf(s.getPoints());
                }//for

        }//populate
}