package finalproject.com.getfit.customworkout;

/**
 * Created by Gurumukh on 2/10/15.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.getbase.floatingactionbutton.FloatingActionButton;

import finalproject.com.getfit.R;
import finalproject.com.getfit.createworkout.CreateWorkoutDialog;

public class CustomWorkoutFragment extends Fragment {
    private FloatingActionButton createWorkoutButton;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_customworkout, container, false);
        createWorkoutButton = (FloatingActionButton) rootView.findViewById(R.id.create_workout_fab);
        createWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateWorkoutDialog newFragment = CreateWorkoutDialog.newInstance();
                newFragment.show(getFragmentManager(), "dialog");

            }
        });
        return rootView;
    }

    public CustomWorkoutFragment() {
        // Auto-generated constructor stub
    }

    public static CustomWorkoutFragment newInstance() {
        return new CustomWorkoutFragment();
    }


}
