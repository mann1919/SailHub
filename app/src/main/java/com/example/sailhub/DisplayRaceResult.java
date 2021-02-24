package com.example.sailhub;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;


import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.listeners.TableDataClickListener;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

public class DisplayRaceResult extends AppCompatActivity {

    String[] columnHeaders={"Rank","SailNo","HelmName","CrewName","PY","Elapsed","Laps","Corrected","Points"};
    String[][] records;
    String sName;
    TextView seriesName;
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
            RaceRecord competitor=new RaceRecord();
            ArrayList<RaceRecord> competitorsList=new ArrayList<>();



            //competitor.setId("1");
            competitor.setRank("");
            competitor.setSailNo("123");
            competitor.setHelmName("Tod M");
            competitor.setCrewName("Venus");
            competitor.setPY("143");
            competitor.setElapsed("");
            competitor.setLaps("");
            competitor.setCorrected("");
            competitor.setPoints("");
            competitorsList.add(competitor);

            competitor=new RaceRecord();
           // competitor.setId("2");
            competitor.setRank("");
            competitor.setSailNo("456");
            competitor.setHelmName("Philip");
            competitor.setCrewName("Phi");
            competitor.setPY("193");
            competitor.setElapsed("");
            competitor.setLaps("");
            competitor.setCorrected("");
            competitor.setPoints("");
            competitorsList.add(competitor);

            competitor=new RaceRecord();
            //competitor.setId("3");
            competitor.setRank("");
            competitor.setSailNo("789");
            competitor.setHelmName("Tom C");
            competitor.setCrewName("Bass");
            competitor.setPY("172");
            competitor.setElapsed("");
            competitor.setLaps("");
            competitor.setCorrected("");
            competitor.setPoints("");
            competitorsList.add(competitor);

            competitor=new RaceRecord();
            //competitor.setId("4");
            competitor.setRank("");
            competitor.setSailNo("101");
            competitor.setHelmName("Boss M");
            competitor.setCrewName("");
            competitor.setPY("187");
            competitor.setElapsed("");
            competitor.setLaps("");
            competitor.setCorrected("");
            competitor.setPoints("");
            competitorsList.add(competitor);

            records= new String[competitorsList.size()][columnHeaders.length];


            for (int i=0;i<competitorsList.size();i++) {

                RaceRecord s=competitorsList.get(i);

               // records[i][0]=s.getId();
                records[i][0]=s.getRank();
                records[i][1]=s.getSailNo();
                records[i][2]=s.getHelmName();
                records[i][3]=s.getCrewName();
                records[i][4]=s.getPY();
                records[i][5]=s.getElapsed();
                records[i][6]=s.getLaps();
                records[i][7]=s.getCorrected();
                records[i][8]=s.getPoints();

            }

        }
}