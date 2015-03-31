package finalproject.com.getfit.findnearby;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckedTextView;

import com.appyvet.rangebar.RangeBar;

import finalproject.com.getfit.R;

/**
 * Created by Gurumukh on 3/26/15.
 */
public class DiscoveryPreferencesDialog extends DialogFragment
{
    private View rootView;
    private Button submitButton;
    private Button cancelButton;
    private RangeBar rangeBarDistance;
    private RangeBar rangeBarAge;
    private CheckedTextView checkedTextViewMale;
    private CheckedTextView checkedTextViewFemale;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.dialog_discovery_settings,null);
        submitButton = (Button) rootView.findViewById(R.id.submitButton_dialog_discovery);
        cancelButton= (Button) rootView.findViewById(R.id.cancelButton_dialog_discovery);
        rangeBarDistance = (RangeBar) rootView.findViewById(R.id.distanceRangeBar);
        rangeBarAge = (RangeBar) rootView.findViewById(R.id.ageRangeBar);
        checkedTextViewMale = (CheckedTextView) rootView.findViewById(R.id.checkedTextMale);
        checkedTextViewFemale = (CheckedTextView) rootView.findViewById(R.id.checkedTextFemale);

        checkedTextViewMale.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(checkedTextViewMale.isChecked())
                {
                    checkedTextViewMale.setChecked(false);
                }
                else
                {
                    checkedTextViewMale.setChecked(true);
                }
            }
        });

        checkedTextViewFemale.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(checkedTextViewFemale.isChecked())
                {
                    checkedTextViewFemale.setChecked(false);
                }
                else
                {
                    checkedTextViewFemale.setChecked(true);
                }
            }
        });

        rangeBarDistance.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener()
        {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex, int rightPinIndex, String leftPinValue, String rightPinValue)
            {

            }
        });

        rangeBarAge.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener()
        {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex, int rightPinIndex, String leftPinValue, String rightPinValue)
            {

            }
        });

        submitButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View view)
            {

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

            }
        });

        return rootView;
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
