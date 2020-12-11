package com.example.sailhub;

import androidx.annotation.NonNull;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SeriesListAdapter extends RecyclerView.Adapter<SeriesListAdapter.MyViewHolder>{

    ArrayList<String> data;
    Context context;
    public SeriesListAdapter(Context ct, ArrayList data){
        context = ct;
        this.data =  data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row_series,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.sName.setText(String.valueOf(data.get(position)));

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView sName;
        public MyViewHolder(@NonNull View itemView){

            super(itemView);
            sName = itemView.findViewById(R.id.tvSeriesName);
        }


    }
}
