package finalproject.com.getfit;

/**
 * Created by Gurumukh on 2/4/15.
 */
import android.graphics.Movie;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import junit.runner.Version;

import java.util.ArrayList;
import java.util.List;

public class BaseWorkoutFragment extends Fragment {

    public final static String TAG = BaseWorkoutFragment.class.getSimpleName();
    private List<BaseWorkout> baseWorkoutList = new ArrayList<>();
    private ListView listView;
    WorkoutLibFragment libfrag;
    private BaseWorkoutAdapter adapter;

    public BaseWorkoutFragment() {
        // TODO Auto-generated constructor stub
    }

    public static BaseWorkoutFragment newInstance() {
        return new BaseWorkoutFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_baseworkout, container, false);
        listView = (ListView)v.findViewById(R.id.base_list);
        libfrag = new WorkoutLibFragment();

        baseWorkoutList = libfrag.getWorkoutData();
        adapter = new BaseWorkoutAdapter(getActivity().getApplicationContext(),baseWorkoutList);
        //System.out.println("In WorkoutFragment:"+ baseWorkoutList.size());
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);

        return v;
    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        //TODO
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
}
