package com.gym8.createworkout;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import com.gym8.ErrorHandlingAlertDialogBox;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import com.gym8.main.R;


/**
 * Created by Gurumukh on 4/1/15.
 */
public class ExerciseListFragment extends Fragment {
    private View rootView;
    private ListView lv;
    private ArrayAdapter<String> adapter;
    private EditText inputSearch;
    private ArrayList<String> exerciseList = new ArrayList<>();
    private List<ParseObject> workList = new ArrayList<>();
    public static ParseObject selectedExercise;
    public static CreateWorkoutDialog.CalendarPageFragmentListener listener;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_exercise_list, null);
        lv = (ListView) rootView.findViewById(R.id.all_exercises_list);
        inputSearch = (EditText) rootView.findViewById(R.id.inputSearch);
        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
               if(adapter!= null) {
                   adapter.getFilter().filter(cs);
               }
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

    public static ExerciseListFragment newInstance(CreateWorkoutDialog.CalendarPageFragmentListener listener) {
        ExerciseListFragment f = new ExerciseListFragment();
        ExerciseListFragment.listener = listener;
        return f;
    }

    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        getAllExercises();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String temp =(String) parent.getItemAtPosition(position);
                int pos = exerciseList.indexOf(temp);
                selectedExercise  = workList.get(pos);
                DialogFragment dialogFrag = new ExerciseListDetailsDialog();
                dialogFrag.show(getActivity().getFragmentManager().beginTransaction(), "dialog");

            }
        });

        //Set Item Click Listener
    }

    private void onPostExecute() {
        adapter = new ArrayAdapter<String>(getActivity(), R.layout.exerciselist_row, R.id.exercise_name, exerciseList);
        lv.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    private void getAllExercises() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Exercise");
        query.orderByAscending("name");
        query.setLimit(1000);
        query.findInBackground(new FindCallback<ParseObject>() {

            public void done(List<ParseObject> parseExerciseList, ParseException e) {
                if (e == null) {
                   for(int n=0;n<parseExerciseList.size();n++){
                       workList.add(parseExerciseList.get(n));
                       exerciseList.add(parseExerciseList.get(n).getString("name"));
                   }

                } else {
                    ErrorHandlingAlertDialogBox.showDialogBox(getActivity().getBaseContext());
                    //Exception
                }
            onPostExecute();
            }

        });
    }
}