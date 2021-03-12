package com.example.sailhub;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.telephony.PreciseDataConnectionState;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

public class RaceResultAdapter extends RecyclerView.Adapter<RaceResultAdapter.MyViewHolder> {

    private String[] columnHeaders = {"Rank", "Class", "SailNo", "Helm", "Crew", "PY", "Elapsed", "Laps", "Corrected", "Points"};
    private LayoutInflater inflater;
    private DBHelper DB;
    private Context context;

    public static ArrayList<Integer> raceIds;

    RaceResultAdapter(Context context, String sName){
        this.DB = DBHelper.getInstance(context);
        this.raceIds = getRIds(sName);
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row_race, parent,false);
        RaceResultAdapter.MyViewHolder holder = new RaceResultAdapter.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        int raceId = raceIds.get(position);
        TableView tb = holder.raceTableView;

        //create table view
        tb.setColumnCount(columnHeaders.length);
        tb.setHeaderBackgroundColor(Color.parseColor("#E4D5B3"));

        //POPULATE
        String[][] tableData = getTableData(raceId);

        //ADAPTERS
        SimpleTableHeaderAdapter headerAdapter = new SimpleTableHeaderAdapter(context, columnHeaders);
        headerAdapter.setPaddings(5, 0, 2, 10);
        tb.setHeaderAdapter(headerAdapter);

        SimpleTableDataAdapter dataAdapter = new SimpleTableDataAdapter(context, tableData);
        dataAdapter.setPaddings(0, 0, 5, 10);
        tb.setDataAdapter(dataAdapter);
    }

    private String[][] getTableData(int raceId) {
        ArrayList<CompetitorData> competitorsList = new ArrayList<>();
        Cursor cursor = DB.readRaceResult(raceId);
        int rankCount = 1;
        while (cursor.moveToNext()) {
            int rank = rankCount;
            String bClass = cursor.getString(0);
            int sailNo = Integer.parseInt(cursor.getString(1));
            String helmName = cursor.getString(2);
            String crewName = cursor.getString(3) == null ? "" :cursor.getString(3) ;
            int PY = Integer.parseInt(cursor.getString(4));
            int elapsed = cursor.getString(5) == null ? -1 : Integer.parseInt(cursor.getString(5));
            int laps = cursor.getString(6) == null ? -1 : Integer.parseInt(cursor.getString(6));
            int corrected = cursor.getString(7) == null ? -1 : Integer.parseInt(cursor.getString(7));
            int points = rankCount;

            rankCount++;
            CompetitorData competitor = new CompetitorData(rank, bClass, sailNo, helmName, crewName, PY, elapsed, laps, corrected, points);
            competitorsList.add(competitor);
        }

        String[][] records = new String[competitorsList.size()][columnHeaders.length];
        for (int i = 0; i < competitorsList.size(); i++) {
            CompetitorData s = competitorsList.get(i);
            records[i][0] = String.valueOf(s.getRank());
            records[i][1] = s.getBoatClass();
            records[i][2] = String.valueOf(s.getSailNo());
            records[i][3] = s.getHelmName();
            records[i][4] = s.getCrewName() == "" ? "--" : s.getCrewName();
            records[i][5] = String.valueOf(s.getPY());
            records[i][6] = s.getElapsed() == -1 ? "--" : String.valueOf(s.getElapsed());
            records[i][7] = s.getLaps() == -1 ? "--" : String.valueOf(s.getLaps());
            records[i][8] = s.getCorrected() == -1 ? "--" : String.valueOf(s.getCorrected());
            records[i][9] = String.valueOf(s.getPoints());
        }//for

        return records;
    }//populate

    @Override
    public int getItemCount() {
        return raceIds.size();
    }

    private ArrayList<Integer> getRIds(String sName) {
        ArrayList<Integer> raceIds = new ArrayList<>();
        Cursor cursor = DB.getRaceIds(sName);
        if (cursor.getCount() == 0) {
            //Toast.makeText(this, "Error occurred", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                raceIds.add(cursor.getInt(0));
            }//while
        }//else
        return raceIds;
    }//getRID

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TableView raceTableView;
        //public Button addResult;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            raceTableView = itemView.findViewById(R.id.tableView);
            //addResult = itemView.findViewById(R.id.tvSeriesName);
        }
    }
}

