package com.gym8.createworkout;

/**
 * Created by Gurumukh on 3/30/15.
 */
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.DialogFragment;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gym8.main.R;

public class CreateWorkoutInformationDialog extends DialogFragment {
    private View rootView;
    private EditText workoutNameText;
    private EditText workoutDescriptionText;
    private RadioGroup levels;
    private RadioButton level1Button;
    private RadioButton level2Button;
    private RadioButton level3Button;
    private CheckedTextView checkedTextViewOnlyMe;
    private CheckedTextView checkedTextViewToAll;
    private boolean onlyMeChecked;
    private boolean toAllChecked;
    private String levelText;
    public static String workoutName;
    public static String workoutDescription;
    public static int workoutLevel;
    public static boolean visibility;
    private static CreateWorkoutInformationDialog f;

    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        rootView = inflater.inflate(R.layout.fragment_create_workout_information, null);
        workoutNameText = (EditText) rootView.findViewById(R.id.workout_name);
        workoutDescriptionText = (EditText) rootView.findViewById(R.id.workout_description);
        levels = (RadioGroup) rootView.findViewById(R.id.radioButtonLevel_create_workout);
        level1Button = (RadioButton) rootView.findViewById(R.id.level1_create_workout);
        level2Button = (RadioButton) rootView.findViewById(R.id.level2_create_workout);
        level3Button = (RadioButton) rootView.findViewById(R.id.level3_create_workout);
        checkedTextViewToAll = (CheckedTextView) rootView.findViewById(R.id.checkedTextToAll);
        checkedTextViewOnlyMe = (CheckedTextView) rootView.findViewById(R.id.checkedTextOnlyMe);
        checkedTextViewOnlyMe.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(checkedTextViewOnlyMe.isChecked())
                {
                    checkedTextViewOnlyMe.setChecked(false);
                }
                else
                {
                    onlyMeChecked= true;
                    checkedTextViewOnlyMe.setChecked(true);
                }
            }
        });

        checkedTextViewToAll.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(checkedTextViewToAll.isChecked())
                {
                    checkedTextViewToAll.setChecked(false);
                }
                else
                {
                    toAllChecked = true;
                    checkedTextViewToAll.setChecked(true);
                }
            }
        });


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
                                    RadioButton selectRadio = null;
                                    Log.d("gender: ", Integer.toString(levels.getCheckedRadioButtonId()));
                                    if (levels.getCheckedRadioButtonId() == -1) {
                                        levels = null;
                                        Toast.makeText(getActivity(), "Please Enter A Workout Level", Toast.LENGTH_SHORT).show();
                                    } else {
                                        selectRadio = (RadioButton) rootView.findViewById(levels.getCheckedRadioButtonId());
                                        //Log.d("selectRadio: ", selectRadio.getText().toString());
                                        if (selectRadio.getText().toString() == null || (!selectRadio.getText().toString().equals("") && selectRadio.getText().toString().length() > 0))
                                            levelText = selectRadio.getText().toString();
                                    }
                                    if (levelText.equals("Level 1")) {
                                        workoutLevel = 1;
                                    } else if (levelText.equals("Level 2")) {
                                        workoutLevel = 2;
                                    } else {
                                        workoutLevel = 3;
                                    }
                                    if(onlyMeChecked)
                                        visibility = false;
                                    if(toAllChecked)
                                        visibility = true;
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
