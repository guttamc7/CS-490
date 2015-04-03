package finalproject.com.getfit.customworkout;

/**
 * Created by Gurumukh on 2/10/15.
 */
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import finalproject.com.getfit.R;
import finalproject.com.getfit.baseworkout.BaseWorkoutAdapter;
import finalproject.com.getfit.baseworkout.BaseWorkoutDetailsFragment;
import finalproject.com.getfit.createworkout.CreateWorkoutDialog;

public class CustomWorkoutFragment extends Fragment {
    private FloatingActionButton createWorkoutButton;
    private ArrayList<ParseObject> customWorkoutList = new ArrayList<>();
    private ListView listView;
    private CustomWorkoutAdapter adapter;
    public static ParseObject selectedWorkout;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_customworkout, container, false);
        listView = (ListView) rootView.findViewById(R.id.custom_workout_list);
        createWorkoutButton = (FloatingActionButton) rootView.findViewById(R.id.create_workout_fab);
        createWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateWorkoutDialog newFragment = CreateWorkoutDialog.newInstance();
                newFragment.show(getFragmentManager(), "dialog");


            }
        });
        return rootView;
    }

    public CustomWorkoutFragment() {
        // Auto-generated constructor stub
    }
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        new GetWorkouts().execute();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                selectedWorkout = (ParseObject)listView.getItemAtPosition(position);
                FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                ft.replace(R.id.frag_custom, new CustomWorkoutDetailsFragment());
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
    }

    public void onResume() {
        super.onResume();

    }

    public static CustomWorkoutFragment newInstance() {
        return new CustomWorkoutFragment();
    }


    private class GetWorkouts extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... arg0) {
            //TODO:Get Custom Workouts PARSE QUERY
            return null;
        }
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            adapter = new CustomWorkoutAdapter(getActivity().getApplicationContext(), customWorkoutList);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        }
    }



}
