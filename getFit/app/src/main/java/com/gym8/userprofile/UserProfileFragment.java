package com.gym8.userprofile;

/**
 * Created by Gurumukh on 2/4/15.
 */
import android.app.DialogFragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import com.gym8.main.EditProfileDialog;
import com.gym8.main.R;
import com.gym8.viewpager.RootFragment;

public class UserProfileFragment extends RootFragment
{

    FloatingActionButton editProfileButton;
    ImageView profilePic;
    TextView userName;
    TextView userAge;
    TextView userWeight;
    TextView userHeight;
    private List<ParseObject> likedWorkoutList = new ArrayList<>();
    private ListView listView;
    private UserProfileAdapter adapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        profilePic = (ImageView) rootView.findViewById(R.id.ProfilePic);
        userName = (TextView) rootView.findViewById(R.id.username_profile);
        userAge = (TextView) rootView.findViewById(R.id.age_profile);
        userWeight = (TextView) rootView.findViewById(R.id.weight_profile);
        userHeight = (TextView) rootView.findViewById(R.id.height_profile);
        listView = (ListView) rootView.findViewById(R.id.user_likes);
        ParseUser currentUser = ParseUser.getCurrentUser();
        ParseFile imageFile = currentUser.getParseFile("profilePic");
        imageFile.getDataInBackground(new GetDataCallback() {
            public void done(byte[] data, ParseException e) {
                if (e == null) {
                    Bitmap bmp = BitmapFactory.decodeByteArray(data, 0,
                            data.length);
                    profilePic.setImageBitmap(bmp);
                    // data has the bytes for the image
                } else {
                    // something went wrong
                }
            }
        });
        if(currentUser.getString("name").isEmpty())
        {
            userName.setText("");
        }
        else
        {
            userName.setText(currentUser.getString("name"));
        }
        if(Integer.toString(currentUser.getInt("weight")).isEmpty())
        {
            userWeight.setText("");
        }
        else
        {
            userWeight.setText(Integer.toString(currentUser.getInt("weight")) + " lbs");
        }
        if(Integer.toString(currentUser.getInt("height")).isEmpty())
        {
            userHeight.setText("");
        }
        else
        {
            userHeight.setText(Integer.toString(currentUser.getInt("height")) + " cm");
        }

        Date date = currentUser.getDate("birthDate");
        if(date == null)
        {
            userAge.setText("");
        }
        else
        {
            userAge.setText(Integer.toString(getAge(date)) + " years old");
        }
        editProfileButton = (FloatingActionButton) rootView.findViewById(R.id.edit_profile_fab);
        editProfileButton.setSize(FloatingActionButton.SIZE_MINI);
        editProfileButton.setColorNormalResId(R.color.button_red);
        editProfileButton.setColorPressedResId(R.color.button_yellow);
        editProfileButton.setIcon(R.drawable.ic_action_edit);
        editProfileButton.setStrokeVisible(false);
        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                DialogFragment dialogFrag = new EditProfileDialog();
                dialogFrag.show(getActivity().getFragmentManager().beginTransaction(), "dialog");

                     }
        });
        return rootView;
    }

    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        getLikedWorkouts();
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

    public static int getAge(Date dateOfBirth) {
        Calendar dob = Calendar.getInstance();
        if(dateOfBirth == null)
        {
            //do nothing
        }
        else
        {
            dob.setTime(dateOfBirth);
        }
        Calendar today = Calendar.getInstance();
        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        if (today.get(Calendar.MONTH) < dob.get(Calendar.MONTH)) {
            age--;
        } else if (today.get(Calendar.MONTH) == dob.get(Calendar.MONTH)
                && today.get(Calendar.DAY_OF_MONTH) < dob.get(Calendar.DAY_OF_MONTH)) {
            age--;
        }
        return age;
    }

    public void onResume()
    {
        super.onResume();
        //adapter.notifyDataSetChanged();
    }

    private void onPostExecute(){
        //Inform UI
        adapter = new UserProfileAdapter(getActivity().getApplicationContext(), likedWorkoutList);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void getLikedWorkouts(){
        ParseUser user = ParseUser.getCurrentUser();
        ParseRelation<ParseObject> relation = user.getRelation("likedWorkout");
        ParseQuery<ParseObject> query = relation.getQuery();
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> workoutList, ParseException e) {
                if (e == null) {
                    likedWorkoutList.addAll(workoutList);
                } else {
                    System.out.println(e.getMessage());
                }
                onPostExecute();
            }
        });
    }

}
