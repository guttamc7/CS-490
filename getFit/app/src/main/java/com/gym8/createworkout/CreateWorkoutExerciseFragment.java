package com.gym8.createworkout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.support.v4.app.Fragment;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;

import com.gym8.main.R;

/**
 * Created by Gurumukh on 3/30/15.
 */
public class CreateWorkoutExerciseFragment extends Fragment {
    private View rootView;
    private Button doneButton;
    private ListView listView;
    private CreateWorkoutExerciseAdapter adapter;
    private FloatingActionButton addExerciseButton;
    private TextView emptyList;
    private String workoutName;
    private String workoutDescription;
    private ViewGroup contain;
    private static CreateWorkoutDialog.CalendarPageFragmentListener listener;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_create_workout_exercise, null);
        listView = (ListView) rootView.findViewById(R.id.create_workout_exercises_list);
        emptyList = (TextView) rootView.findViewById(R.id.empty_list);
        doneButton = (Button) rootView.findViewById(R.id.createWorkout_Button);
        contain = container;
//        workoutName = container.getTag(0).toString();
  //      workoutDescription = container.getTag(1).toString();
        if(ExerciseListDetailsDialog.exerciseWorkoutList!=null){
            emptyList.setVisibility(View.INVISIBLE);
            adapter = new CreateWorkoutExerciseAdapter(getActivity().getApplicationContext(),ExerciseListDetailsDialog.exerciseWorkoutList);
            listView.setAdapter(adapter);
        }
        else {
            emptyList.setVisibility(View.VISIBLE);

        }

        addExerciseButton = (FloatingActionButton) rootView.findViewById(R.id.add_exercise_fab);
        addExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateWorkoutExerciseFragment.listener.onSwitchToNextFragment();

            }
        });

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    System.out.println("The Workout Name: " + contain.getTag(0).toString());
                    System.out.println("The Workout Description: " + contain.getTag(1).toString()); //TODO: CREATE WORKOUT - Parse Query
                    addWorkout("Test Name","Test Description",1,ExerciseListDetailsDialog.exerciseWorkoutList);
                    CreateWorkoutDialog dialog = CreateWorkoutDialog.getInstance();
                    dialog.dismiss();
                }

        });
        return rootView;
    }


    public static CreateWorkoutExerciseFragment newInstance(CreateWorkoutDialog.CalendarPageFragmentListener listener) {
        CreateWorkoutExerciseFragment f = new CreateWorkoutExerciseFragment();
        CreateWorkoutExerciseFragment.listener = listener;
        return f;
    }

    public void addWorkout(String name, String description, int level, ArrayList<ParseObject> exercise){
        final ArrayList<ParseObject> workoutExercisesList = new ArrayList<ParseObject>();

        for(int n=0;n <exercise.size();n++){
            ParseObject workoutExercises = new ParseObject("WorkoutExercises");
            workoutExercises.put("sets",exercise.get(n).getInt("sets"));
            workoutExercises.put("reps",exercise.get(n).getInt("reps"));
            workoutExercises.put("exercise", exercise.get(n));
            workoutExercisesList.add(workoutExercises);
        }

        ParseObject.saveAllInBackground(workoutExercisesList,new SaveCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    ParseObject workout= new ParseObject("Workout");
                    workout.put("userId", ParseUser.getCurrentUser());
                    //ToDo
                    workout.put("name","test");
                    workout.put("description","text-description");
                    //workout.put("level",)
                    workout.put("workoutType","custom");
                    workout.put("likes",0);

                    ParseRelation<ParseObject> workoutExercisesRelation = workout.getRelation("exercises");

                    for(int n=0;n< workoutExercisesList.size();n++){
                        workoutExercisesRelation.add(workoutExercisesList.get(n));
                    }

                    workout.saveInBackground();

                } else {
                }
            }
        });
    }

}
