package com.gym8.trendingworkout;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.gym8.baseworkout.BaseWorkoutFragment;
import com.gym8.customworkout.CustomWorkoutAdapter;
import com.gym8.customworkout.CustomWorkoutDetailsAdapter;
import com.gym8.customworkout.CustomWorkoutDetailsFragment;
import com.gym8.customworkout.CustomWorkoutFragment;
import com.gym8.main.R;
import com.gym8.viewpager.RootFragment;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rishabhmittal on 3/29/15.
 */
public class TrendingWorkoutDetailsFragment extends RootFragment {
    private ListView listView;
    private CustomWorkoutDetailsAdapter adapter;
    private ParseObject selectedWorkout;
    private ArrayList<ParseObject> workoutExercisesList = new ArrayList<>();
    private WebView webView;
    View v;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_trending_workout_details, container, false);
        webView = (WebView) v.findViewById(R.id.webView);
        listView = (ListView) v.findViewById(R.id.trending_workout_details_list);
        if (TrendingWorkoutFragment.trendingWorkout.getString("workoutType").equals("custom")) {
            webView.setVisibility(View.INVISIBLE);


        } else {
            listView.setVisibility(View.INVISIBLE);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.loadUrl(TrendingWorkoutFragment.trendingWorkout.getString("workoutUrl"));

        }
        return v;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState == null)
            getExercises();
    }


    private void getExercises() {

        selectedWorkout = TrendingWorkoutFragment.trendingWorkout;
        ParseRelation<ParseObject> relation = selectedWorkout.getRelation("exercises");
        ParseQuery<ParseObject> query = relation.getQuery();
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> workoutList, ParseException e) {
                if (e == null) {

                    workoutExercisesList.addAll(workoutList);


                } else {
                    System.out.println(e.getMessage());
                }
                onPostExecute();
            }
        });

    }

    private void onPostExecute() {
        adapter = new CustomWorkoutDetailsAdapter(getActivity().getApplicationContext(), workoutExercisesList);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }
}

