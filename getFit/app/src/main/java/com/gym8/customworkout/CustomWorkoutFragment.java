package com.gym8.customworkout;

/**
 * Created by Gurumukh on 2/10/15.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.gym8.createworkout.CreateWorkoutInformationDialog;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import com.gym8.main.R;

public class CustomWorkoutFragment extends Fragment {
    private FloatingActionButton createWorkoutButton;
    private ArrayList<ParseObject> customWorkoutList = new ArrayList<>();
    private ListView listView;
    private CustomWorkoutAdapter adapter;
    public static ParseObject selectedWorkout;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_customworkout, container, false);
        listView = (ListView) rootView.findViewById(R.id.custom_workout_list);
        createWorkoutButton = (FloatingActionButton) rootView.findViewById(R.id.create_workout_fab);
        createWorkoutButton.setSize(FloatingActionButton.SIZE_MINI);
        createWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateWorkoutInformationDialog newFragment = CreateWorkoutInformationDialog.newInstance();
                newFragment.show(getFragmentManager(), "dialog");


            }
        });
        return rootView;
    }

    public CustomWorkoutFragment() {
        // Auto-generated constructor stub
    }
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getWorkouts();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                CustomWorkoutDetailsFragment customDetailsFrag = new CustomWorkoutDetailsFragment();
                selectedWorkout = (ParseObject)listView.getItemAtPosition(position);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.add(R.id.content_frame, customDetailsFrag);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.addToBackStack("Custom_Workout_List");
                ft.commit();
            }
        });
    }

    public void onResume() {
        super.onResume();

    }

    public static CustomWorkoutFragment newInstance() {
        return new CustomWorkoutFragment();
    }

    private void getWorkouts() {
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Workout");
            query.whereEqualTo("userId", ParseUser.getCurrentUser());
            query.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> workoutList, ParseException e) {
                    if (e == null) {
                        customWorkoutList.addAll(workoutList);
                      }
                     else {
                    }
                    onPostExecute();
                }
            });

        }

        protected void onPostExecute() {
            adapter = new CustomWorkoutAdapter(getActivity().getApplicationContext(), customWorkoutList);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        }


}
