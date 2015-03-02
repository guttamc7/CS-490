package finalproject.com.getfit.baseworkout;

/**
 * Created by Gurumukh on 2/4/15.
 */
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import finalproject.com.getfit.R;
import finalproject.com.getfit.viewpager.RootFragment;

public class BaseWorkoutFragment extends RootFragment {

    public final static String TAG = BaseWorkoutFragment.class.getSimpleName();
    private List<BaseWorkout> baseWorkoutList = new ArrayList<>();
    private ListView listView;
    View v;
    public static String webLink;
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


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        new GetWorkouts().execute();
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                System.out.println("In Item Click");
                BaseWorkout data = (BaseWorkout)listView.getItemAtPosition(position);
                webLink = data.getWorkoutUrl();
                System.out.println(webLink);
                FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                ft.replace(R.id.frag_base, new BaseWorkoutDetailsFragment());
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.addToBackStack(null);
                ft.commit();

            }
        });


    }

    public void onListItemClick(ListView l, View v, int position, long id) {


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
    public String getWebLink(){
        return webLink;
    }

    private class GetWorkouts extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... arg0) {
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Workout");
            query.whereEqualTo("userId", ParseUser.createWithoutData("_User",getResources().getString(R.string.baseUserId)));
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
                            baseWorkoutData.setBaseWorkoutUrl(obj.getString("workoutUrl"));
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
            adapter.notifyDataSetChanged();

        }
    }
}

