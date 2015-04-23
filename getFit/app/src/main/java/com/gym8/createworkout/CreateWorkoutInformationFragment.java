package com.gym8.createworkout;

/**
 * Created by Gurumukh on 3/30/15.
 */
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.gym8.main.R;

public class CreateWorkoutInformationFragment extends Fragment {
    private View rootView;
    private EditText workoutNameText;
    private EditText workoutDescriptionText;
    private ImageButton level1;
    private ImageButton level2;
    private ImageButton level3;
    private TextView selectedLevelText;
    public static String workoutName;
    public static String workoutDescription;
    public static int workoutLevel;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_create_workout_information, null);
        workoutNameText = (EditText) rootView.findViewById(R.id.workout_name);
        workoutDescriptionText = (EditText) rootView.findViewById(R.id.workout_description);
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
        workoutName = workoutNameText.getText().toString();
        workoutDescription = workoutDescriptionText.getText().toString();
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        System.out.println("In Information Fragment On Attach");
    }
}
