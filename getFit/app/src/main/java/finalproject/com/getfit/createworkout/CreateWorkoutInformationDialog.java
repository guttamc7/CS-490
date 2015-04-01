package finalproject.com.getfit.createworkout;

/**
 * Created by Gurumukh on 3/30/15.
 */
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import finalproject.com.getfit.EditProfileDialog;
import finalproject.com.getfit.HomePageActivity;
import finalproject.com.getfit.NewProfileActivity;
import finalproject.com.getfit.R;

public class CreateWorkoutInformationDialog extends DialogFragment {
    private View rootView;
    private Button nextButton;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.dialog_create_workout_information, null);
        nextButton = (Button)rootView.findViewById(R.id.createworkout_nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialogFrag = new CreateWorkoutExerciseDialog();
                dialogFrag.show(getActivity().getFragmentManager().beginTransaction(), "dialog");
                getDialog().dismiss();

            }
        });
        return rootView;
    }
}
