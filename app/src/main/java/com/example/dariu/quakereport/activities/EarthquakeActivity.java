package com.example.dariu.quakereport.activities;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dariu.quakereport.Interfaces.RecyclerViewItemClickListener;
import com.example.dariu.quakereport.adapters.EarthquakeAdapterWithRecyclerViewAdapter;
import com.example.dariu.quakereport.pojos.Earthquake;
import com.example.dariu.quakereport.R;
import com.example.dariu.quakereport.utils.QueryUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EarthquakeActivity extends AppCompatActivity {

    //Constant value for the earthquake loader ID. We can choose any integer.
    private static final int EARTHQUAKE_LOADER_ID = 1;
    public static final String LOG_TAG = EarthquakeActivity.class.getSimpleName();
    public static final String USGS_REQUEST_URL =  "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=6&limit=10";

    private EarthquakeAdapterWithRecyclerViewAdapter mAdapter;
    private RecyclerView mItemsList;



    @BindView(R.id.empty_view)
    TextView mEmptyStateTextView;

    @BindView(R.id.list_of_items)
    ListView earthquakeListView;

    @BindView(R.id.loading_indicator)
    View loadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        List<Earthquake> earthquakes = QueryUtils.fetchEarthquakeData(USGS_REQUEST_URL);

        mItemsList = (RecyclerView) findViewById(R.id.list_of_items);

        mAdapter = new EarthquakeAdapterWithRecyclerViewAdapter(this, earthquakes);

        mItemsList.setAdapter(mAdapter);
        mItemsList.setLayoutManager(new LinearLayoutManager(this));




        ButterKnife.bind(this);

        // mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        earthquakeListView.setEmptyView(mEmptyStateTextView);

        // Set an item click listener on the ListView, which sends an intent to a web browser
        // to open a website with more information about the selected earthquake.

        class CustomRVItemTouchListener implements RecyclerView.OnItemTouchListener {

            //GestureDetector to intercept touch events
            GestureDetector gestureDetector;
            private RecyclerViewItemClickListener clickListener;

            public CustomRVItemTouchListener(Context context, final RecyclerView recyclerView, final RecyclerViewItemClickListener clickListener) {
                this.clickListener = clickListener;
                gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {

                    @Override
                    public boolean onSingleTapUp(MotionEvent e) {
                        return true;
                    }

                    @Override
                    public void onLongPress(MotionEvent e) {
                        //find the long pressed view
                        View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                        if (child != null && clickListener != null) {
                            clickListener.onLongClick(child, recyclerView.getChildLayoutPosition(child));
                        }
                    }
                });
            }

            @Override
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent e) {

                View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                    clickListener.onClick(child, recyclerView.getChildLayoutPosition(child));
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        }

        mItemsList.addOnItemTouchListener(new CustomRVItemTouchListener(this, mItemsList, new RecyclerViewItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                // Find the current earthquake that was clicked on
                Earthquake currentEarthquake = mAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri earthquakeUri = Uri.parse(currentEarthquake.getUrl());

                // Create a new intent to view the earthquake URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }
}
