package com.example.sailhub;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CompetitorDetailAdapter extends RecyclerView.Adapter<CompetitorDetailAdapter.MyViewHolder>{

    private LayoutInflater inflater;
    public static ArrayList<EditModel> editModelArrayList;
    public CompetitorDetailAdapter(Context ct, ArrayList<EditModel> editModelArrayList){
        inflater = LayoutInflater.from(ct);
        this.editModelArrayList = editModelArrayList;

    }

    @NonNull
    @Override
    public CompetitorDetailAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.my_row_competitor,parent,false);
        //return new CompetitorDetailAdapter.MyViewHolder(view);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull CompetitorDetailAdapter.MyViewHolder holder, int position) {

        holder.etClass.setText(editModelArrayList.get(position).getEditTextValue());
        Log.d("print","yes");
    }

    @Override
    public int getItemCount() {
        return editModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView sName,tvIndex;
        EditText etClass,etPY,etSailNo,etHelmName,etCrewName;
        public MyViewHolder(@NonNull View itemView){

            super(itemView);
            sName = itemView.findViewById(R.id.tvSeriesName);
            tvIndex = itemView.findViewById(R.id.tvIndex);
            etClass = itemView.findViewById(R.id.etClass);
            etPY = itemView.findViewById(R.id.etPY);
            etSailNo = itemView.findViewById(R.id.etSailNo);
            etHelmName = itemView.findViewById(R.id.etHelmName);
            etCrewName= itemView.findViewById(R.id.etCrewName);

        }


    }
}
