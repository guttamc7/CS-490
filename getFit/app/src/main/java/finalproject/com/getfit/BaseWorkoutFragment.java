package finalproject.com.getfit;

/**
 * Created by Gurumukh on 2/4/15.
 */
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Movie;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import junit.runner.Version;

import java.util.ArrayList;
import java.util.List;

public class BaseWorkoutFragment extends Fragment {

    public final static String TAG = BaseWorkoutFragment.class.getSimpleName();
    private List<BaseWorkout> baseWorkoutList = new ArrayList<>();
    private ListView listView;
    View v;
    private BaseWorkoutAdapter adapter;
    public BaseWorkoutFragment() {
        // Auto-generated constructor stub
    }

    public static BaseWorkoutFragment newInstance() {
        return new BaseWorkoutFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_baseworkout, container, false);
        listView = (ListView) v.findViewById(R.id.base_list);
        return v;
    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        //TODO
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        new GetWorkouts().execute();

    }

    /*private void getExercisesForWorkout(String workoutId) {

        //TODO
        ParseQuery<ParseObject> query = ParseQuery.getQuery("WorkoutExercises");
        query.whereEqualTo("workOutId", workoutId);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> exerciseList, ParseException e) {
                if (e == null) {
                    //All the exercises for the specified workout retrieved
                } else {
                    //Exception
                }
            }
        });
    }*/

    public void onResume() {
        super.onResume();

    }


    private class GetWorkouts extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... arg0) {
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Workout");
            query.whereEqualTo("userId", ParseUser.getCurrentUser());
            query.findInBackground(new FindCallback<ParseObject>() {

                public void done(List<ParseObject> workoutList, ParseException e) {
                    if (e == null) {
                        System.out.println("Getting base workouts");
                        BaseWorkout baseWorkoutData;
                        for (int i = 0; i < workoutList.size(); i++) {
                            baseWorkoutData = new BaseWorkout();
                            ParseObject obj = workoutList.get(i);
                            int level = obj.getInt("level");
                            baseWorkoutData.setBaseWorkoutLevel(Integer.toString(level));
                            baseWorkoutData.setBaseWorkoutDescription(obj.getString("description"));
                            baseWorkoutData.setBaseWorkoutName(obj.getString("name"));
                            baseWorkoutList.add(baseWorkoutData);
                            adapter.notifyDataSetChanged();
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
            adapter = new BaseWorkoutAdapter(getActivity().getApplicationContext(), baseWorkoutList);
            listView.setAdapter(adapter);

        }
    }
}

