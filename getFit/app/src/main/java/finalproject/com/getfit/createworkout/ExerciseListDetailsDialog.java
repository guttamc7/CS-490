package finalproject.com.getfit.createworkout;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import finalproject.com.getfit.R;
import finalproject.com.getfit.imageloader.ImageLoader;

/**
 * Created by Gurumukh on 4/2/15.
 */
public class ExerciseListDetailsDialog extends DialogFragment {
    private ImageView exerciseImage1;
    private ImageView exerciseImage2;
    private ImageView exerciseImage3;
    private ImageView exerciseImage4;
    ImageLoader imageLoader;
    private EditText sets;
    private EditText reps;
    private View rootView;
    private String numberSets;
    private String numberReps;
    public static ArrayList<Exercise> exerciseWorkoutList = new ArrayList<>();
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder b = new AlertDialog.Builder(getActivity())
                .setIcon(R.drawable.ic_dumbbell)
                .setPositiveButton("Add",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                if (sets.getText().toString() == null || (!sets.getText().toString().equals("") && sets.getText().toString().length() > 0))
                                    numberSets = sets.getText().toString();

                                if (reps.getText().toString() == null || (!reps.getText().toString().equals("") && reps.getText().toString().length() > 0))
                                    numberReps = reps.getText().toString();
                                Exercise e = new Exercise();
                                e.setExerciseName(ExerciseListFragment.selectedExercise.getString("name"));
                                e.setExerciseSets(numberSets);
                                e.setExerciseReps(numberReps);
                                e.setExerciseImage1(ExerciseListFragment.selectedExercise.getString("maleImg1"));
                                e.setExerciseImage2(ExerciseListFragment.selectedExercise.getString("maleImg2"));
                                exerciseWorkoutList.add(e);
                                dismiss();
                                ExerciseListFragment.listener.onSwitchToNextFragment();
                            }
                        }
                )
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                            }
                        }
                );
        LayoutInflater inflater = getActivity().getLayoutInflater();
        imageLoader = new ImageLoader(getActivity(), 124);
        rootView = inflater.inflate(R.layout.dialog_exercise_list_details, null);
        exerciseImage1 = (ImageView) rootView.findViewById(R.id.exercise_imgView_1);
        exerciseImage2 = (ImageView) rootView.findViewById(R.id.exercise_imgView_2);
        exerciseImage3 = (ImageView) rootView.findViewById(R.id.exercise_imgView_3);
        exerciseImage4 = (ImageView) rootView.findViewById(R.id.exercise_imgView_4);
        sets = (EditText) rootView.findViewById(R.id.exercise_sets);
        reps = (EditText) rootView.findViewById(R.id.exercise_reps);
        System.out.println(ExerciseListFragment.selectedExercise.getString("name"));
        imageLoader.DisplayImage(ExerciseListFragment.selectedExercise.getString("maleImg1"), exerciseImage1);
        imageLoader.DisplayImage(ExerciseListFragment.selectedExercise.getString("maleImg2"), exerciseImage2);
        imageLoader.DisplayImage(ExerciseListFragment.selectedExercise.getString("femaleImg1"), exerciseImage3);
        imageLoader.DisplayImage(ExerciseListFragment.selectedExercise.getString("femaleImg2"), exerciseImage4);
        b.setView(rootView);
        TextView title = new TextView(getActivity());
        title.setText(ExerciseListFragment.selectedExercise.getString("name"));
        title.setBackgroundColor(getResources().getColor(R.color.primary_blue));
        title.setPadding(15, 15, 15, 15);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(getResources().getColor(R.color.primary_white));
        title.setTextSize(20);
        b.setCustomTitle(title);

        return b.create();
    }


}
