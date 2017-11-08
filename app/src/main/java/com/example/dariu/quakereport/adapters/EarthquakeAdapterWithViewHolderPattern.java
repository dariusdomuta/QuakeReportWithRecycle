/*package com.example.dariu.quakereport.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.dariu.quakereport.pojos.Earthquake;

import java.util.List;



import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.dariu.quakereport.pojos.Earthquake;

import java.util.List;

public class EarthquakeAdapterWithViewHolderPattern extends BaseAdapter {

    private final Context mContext;
    private final List<Earthquake> mEarthquakes;

    public EarthquakeAdapterWithViewHolderPattern(Context context, List<Earthquake> earthquakes) {
        mContext = context;
        mEarthquakes = earthquakes;
    }

    @Override
    public int getCount() {
        if (mEarthquakes != null) {
            return mEarthquakes.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int i) {
        if (mEarthquakes != null && mEarthquakes.size() > i) {
            return mEarthquakes.get(i);
        }
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }

}*/