package com.gym8.customworkout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;

import java.util.ArrayList;
import java.util.List;

import com.gym8.main.R;

/**
 * Created by Gurumukh on 4/3/15.
 */
public class CustomWorkoutDetailsFragment extends Fragment {

    private ListView listView;
    private CustomWorkoutDetailsAdapter adapter;
    private ParseObject selectedWorkout;
    private ArrayList<ParseObject> workoutExercisesList = new ArrayList<>();
    private View rootView;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_custom_workout_details, null);
        listView = (ListView) rootView.findViewById(R.id.custom_details_list);
        return rootView;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState == null)
            getExercises();
    }

    private void onPostExecute() {
        System.out.println(workoutExercisesList.size());
        adapter = new CustomWorkoutDetailsAdapter(getActivity().getApplicationContext(), workoutExercisesList);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    private void getExercises() {

        selectedWorkout=CustomWorkoutFragment.selectedWorkout;
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
}