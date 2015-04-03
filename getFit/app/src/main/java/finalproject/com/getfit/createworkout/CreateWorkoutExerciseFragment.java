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

import com.getbase.floatingactionbutton.FloatingActionButton;

import finalproject.com.getfit.R;
import finalproject.com.getfit.baseworkout.BaseWorkoutDetailsFragment;

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
                boolean valid = validate();
                if(valid) {
                    //TODO: CREATE WORKOUT - Parse Query

                }

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



}
