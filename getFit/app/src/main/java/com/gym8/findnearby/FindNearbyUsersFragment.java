package com.gym8.findnearby;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.gym8.ErrorHandlingAlertDialogBox;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

import android.widget.AdapterView.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import com.gym8.main.R;
import com.gym8.viewpager.RootFragment;

/**
 * Created by Gurumukh on 3/6/15.
 */
public class FindNearbyUsersFragment extends RootFragment {

    private ParseQueryAdapter<ParseObject> mainAdapter;
    private FindNearbyUsersAdapter nearbyUsersAdapter;
    private ArrayList<ParseUser> findNearbyUsersList = new ArrayList<>();
    private GridView gridView;
    private View v;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_find_nearby_users, container, false);
        gridView = (GridView) v.findViewById(R.id.find_nearby_grid);
        return v;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        findNearbyUsers();
        gridView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                FindNearbyUserProfileFragment nearbyUserProfileFragment = new FindNearbyUserProfileFragment();
                nearbyUserProfileFragment.setParseUser(findNearbyUsersList.get(position));
                ft.replace(R.id.findnearby_user_frag, nearbyUserProfileFragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.addToBackStack(null);
                ft.commit();

                }
            });

    }

    private void onPostExecute() {
        nearbyUsersAdapter = new FindNearbyUsersAdapter(getActivity(), findNearbyUsersList);
        gridView.setAdapter(nearbyUsersAdapter);
        nearbyUsersAdapter.notifyDataSetChanged();
    }

    private void findNearbyUsers(){
        ParseGeoPoint userLocation = new ParseGeoPoint(FindNearbyFragment.getLatitude(), FindNearbyFragment.getLongitude());
        ParseUser currentUser = ParseUser.getCurrentUser();
        currentUser.put("location", userLocation);
        currentUser.saveInBackground();

        ParseQuery<ParseUser> query = ParseQuery.getQuery("_User");
        query.whereNotEqualTo("objectId", currentUser.getObjectId());
        query.whereWithinMiles("location", userLocation, DiscoveryPreferencesDialog.returnFinaldistance);
        if(DiscoveryPreferencesDialog.gender!="both") {
            query.whereEqualTo("gender",DiscoveryPreferencesDialog.gender);
        }
        query.setLimit(10);
        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> userList, ParseException e) {
                if (e == null) {
                    findNearbyUsersList.addAll(userList);
                } else {
                    ErrorHandlingAlertDialogBox.showDialogBox(getActivity().getBaseContext());
                }
        onPostExecute();
            }
        });
    }
}


