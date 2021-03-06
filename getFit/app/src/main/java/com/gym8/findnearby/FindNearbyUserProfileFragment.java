package com.gym8.findnearby;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.gym8.main.HomePageActivity;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import com.gym8.main.R;
import com.gym8.userprofile.UserProfileFragment;
import com.gym8.viewpager.RootFragment;
import com.parse.SaveCallback;

import java.util.List;

/**
 * Created by Gurumukh on 3/12/15.
 */
public class FindNearbyUserProfileFragment extends RootFragment {
    private ParseUser user;
    private TextView nearbyUserName,nearbyUserHeight,nearbyUserWeight,nearbyUserAge;
    private ImageView nearbyUserProfilePic;
    private ListView listView;
    private ViewUserWorkoutAdapter adapter;
    private FloatingActionButton chatButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_find_nearby_user_profile, container, false);
        nearbyUserName = (TextView) rootView.findViewById(R.id.nearbyUserName);
        nearbyUserAge = (TextView) rootView.findViewById(R.id.nearbyUserAge);
        nearbyUserHeight = (TextView) rootView.findViewById(R.id.nearbyUserHeight);
        nearbyUserWeight = (TextView) rootView.findViewById(R.id.nearbyUserWeight);
        nearbyUserProfilePic = (ImageView) rootView.findViewById(R.id.nearbyUserProfilePic);
        chatButton = (FloatingActionButton) rootView.findViewById(R.id.chat_button);
        setUserDetails();
        chatButton.setSize(FloatingActionButton.SIZE_MINI);
        chatButton.setColorNormalResId(R.color.button_yellow);
        chatButton.setIcon(R.drawable.ic_messages);

        listView = (ListView) rootView.findViewById(R.id.user_likes_fragnearby);
        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }

                // Handle ListView touch events.
                v.onTouchEvent(event);
                return true;}
        });
        UserProfileFragment.setListViewHeightBasedOnChildren(listView);
        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToUserChat();
            }
        });

        return rootView;
    }

    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        getCustomWorkoutsForUser(user);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
    }

    private void getCustomWorkoutsForUser(ParseUser user){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Workout");
        query.whereEqualTo("visibility",true);
        query.whereEqualTo("workoutType","custom");
        query.whereEqualTo("userId", ParseUser.createWithoutData("_User",user.getObjectId()));
        query.findInBackground(new FindCallback<ParseObject>() {

            public void done(List<ParseObject> workoutList, ParseException e) {
                if (e == null) {
                    adapter = new ViewUserWorkoutAdapter(getActivity().getApplicationContext(), workoutList);
                    listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getActivity(), "Connection error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void setParseUser(ParseUser selectedUser){
        this.user = selectedUser;
    }

    private void setUserDetails() {
        nearbyUserName.setText(this.user.getString("name"));
        nearbyUserHeight.setText(String.valueOf(this.user.getInt("height")) + " cm");
        nearbyUserWeight.setText(String.valueOf(this.user.getInt("weight")) + " lbs");
        nearbyUserAge.setText(Integer.toString(UserProfileFragment.getAge(this.user.getDate("birthDate"))) + " years");
        ParseFile imageFile = user.getParseFile("profilePic");
        if (imageFile == null) {
            nearbyUserProfilePic.setImageResource(R.drawable.no_user_logo);
        } else {
            imageFile.getDataInBackground(new GetDataCallback() {
                public void done(byte[] data, ParseException e) {
                    if (e == null) {
                        Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                        nearbyUserProfilePic.setImageBitmap(bmp);
                    } else {
                        Toast.makeText(getActivity(), "Connection error", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void moveToChat(){
        getActivity().findViewById(R.id.drawer_layout);
        ((HomePageActivity)getActivity()).navigateTo(3);
        ((HomePageActivity)getActivity()).setTitle("Messsage");
    }

    private void goToUserChat(){
        ParseQuery<ParseUser> query = ParseQuery.getQuery("_User");
        query.fromLocalDatastore();
        query.getInBackground(this.user.getObjectId(), new GetCallback<ParseUser>() {
            public void done(ParseUser object, ParseException e) {
                if (e != null) {
                    user.pinInBackground();
                    ParseObject chatUser = new ParseObject("ChatUsers");
                    chatUser.put("userId", user);
                    chatUser.pinInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if(e==null){
                                moveToChat();
                            }else{
                                Toast.makeText(getActivity(), "Connection error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else{
                    moveToChat();
                }
            }
        });
    }
}