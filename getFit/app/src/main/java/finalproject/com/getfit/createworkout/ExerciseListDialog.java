package finalproject.com.getfit.createworkout;

import android.app.DialogFragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

import finalproject.com.getfit.MainActivity;
import finalproject.com.getfit.R;
import finalproject.com.getfit.trendingworkout.TrendingWorkoutAdapter;
import finalproject.com.getfit.trendingworkout.TrendingWorkoutDetailsFragment;

/**
 * Created by Gurumukh on 4/1/15.
 */
public class ExerciseListDialog extends DialogFragment {
    private View rootView;
    private Button nextButton;
    private ListView lv;
    private ArrayAdapter<String> adapter;
    private EditText inputSearch;
    private ArrayList<String> exerciseList = new ArrayList<>();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.dialog_exercise_list, null);
        lv = (ListView) rootView.findViewById(R.id.all_exercises_list);
        inputSearch = (EditText) rootView.findViewById(R.id.inputSearch);
        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
               adapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {

            }

            @Override
            public void afterTextChanged(Editable arg0) {

            }
        });
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (this.getDialog() == null) {
            return;
        }
        int dialogWidth = ViewGroup.LayoutParams.MATCH_PARENT;
        int dialogHeight =ViewGroup.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setLayout(dialogWidth, dialogHeight);
    }

    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        new GetExercises().execute();
        //Set Item Click Listener
    }

    private class GetExercises extends AsyncTask<Void, Void, Void> {

        protected Void doInBackground(Void... arg0) {
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Exercise");
            query.orderByAscending("name");
            query.findInBackground(new FindCallback<ParseObject>() {

                public void done(List<ParseObject> workoutList, ParseException e) {
                    if (e == null) {
                        for (int i = 0; i < workoutList.size(); i++) {
                            exerciseList.add(workoutList.get(i).getString("name"));
                        }
                        //All the base workouts retrieved
                    } else {
                        System.out.println(e.getMessage());
                        //Exception
                    }
                }
            });
            return null;
        }


        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            adapter = new ArrayAdapter<String>(getActivity(), R.layout.exerciselist_row, R.id.exercise_name, exerciseList);
            lv.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        }


    }



}