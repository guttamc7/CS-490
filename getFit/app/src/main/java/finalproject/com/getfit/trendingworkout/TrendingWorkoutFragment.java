package finalproject.com.getfit.trendingworkout;

/**
 * Created by Gurumukh on 2/4/15.
 */

//TODO: Loading trending again

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import finalproject.com.getfit.R;
import finalproject.com.getfit.viewpager.RootFragment;

public class TrendingWorkoutFragment extends RootFragment
{
    public final static String TAG = TrendingWorkoutFragment.class.getSimpleName();
    private volatile List<ParseObject> trendingWorkoutList = new ArrayList<>();
    private ListView listViewTrendingWorkout;
    View v;
    private TrendingWorkoutAdapter adapter;

    public TrendingWorkoutFragment()
    {
        // Auto-generated constructor stub
    }

    public static TrendingWorkoutFragment newInstance()
    {
        return new TrendingWorkoutFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        v = inflater.inflate(R.layout.fragment_trending, container, false);
        listViewTrendingWorkout = (ListView) v.findViewById(R.id.trending_list);
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
    }

    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        getTrendingWorkouts();
        listViewTrendingWorkout.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                ParseObject trendingWorkout = (ParseObject)listViewTrendingWorkout.getItemAtPosition(position);
                FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                ft.replace(R.id.frag_trending, new TrendingWorkoutDetailsFragment());
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
    }


    public void onResume()
    {
        super.onResume();
    }

    private void onPostExecute() {
        adapter = new TrendingWorkoutAdapter(getActivity().getApplicationContext(), trendingWorkoutList);
        listViewTrendingWorkout.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void getTrendingWorkouts(){
        ParseUser user = ParseUser.getCurrentUser();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Workout");
        query.orderByDescending("likes");
        query.findInBackground(new FindCallback<ParseObject>() {

            public void done(List<ParseObject> workoutList, ParseException e) {
                if (e == null) {
                    //All the workouts retrieved
                    trendingWorkoutList.addAll(workoutList);
                } else {
                    System.out.println(e.getMessage());
                }
        onPostExecute();
            }
        });
    }
}