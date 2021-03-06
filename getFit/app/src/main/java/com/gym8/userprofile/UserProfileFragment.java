package com.gym8.userprofile;

/**
 * Created by Gurumukh on 2/4/15.
 */


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.MeasureSpec;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import com.gym8.main.R;
import com.gym8.viewpager.RootFragment;

public class UserProfileFragment extends RootFragment {

    private TextView userName;
    private static TextView userAge;
    private static TextView userWeight;
    private static TextView userHeight;
    private FloatingActionButton editProfileButton;
    private static ImageView profilePic;
    private ListView listView;
    private UserProfileAdapter adapter;
    public static final int DIALOG_FRAGMENT = 1;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        profilePic = (ImageView) rootView.findViewById(R.id.ProfilePic);
        userName = (TextView) rootView.findViewById(R.id.username_profile);
        userAge = (TextView) rootView.findViewById(R.id.age_profile);
        userWeight = (TextView) rootView.findViewById(R.id.weight_profile);
        userHeight = (TextView) rootView.findViewById(R.id.height_profile);
        listView = (ListView) rootView.findViewById(R.id.user_likes);
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
                return true;
            }
        });
        setListViewHeightBasedOnChildren(listView);
        setUserAttributes();
        editProfileButton = (FloatingActionButton) rootView.findViewById(R.id.edit_profile_fab);
        editProfileButton.setSize(FloatingActionButton.SIZE_MINI);
        editProfileButton.setColorNormalResId(R.color.button_green);
        editProfileButton.setColorPressedResId(R.color.button_yellow);
        editProfileButton.setIcon(R.drawable.ic_action_edit);
        editProfileButton.setStrokeVisible(false);
        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        return rootView;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLikedWorkouts();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

    private void setUserAttributes() {
        ParseUser currentUser = ParseUser.getCurrentUser();
        ParseFile imageFile = currentUser.getParseFile("profilePic");
        if (imageFile == null)
            profilePic.setImageResource(R.drawable.no_user_logo);
        else {
            imageFile.getDataInBackground(new GetDataCallback() {
                public void done(byte[] data, ParseException e) {
                    if (e == null) {
                        Bitmap bmp = BitmapFactory.decodeByteArray(data, 0,
                                data.length);
                        profilePic.setImageBitmap(bmp);
                    } else {
                        Toast.makeText(getActivity(), "Connection error", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        if (currentUser.getString("name").isEmpty()) {
            userName.setText("");
        } else {
            userName.setText(currentUser.getString("name"));
        }
        if (Integer.toString(currentUser.getInt("weight")).isEmpty()) {
            userWeight.setText("");
        } else {
            userWeight.setText(Integer.toString(currentUser.getInt("weight")) + " lbs");
        }
        if (Integer.toString(currentUser.getInt("height")).isEmpty()) {
            userHeight.setText("");
        } else {
            userHeight.setText(Integer.toString(currentUser.getInt("height")) + " cm");
        }

        Date date = currentUser.getDate("birthDate");
        if (date == null) {
            userAge.setText("");
        } else {
            userAge.setText(Integer.toString(getAge(date)) + " years old");
        }
    }

    private void showDialog() {
        EditProfileDialog dialogFrag = new EditProfileDialog();
        dialogFrag.setTargetFragment(getParentFragment(), DIALOG_FRAGMENT);
        dialogFrag.show(getFragmentManager(), "dialog");
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = MeasureSpec.makeMeasureSpec(listView.getWidth(), MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, MeasureSpec.UNSPECIFIED);

            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    public static int getAge(Date dateOfBirth) {
        Calendar dob = Calendar.getInstance();
        if (!(dateOfBirth == null)) {
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

    @Override
    public void onResume() {
        super.onResume();
    }

    private void getLikedWorkouts() {
        ParseUser user = ParseUser.getCurrentUser();
        ParseRelation<ParseObject> relation = user.getRelation("likedWorkout");
        ParseQuery<ParseObject> query = relation.getQuery();
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> workoutList, ParseException e) {
                if (e == null) {
                    adapter = new UserProfileAdapter(getActivity().getApplicationContext(), workoutList);
                    listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getActivity(), "Connection error", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public static TextView getUserHeight() {
        return userHeight;
    }

    public static TextView getUserWeight() {
        return userWeight;
    }

    public static TextView getUserAge() {
        return userAge;
    }

    public static ImageView getProfilePic() {
        return profilePic;
    }

}
