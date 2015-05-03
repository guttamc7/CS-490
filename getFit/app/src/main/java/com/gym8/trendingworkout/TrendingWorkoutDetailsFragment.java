package com.gym8.trendingworkout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.gym8.customworkout.CustomWorkoutDetailsAdapter;
import com.gym8.main.R;
import com.gym8.viewpager.RootFragment;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rishabhmittal on 3/29/15.
 */
public class TrendingWorkoutDetailsFragment extends RootFragment {
    private ListView listView;
    private CustomWorkoutDetailsAdapter adapter;
    private ParseObject selectedWorkout;
    private WebView webView;
    private View v;
    private FrameLayout customDetails;
    private FrameLayout baseDetails;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_trending_workout_details, container, false);
        webView = (WebView) v.findViewById(R.id.webView_trending);
        listView = (ListView) v.findViewById(R.id.trending_workout_details_list);
        this.selectedWorkout = TrendingWorkoutFragment.getSelectedTrendingWorkout();
        customDetails = (FrameLayout) v.findViewById(R.id.frag_trending_detail);
        baseDetails = (FrameLayout) v.findViewById(R.id.frag_trending_webview);
        if (this.selectedWorkout.getString("workoutType").equals("custom")) {
            baseDetails.setVisibility(View.GONE);
            customDetails.setVisibility(View.VISIBLE);
        } else {
            customDetails.setVisibility(View.INVISIBLE);
            baseDetails.setVisibility(View.VISIBLE);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setDomStorageEnabled(true);
            webView.loadUrl(this.selectedWorkout.getString("workoutUrl"));
        }
        return v;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState == null && this.selectedWorkout.getString("workoutType").equals("custom"))
            getExercises();
    }

    private void getExercises() {
        ParseRelation<ParseObject> relation = this.selectedWorkout.getRelation("exercises");
        ParseQuery<ParseObject> query = relation.getQuery();
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> workoutList, ParseException e) {
                if (e == null) {
                    adapter = new CustomWorkoutDetailsAdapter(getActivity().getApplicationContext(), workoutList);
                    listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
                else{
                    Toast.makeText(getActivity(), "Connection Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

