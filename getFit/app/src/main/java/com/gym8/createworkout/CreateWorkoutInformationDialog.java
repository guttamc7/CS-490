package com.gym8.createworkout;

/**
 * Created by Gurumukh on 3/30/15.
 */
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.DialogFragment;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.gym8.main.R;

public class CreateWorkoutInformationDialog extends DialogFragment {
    private View rootView;
    private EditText workoutNameText;
    private EditText workoutDescriptionText;
    private ImageButton level1;
    private ImageButton level2;
    private ImageButton level3;
    private TextView selectedLevelText;
    private Button nextButton;
    public static String workoutName;
    public static String workoutDescription;
    public static int workoutLevel;
    private static CreateWorkoutInformationDialog f;

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder b = new AlertDialog.Builder(getActivity())
                .setIcon(R.drawable.ic_dumbbell)
                .setPositiveButton("Next",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                if (workoutNameText.getText().toString() == null || workoutNameText.getText().toString().length() == 0) {
                                    Toast.makeText(getActivity(), "Please Enter A Workout Name", Toast.LENGTH_SHORT).show();


                                } else if (workoutDescriptionText.getText().toString() == null || workoutNameText.getText().toString().length() == 0) {
                                    Toast.makeText(getActivity(), "Please Enter A Workout Description", Toast.LENGTH_SHORT).show();

                                } else if (CreateWorkoutInformationDialog.workoutLevel == 0) {
                                    Toast.makeText(getActivity(), "Please Enter A Workout Level", Toast.LENGTH_SHORT).show();

                                } else {
                                    workoutName = workoutNameText.getText().toString();
                                    workoutDescription = workoutDescriptionText.getText().toString();
                                    dismiss();
                                    CreateWorkoutDialog f = new CreateWorkoutDialog().newInstance();
                                    f.show(getFragmentManager(), "dialog");
                                }
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
        rootView = inflater.inflate(R.layout.fragment_create_workout_information, null);
        workoutNameText = (EditText) rootView.findViewById(R.id.workout_name);
        workoutDescriptionText = (EditText) rootView.findViewById(R.id.workout_description);
        //nextButton = (Button) rootView.findViewById(R.id.createWorkout_nextButton);
        level1 = (ImageButton) rootView.findViewById(R.id.level1Button);
        level2 = (ImageButton) rootView.findViewById(R.id.level2Button);
        level3 = (ImageButton) rootView.findViewById(R.id.level3Button);
        selectedLevelText = (TextView) rootView.findViewById(R.id.workoutlev);
        if(workoutLevel == 0)
            selectedLevelText.setText("Workout Level : ");
        level1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedLevelText.setText("Workout Level :  Level 1");
                workoutLevel  = 1;

            }
        });
        level2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                workoutLevel  = 2;
                selectedLevelText.setText("Workout Level :  Level 2");

            }
        });
        level3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                workoutLevel  = 3;
                selectedLevelText.setText("Workout Level :  Level 3");

            }
        });
        b.setView(rootView);
        return b.create();
    }

    public static CreateWorkoutInformationDialog newInstance(){
        f = new CreateWorkoutInformationDialog();
        return f;
    }


    @Override
    public void onStart()
    {
        super.onStart();
        if (this.getDialog() == null)
        {
            return;
        }
        int dialogWidth = ViewGroup.LayoutParams.MATCH_PARENT;
        int dialogHeight =ViewGroup.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setLayout(dialogWidth, dialogHeight);
    }
}
