package com.gym8.trendingworkout;

/**
 * Created by Gurumukh on 2/4/15.
 */


import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

import com.gym8.main.R;
import com.gym8.viewpager.RootFragment;

public class TrendingWorkoutFragment extends RootFragment {
    public final static String TAG = TrendingWorkoutFragment.class.getSimpleName();
    private ListView listViewTrendingWorkout;
    private View v;
    private static ParseObject selectedTrendingWorkout;
    private TrendingWorkoutAdapter adapter;

    public static TrendingWorkoutFragment newInstance() {
        return new TrendingWorkoutFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_trending, container, false);
        listViewTrendingWorkout = (ListView) v.findViewById(R.id.trending_list);
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getTrendingWorkouts();
        listViewTrendingWorkout.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TrendingWorkoutFragment.selectedTrendingWorkout = (ParseObject) listViewTrendingWorkout.getItemAtPosition(position);
                FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                ft.replace(R.id.frag_trending, new TrendingWorkoutDetailsFragment());
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
    }

    static ParseObject getSelectedTrendingWorkout() {
        return selectedTrendingWorkout;
    }

    public void onResume() {
        super.onResume();
    }


    private void getTrendingWorkouts() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Workout");
        query.orderByDescending("likes");
        query.findInBackground(new FindCallback<ParseObject>() {

            public void done(List<ParseObject> workoutList, ParseException e) {
                if (e == null) {
                    adapter = new TrendingWorkoutAdapter(getActivity().getApplicationContext(), workoutList);
                    listViewTrendingWorkout.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
                else{
                    Toast.makeText(getActivity(), "Connection Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}