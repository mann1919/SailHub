package com.example.sailhub;

import androidx.annotation.NonNull;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SeriesListAdapter extends RecyclerView.Adapter<SeriesListAdapter.MyViewHolder>{

    ArrayList<String> seriesList;
    Context context;
    private OnSeriesListener seriesListener;

    public SeriesListAdapter(Context ct, ArrayList data,OnSeriesListener sListener){
        context = ct;
        this.seriesList =  data;
        seriesListener = sListener;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row_series,parent,false);
        return new MyViewHolder(view,seriesListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String currentItem = seriesList.get(position);
        holder.sName.setText(String.valueOf(currentItem));
        holder.imgDelete.setImageResource(R.drawable.ic_delete);
    }

    @Override
    public int getItemCount() {
        return seriesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView sName;
        ImageView imgDelete;
        OnSeriesListener onSeriesListener;
        public MyViewHolder(@NonNull View itemView, OnSeriesListener onSeriesListener){
            super(itemView);
            sName = itemView.findViewById(R.id.tvSeriesName);
            imgDelete = itemView.findViewById(R.id.imgDelete);
            this.onSeriesListener = onSeriesListener;
            sName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSeriesListener.onSeriesClick(getAdapterPosition());
                }
            });
            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSeriesListener.onDeleteClick(getAdapterPosition());
                }
            });
        }
        
    }

    public interface OnSeriesListener{
        void onSeriesClick(int position);
        void onDeleteClick(int position);
    }


}
