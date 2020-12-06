package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

class Myadapter extends BaseAdapter {
    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<SampleData> sample;

    public Myadapter(Context context, ArrayList<SampleData> data) {
        mContext = context;
        sample = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return sample.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public SampleData getItem(int position) {
        return sample.get(position);
    }

    @Override
    public View getView(int position, View converView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.time_list, null);

        TextView aDate = (TextView)view.findViewById(R.id.aDate);
        TextView aTime = (TextView)view.findViewById(R.id.aTime);

        aDate.setText(sample.get(position).getAvail_Date());
        aTime.setText(sample.get(position).getAvail_Time());

        return view;
    }
}

