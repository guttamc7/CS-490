package com.gym8.findnearby;

import android.app.AlertDialog;
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

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.gym8.ErrorHandlingAlertDialogBox;
import com.gym8.baseworkout.BaseWorkoutDetailsFragment;
import com.gym8.messages.ChatMessaging;
import com.gym8.messages.MessagesFragment;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import com.gym8.main.R;
import com.gym8.userprofile.UserProfileFragment;
import com.gym8.viewpager.RootFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gurumukh on 3/12/15.
 */
public class FindNearbyUserProfileFragment extends RootFragment {
    private ParseUser user;
    private TextView nearbyUserName;
    private TextView nearbyUserHeight;
    private TextView nearbyUserWeight;
    private TextView nearbyUserAge;
    private ImageView nearbyUserProfilePic;
    private List<ParseObject> customWorkoutList = new ArrayList<>();
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
       //TODO
       getCustomWorkoutsForUser(user);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //UserProfileWorkout data = (UserProfileWorkout)listView.getItemAtPosition(position);
                FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
    }

    private void onPostExecute(){
        //Inform UI
        adapter = new ViewUserWorkoutAdapter(getActivity().getApplicationContext(), customWorkoutList);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void getCustomWorkoutsForUser(ParseObject user){

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Workout");
        query.whereEqualTo("visibility",true);
        query.whereEqualTo("workoutType","custom");
        query.whereEqualTo("userId", ParseUser.createWithoutData("_User",user.getObjectId()));
        query.findInBackground(new FindCallback<ParseObject>() {

            public void done(List<ParseObject> workoutList, ParseException e) {
                if (e == null) {
                    //All the base workouts retrieved
                    customWorkoutList.addAll(workoutList);
                } else {
                    System.out.println(e.getMessage());
                    //Exception
                }
                onPostExecute();
            }
        });
    }
    public void setParseUser(ParseUser selectedUser){
        this.user = selectedUser;
    }

    private void setUserDetails(){
        nearbyUserName.setText(this.user.getString("name"));
        nearbyUserHeight.setText(String.valueOf(this.user.getInt("height")) + " cm");
        nearbyUserWeight.setText(String.valueOf(this.user.getInt("weight")) + " lbs");
        nearbyUserAge.setText(Integer.toString(UserProfileFragment.getAge(this.user.getDate("birthDate"))) + " years");
        ParseFile imageFile = user.getParseFile("profilePic");
        imageFile.getDataInBackground(new GetDataCallback() {
            public void done(byte[] data, ParseException e) {
                if (e == null) {
                    Bitmap bmp = BitmapFactory.decodeByteArray(data, 0,data.length);
                    nearbyUserProfilePic.setImageBitmap(bmp);
                    // data has the bytes for the image
                }
                else
                {
                    ErrorHandlingAlertDialogBox.showDialogBox(getActivity().getBaseContext());
                }
            }
        });
    }

    private void goToUserChat(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("_User");
        query.fromLocalDatastore();
        query.getInBackground(this.user.getObjectId(), new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e != null) {
                    ChatMessaging.saveUserLocally(user);
                }

                FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                ft.replace(R.id.find_nearby_user_frag, new MessagesFragment());
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.addToBackStack("Find Nearby User Profile");
                ft.commit();
            }
        });
    }
}
