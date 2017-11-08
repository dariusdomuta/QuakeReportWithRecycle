package com.example.dariu.quakereport.Interfaces;

import android.view.View;

/**
 * Created by dariu on 11/8/2017.
 */

public interface RecyclerViewItemClickListener {
    public void onClick(View view, int position);

    public void onLongClick(View view, int position);
}
