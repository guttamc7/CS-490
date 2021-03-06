package com.gym8.findnearby;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.appyvet.rangebar.RangeBar;

import com.gym8.main.R;

/**
 * Created by Gurumukh on 3/26/15.
 */
public class DiscoveryPreferencesDialog extends DialogFragment {
    private View rootView;
    private Button submitButton;
    private Button cancelButton;
    private RangeBar rangeBarDistance;
    private RangeBar rangeBarAge;
    private CheckedTextView checkedTextViewMale;
    private CheckedTextView checkedTextViewFemale;
    private boolean maleChecked;
    private boolean femaleChecked;
    private static double finalDistance = 100;
    private static int startAge = 16;
    private static int endAge = 75;
    private static String gender = "both";
    private TextView ageMinimum;
    private TextView ageMaximun;
    private TextView distanceMaximum;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.dialog_discovery_settings, null);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        submitButton = (Button) rootView.findViewById(R.id.submitButton_dialog_discovery);
        cancelButton = (Button) rootView.findViewById(R.id.cancelButton_dialog_discovery);
        rangeBarDistance = (RangeBar) rootView.findViewById(R.id.distanceRangeBar);
        rangeBarAge = (RangeBar) rootView.findViewById(R.id.ageRangeBar);
        checkedTextViewMale = (CheckedTextView) rootView.findViewById(R.id.checkedTextMale);
        checkedTextViewFemale = (CheckedTextView) rootView.findViewById(R.id.checkedTextFemale);
        distanceMaximum = (TextView) rootView.findViewById(R.id.finalMiles);
        ageMaximun = (TextView) rootView.findViewById(R.id.finalAge);
        ageMinimum = (TextView) rootView.findViewById(R.id.initialAge);

        checkedTextViewMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkedTextViewMale.isChecked()) {
                    checkedTextViewMale.setChecked(false);
                } else {
                    maleChecked = true;
                    checkedTextViewMale.setChecked(true);
                }
            }
        });

        checkedTextViewFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkedTextViewFemale.isChecked()) {
                    checkedTextViewFemale.setChecked(false);
                } else {
                    femaleChecked = true;
                    checkedTextViewFemale.setChecked(true);
                }
            }
        });

        rangeBarDistance.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex, int rightPinIndex, String leftPinValue, String rightPinValue) {
                DiscoveryPreferencesDialog.finalDistance = rightPinIndex;
                distanceMaximum.setText(rightPinValue + " miles");

            }
        });

        rangeBarAge.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex, int rightPinIndex, String leftPinValue, String rightPinValue) {
                ageMinimum.setText(leftPinValue + " years");
                ageMaximun.setText(rightPinValue + " years");
                DiscoveryPreferencesDialog.startAge = leftPinIndex + 16;
                DiscoveryPreferencesDialog.endAge = rightPinIndex + 16;

            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (maleChecked == true && femaleChecked == false) {
                    gender = "Male";
                } else if (femaleChecked == true && maleChecked == false) {
                    gender = "Female";
                } else if (maleChecked == true && femaleChecked == true) {
                    gender = "both";
                }
                getDialog().dismiss();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });
        return rootView;
    }

    static String getGender() {
        return DiscoveryPreferencesDialog.gender;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (this.getDialog() == null) {
            return;
        }
        int dialogWidth = ViewGroup.LayoutParams.MATCH_PARENT;
        int dialogHeight = ViewGroup.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setLayout(dialogWidth, dialogHeight);
    }

    public static Double getFinalDistance() {
        return finalDistance;
    }

    static int getMinimumAge() {
        return DiscoveryPreferencesDialog.startAge;
    }

    static int getMaximumAge() {
        return DiscoveryPreferencesDialog.endAge;
    }

}
