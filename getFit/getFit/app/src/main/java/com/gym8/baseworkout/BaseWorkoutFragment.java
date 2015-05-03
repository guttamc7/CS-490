package com.gym8.baseworkout;

/**
 * Created by Gurumukh on 2/4/15.
 */
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.gym8.ErrorHandlingAlertDialogBox;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import com.gym8.main.R;

public class BaseWorkoutFragment extends Fragment {

    public final static String TAG = BaseWorkoutFragment.class.getSimpleName();
    private ListView listView;
    private View v;
    private static String webLink;
    private Context mContext;
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
        if(savedInstanceState == null)
            getBaseWorkouts();
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                ParseObject workout = (ParseObject)listView.getItemAtPosition(position);
                webLink = workout.getString("workoutUrl");
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.add(R.id.content_frame, new BaseWorkoutDetailsFragment());
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.addToBackStack("Base Workout List");
                ft.commit();
            }
        });
    }

    public void onResume() {
        super.onResume();

    }
    public String getWebLink(){
        return webLink;
    }


    private void getBaseWorkouts(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Workout");
        query.orderByAscending("level");
        query.whereEqualTo("userId", ParseUser.createWithoutData("_User",getResources().getString(R.string.baseUserId)));
        query.findInBackground(new FindCallback<ParseObject>() {

            public void done(List<ParseObject> workoutList, ParseException e) {
               if (e == null) {
                    //All the base workouts retrieved
                    adapter = new BaseWorkoutAdapter(getActivity().getApplicationContext(), workoutList);
                    listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
                else
                {
                    Toast.makeText(getActivity(), "Connection error", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

}

