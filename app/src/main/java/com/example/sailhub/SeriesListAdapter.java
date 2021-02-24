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

    ArrayList<String> seriesList;
    Context context;
    private OnSeriesListener seriesListener;

    public SeriesListAdapter(Context ct, ArrayList data,OnSeriesListener seriesListener){
        context = ct;
        this.seriesList =  data;
        this.seriesListener = seriesListener;
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
        holder.sName.setText(String.valueOf(seriesList.get(position)));

    }

    @Override
    public int getItemCount() {
        return seriesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView sName;
        OnSeriesListener onSeriesListener;
        public MyViewHolder(@NonNull View itemView, OnSeriesListener onSeriesListener){
            super(itemView);
            sName = itemView.findViewById(R.id.tvSeriesName);
            this.onSeriesListener = onSeriesListener;

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            onSeriesListener.onSeriesClick(getAdapterPosition());
        }
    }

    public interface OnSeriesListener{
        void onSeriesClick(int position);
    }

}
