package finalproject.com.getfit.createworkout;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.support.v4.app.Fragment;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.FragmentManager;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.parse.ParseObject;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.util.ArrayList;

import finalproject.com.getfit.R;
import finalproject.com.getfit.baseworkout.BaseWorkoutDetailsFragment;
import finalproject.com.getfit.customworkout.CustomWorkoutDetailsFragment;
import finalproject.com.getfit.customworkout.CustomWorkoutFragment;

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
    private static CreateWorkoutDialog.CalendarPageFragmentListener listener;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_create_workout_exercise, null);
        listView = (ListView) rootView.findViewById(R.id.create_workout_exercises_list);
        emptyList = (TextView) rootView.findViewById(R.id.empty_list);
        doneButton = (Button) rootView.findViewById(R.id.createWorkout_Button);
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
                    System.out.println("The Workout Name: " + CreateWorkoutInformationFragment.workoutName);
                    System.out.println("The Workout Name: " + CreateWorkoutInformationFragment.workoutDescription); //TODO: CREATE WORKOUT - Parse Query
                    addWorkout("Test Name","Test Description",1,ExerciseListDetailsDialog.exerciseWorkoutList);
                    CreateWorkoutDialog dialog = CreateWorkoutDialog.getInstance();
                    dialog.dismiss();
                }

        });
        return rootView;
    }

    public boolean validate() {
        if (CreateWorkoutInformationFragment.workoutName == null || CreateWorkoutInformationFragment.workoutName.length() <= 0) {
            Toast.makeText(getActivity(), "Please Enter a Valid Workout Name", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (CreateWorkoutInformationFragment.workoutDescription == null ||  CreateWorkoutInformationFragment.workoutDescription.length() <= 0) {
            Toast.makeText(getActivity(), "Please Enter a Valid Workout Description", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(CreateWorkoutInformationFragment.workoutLevel == 0) {
            Toast.makeText(getActivity(), "Please Select a Level", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(ExerciseListDetailsDialog.exerciseWorkoutList == null || ExerciseListDetailsDialog.exerciseWorkoutList.size() == 0) {
            Toast.makeText(getActivity(), "Please Add At least One Exercise", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;

    }

    public static CreateWorkoutExerciseFragment newInstance(CreateWorkoutDialog.CalendarPageFragmentListener listener) {
        CreateWorkoutExerciseFragment f = new CreateWorkoutExerciseFragment();
        CreateWorkoutExerciseFragment.listener = listener;
        return f;
    }

    public void addWorkout(String name, String description, int level, ArrayList<ParseObject> exercise){
        System.out.println("Exercise is "+exercise.size());

        ParseObject workout = new ParseObject("Workout");
        workout.add("name",name);
        workout.add("workoutType","custom");
        workout.add("level",level);
        workout.add("likes",0);
        workout.add("userId", ParseUser.getCurrentUser());
        workout.add("description",description);
        ParseRelation<ParseObject> relation = workout.getRelation("exercises");
        for(int n=0;n <exercise.size();n++){

            ParseObject workoutExercises = new ParseObject("WorkoutExercises");
            System.out.println(exercise.get(n).getInt("sets"));
            System.out.println(exercise.get(n).getString("name"));
            workoutExercises.add("sets",exercise.get(n).getInt("sets"));
            workoutExercises.add("reps",exercise.get(n).getInt("reps"));
            workoutExercises.add("exerciseId",exercise.get(n)); //Uncomment
            workoutExercises.saveInBackground();
            relation.add(workoutExercises);
        }
        workout.saveInBackground();
    }



}
