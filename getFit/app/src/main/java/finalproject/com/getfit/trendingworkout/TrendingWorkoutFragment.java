package finalproject.com.getfit.trendingworkout;

/**
 * Created by Gurumukh on 2/4/15.
 */

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
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import finalproject.com.getfit.R;
import finalproject.com.getfit.viewpager.RootFragment;

public class TrendingWorkoutFragment extends RootFragment
{
    public final static String TAG = TrendingWorkoutFragment.class.getSimpleName();
    private List<TrendingWorkout> trendingWorkoutList = new ArrayList<>();
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
        listViewTrendingWorkout.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                TrendingWorkout data = (TrendingWorkout)listViewTrendingWorkout.getItemAtPosition(position);
                FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                ft.replace(R.id.frag_trending, new TrendingWorkoutDetailsFragment());
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
    }

    public void onListItemClick(ListView l, View v, int position, long id)
    {
    }

    public void onResume()
    {
        super.onResume();
    }

   /* private class GetCustomWorkouts extends AsyncTask<Void, Void, Void> {
   //TODO

        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            adapter = new TrendingWorkoutAdapter(getActivity().getApplicationContext(), trendingWorkoutList);
            listViewTrendingWorkout.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        }
    }*/
}
