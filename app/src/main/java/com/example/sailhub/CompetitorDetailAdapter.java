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
The competitor details form uses recycler view
to take input for each competitor.
This is the adapter for that recycler view
 */
public class CompetitorDetailAdapter extends RecyclerView.Adapter<CompetitorDetailAdapter.MyViewHolder>{

    private LayoutInflater inflater;
    public static ArrayList<EditModel> editModelArrayList;
    // Constructor
    public CompetitorDetailAdapter(Context ct, ArrayList<EditModel> editModelArrayList){
        inflater = LayoutInflater.from(ct);
        this.editModelArrayList = editModelArrayList;

    }

    @NonNull
    @Override
    public CompetitorDetailAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // link the row competitor creaetd
        View view = inflater.inflate(R.layout.my_row_competitor,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull CompetitorDetailAdapter.MyViewHolder holder, int position) {
        // set the values for the edit texts and text views
        holder.tvIndex.setText(editModelArrayList.get(position).getTvIndexValue());
        holder.etClass.setText(editModelArrayList.get(position).getEtClassValue());
        holder.etPY.setText(editModelArrayList.get(position).getEtPYValue());
        holder.etSailNo.setText(editModelArrayList.get(position).getEtSailNoValue());
        holder.etHelmName.setText(editModelArrayList.get(position).getEtHelmNameValue());
        holder.etCrewName.setText(editModelArrayList.get(position).getEtCrewNameValue());
        Log.d("print","yes");
    }

    @Override
    public int getItemCount() {
        //used to determine how many rows to generate
        return editModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView sName,tvIndex;
        EditText etClass,etPY,etSailNo,etHelmName,etCrewName;
        public MyViewHolder(@NonNull View itemView){

            super(itemView);
            //linking the variable to the element in XML
            sName = itemView.findViewById(R.id.tvSeriesName);
            tvIndex = itemView.findViewById(R.id.tvIndex);
            etClass = itemView.findViewById(R.id.etClass);
            etPY = itemView.findViewById(R.id.etPY);
            etSailNo = itemView.findViewById(R.id.etSailNo);
            etHelmName = itemView.findViewById(R.id.etHelmName);
            etCrewName= itemView.findViewById(R.id.etCrewName);

            // text watcher to save data even when user scroll in recycler view
            etClass.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    editModelArrayList.get(getAdapterPosition()).setEtClassValue(etClass.getText().toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            etPY.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    editModelArrayList.get(getAdapterPosition()).setEtPYValue(etPY.getText().toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            etSailNo.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    editModelArrayList.get(getAdapterPosition()).setEtSailNoValue(etSailNo.getText().toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            etHelmName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    editModelArrayList.get(getAdapterPosition()).setEtHelmNameValue(etHelmName.getText().toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            etCrewName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    editModelArrayList.get(getAdapterPosition()).setEtCrewNameValue(etCrewName.getText().toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

        }//MyViewHolder

    }
}
