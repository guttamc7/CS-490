package finalproject.com.getfit.createworkout;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.support.v4.app.Fragment;
import com.getbase.floatingactionbutton.FloatingActionButton;

import finalproject.com.getfit.R;

/**
 * Created by Gurumukh on 3/30/15.
 */
public class CreateWorkoutExerciseFragment extends Fragment {
    private View rootView;
    private Button doneButton;
    private FloatingActionButton addExerciseButton;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_create_workout_exercise, null);
        addExerciseButton = (FloatingActionButton) rootView.findViewById(R.id.add_exercise_fab);
        addExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFrag = new ExerciseListDialog();
                dialogFrag.show(getActivity().getFragmentManager().beginTransaction(), "");

            }
        });
        return rootView;
    }



}
