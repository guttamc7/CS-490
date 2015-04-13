package finalproject.com.getfit.customworkout;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import finalproject.com.getfit.R;
import finalproject.com.getfit.baseworkout.BaseWorkoutAdapter;
import finalproject.com.getfit.createworkout.CreateWorkoutExerciseAdapter;

/**
 * Created by Gurumukh on 4/3/15.
 */
public class CustomWorkoutDetailsFragment extends Fragment {

    private ListView listView;
    private CustomWorkoutDetailsAdapter adapter;
    private ParseObject selectedWorkout;
    private List<ParseObject> workoutExercisesList;
    private View rootView;

    public CustomWorkoutDetailsFragment(ParseObject selectedWorkout) {
        // Auto-generated constructor stub
        this.selectedWorkout = selectedWorkout;
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_custom_workout_details, null);
        listView = (ListView) rootView.findViewById(R.id.custom_details_list);
        return rootView;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void getExercises() {
        ParseRelation<ParseObject> exercises = selectedWorkout.getRelation("exercises");
        ParseQuery<ParseObject> query = exercises.getQuery();
        query.findInBackground(new FindCallback<ParseObject>() {
        public void done(List<ParseObject> workoutExercises, ParseException e) {
            if (e == null) {
                workoutExercisesList.addAll(workoutExercises);
            } else {
             }
            }
        });


        /*for (int n = 0; n < workoutExercisesList.size(); n++) {
            workoutExercisesList.get(n).get("reps");
            workoutExercisesList.get(n).get("sets");
            ParseObject exercise = workoutExercises.get(m).getParseObject("exerciseId");
        }*/

    }
}