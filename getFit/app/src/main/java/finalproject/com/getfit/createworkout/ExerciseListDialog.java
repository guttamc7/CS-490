package finalproject.com.getfit.createworkout;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import finalproject.com.getfit.R;

/**
 * Created by Gurumukh on 4/1/15.
 */
public class ExerciseListDialog extends DialogFragment {
    private View rootView;
    private Button nextButton;
    private ListView exerciseList;
    private ArrayAdapter<String> adapter;
    private EditText inputSearch;
    private List<ParseObject> excerciseList = new ArrayList<>();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.dialog_exercise_list, null);
        exerciseList = (ListView) rootView.findViewById(R.id.all_exercises_list);
        inputSearch = (EditText) rootView.findViewById(R.id.inputSearch);

        return rootView;
    }

    private void retrieveExercises () {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Exercise");
        query.orderByAscending("name");
        query.findInBackground(new FindCallback<ParseObject>() {

            public void done(List<ParseObject> workoutList, ParseException e) {
                if (e == null) {
                    for (int i = 0; i < workoutList.size(); i++) {
                       excerciseList.add(workoutList.get(i));
                    }
                    //All the base workouts retrieved
                } else {
                    System.out.println(e.getMessage());
                    //Exception
                }
            }
        });


    }



}