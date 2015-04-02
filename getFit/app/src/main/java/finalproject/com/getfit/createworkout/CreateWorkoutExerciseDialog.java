package finalproject.com.getfit.createworkout;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.getbase.floatingactionbutton.FloatingActionButton;

import finalproject.com.getfit.R;

/**
 * Created by Gurumukh on 3/30/15.
 */
public class CreateWorkoutExerciseDialog extends DialogFragment {
    private View rootView;
    private Button doneButton;
    private FloatingActionButton addExerciseButton;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.dialog_create_workout_exercise, null);
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

    public void onStart() {
        super.onStart();
        if (this.getDialog() == null) {
            return;
        }
        int dialogWidth = ViewGroup.LayoutParams.MATCH_PARENT;
        int dialogHeight =ViewGroup.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setLayout(dialogWidth, dialogHeight);
    }

}
