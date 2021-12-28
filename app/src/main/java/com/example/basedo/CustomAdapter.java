package com.example.basedo;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private List<Data> localDataSet;
    Activity act=null;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private  TextView IdView;
        private  TextView LnameView;
        private  TextView FnameView;
        private ImageButton but;
        public ViewHolder(View view) {
            super(view);
            IdView = (TextView) view.findViewById(R.id.IdView);
            LnameView = (TextView) view.findViewById(R.id.LnameV);
            FnameView=(TextView) view.findViewById(R.id.FnameV);
            but=(ImageButton) view.findViewById(R.id.DeleteRec);
        }

        public ImageButton getBut() {
            return but;
        }
        public TextView getIdView() {
            return IdView;
        }
        public TextView getLnameView(){
            return LnameView;
        }
        public TextView getFnameView(){
            return FnameView;
        }
    }
    public CustomAdapter(List<Data> dataSet) {
        localDataSet = dataSet;
    }


    public CustomAdapter(List<Data> localDataSet, RecyclerView layout) {
        this.localDataSet = localDataSet;
        layout.setAdapter(this);
    }

    public CustomAdapter( List<Data> localDataSet, RecyclerView layout, Activity act) {
        this.localDataSet = localDataSet;
        this.act = act;
        layout.setAdapter(this);
    }


    public List<Data> getLocalDataSet() {
        return localDataSet;
    }

    public void setLocalDataSet(List<Data> localDataSet) {
        this.localDataSet = localDataSet;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.comp_list_insc_card, viewGroup, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.getIdView().setText( Integer.toString(localDataSet.get(position).getId()));
        viewHolder.getLnameView().setText(localDataSet.get(position).getLname());
        viewHolder.getFnameView().setText(localDataSet.get(position).getFname());
        viewHolder.itemView.setId(localDataSet.get(position).getId());
    }
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
