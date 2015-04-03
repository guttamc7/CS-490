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
    private ArrayList<ParseObject> customWorkoutExerciseList = new ArrayList<>();
    private View rootView;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_custom_workout_details, null);
        listView = (ListView) rootView.findViewById(R.id.custom_details_list);
        return rootView;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        new GetWorkouts().execute();
    }
    private class GetWorkouts extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... arg0) {
            //TODO: Get Exercises PARSE QUERY
            return null;
        }
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            adapter = new CustomWorkoutDetailsAdapter(getActivity().getApplicationContext(), customWorkoutExerciseList);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        }
    }


}
