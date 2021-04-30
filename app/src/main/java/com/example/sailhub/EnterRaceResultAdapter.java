package com.example.sailhub;

import android.content.Context;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.text.TextWatcher;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

/*
Adapter for the recycler view used to display race result
 */
public class EnterRaceResultAdapter extends RecyclerView.Adapter<com.example.sailhub.EnterRaceResultAdapter.MyViewHolder>{

    private LayoutInflater inflater;
    public static ArrayList<EditModel> editModelArrayList;
    // constructor
    public EnterRaceResultAdapter(Context ct, ArrayList<EditModel> editModelArrayList){
        inflater = LayoutInflater.from(ct);
        this.editModelArrayList = editModelArrayList;

    }

    @NonNull
    @Override
    public com.example.sailhub.EnterRaceResultAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // link to row enter result
        View view = inflater.inflate(R.layout.my_row_enter_result,parent,false);
        com.example.sailhub.EnterRaceResultAdapter.MyViewHolder holder = new com.example.sailhub.EnterRaceResultAdapter.MyViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull com.example.sailhub.EnterRaceResultAdapter.MyViewHolder holder, int position) {
        // set values of text views and edit texts
        holder.tvIndexTwo.setText(editModelArrayList.get(position).getTvIndexTwoValue());
        holder.tvDisplayClass.setText(editModelArrayList.get(position).getTvClassValue());
        holder.tvDisplaySailNo.setText(editModelArrayList.get(position).getTvSailNoValue());
        holder.tvDisplayHelmName.setText(editModelArrayList.get(position).getTvHelmNameValue());
        holder.etElapsed.setText(editModelArrayList.get(position).getEtElapsedValue());
        holder.etLaps.setText(editModelArrayList.get(position).getEtLapsValue());
        Log.d("print","yes");
    }

    // determine number of rows to create
    @Override
    public int getItemCount() {
        return editModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView sName,tvIndexTwo,tvDisplayClass,tvDisplaySailNo,tvDisplayHelmName,tvDisplayPY;
        EditText etElapsed,etLaps;
        public MyViewHolder(@NonNull View itemView){

            // link variable to XML object
            super(itemView);
            sName = itemView.findViewById(R.id.tvSeriesNameTwo);
            tvIndexTwo = itemView.findViewById(R.id.tvIndexTwo);
            tvDisplayClass = itemView.findViewById(R.id.tvDisplayClass);
            tvDisplaySailNo = itemView.findViewById(R.id.tvDisplaySailNo);
            tvDisplayHelmName = itemView.findViewById(R.id.tvDisplayHelmName);
            etElapsed = itemView.findViewById(R.id.etElapsed);
            etLaps= itemView.findViewById(R.id.etLaps);


            etElapsed.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    editModelArrayList.get(getAdapterPosition()).setEtElapsedValue(etElapsed.getText().toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            etLaps.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    editModelArrayList.get(getAdapterPosition()).setEtLapsValue(etLaps.getText().toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });


        }//MyViewHolder
    }
}
