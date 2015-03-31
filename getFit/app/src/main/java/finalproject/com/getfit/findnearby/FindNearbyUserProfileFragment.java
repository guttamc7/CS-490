package finalproject.com.getfit.findnearby;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;

import finalproject.com.getfit.R;
import finalproject.com.getfit.userprofile.ProfileFragment;
import finalproject.com.getfit.viewpager.RootFragment;

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
    private FloatingActionsMenu userProfileActions;
    private FloatingActionButton chatButton;
    private FloatingActionButton viewWorkoutButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_find_nearby_user_profile, container, false);
        nearbyUserName = (TextView) rootView.findViewById(R.id.nearbyUserName);
        nearbyUserAge = (TextView) rootView.findViewById(R.id.nearbyUserAge);
        nearbyUserHeight = (TextView) rootView.findViewById(R.id.nearbyUserHeight);
        nearbyUserWeight = (TextView) rootView.findViewById(R.id.nearbyUserWeight);
        nearbyUserProfilePic = (ImageView) rootView.findViewById(R.id.nearbyUserProfilePic);
        userProfileActions = (FloatingActionsMenu) rootView.findViewById(R.id.user_profile_actions);
        chatButton = (FloatingActionButton) rootView.findViewById(R.id.chat_button);

        this.setUserDetails();

        chatButton.setSize(FloatingActionButton.SIZE_NORMAL);
        chatButton.setColorNormalResId(R.color.button_yellow);
        chatButton.setIcon(R.drawable.ic_messages);
        viewWorkoutButton = (FloatingActionButton) rootView.findViewById(R.id.view_workouts_button);
        viewWorkoutButton.setSize(FloatingActionButton.SIZE_NORMAL);
        viewWorkoutButton.setColorNormalResId(R.color.button_green);
        viewWorkoutButton.setIcon(R.drawable.ic_action_list);

        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        viewWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        return rootView;
    }

    public void setParseUser(ParseUser selectedUser){
        this.user = selectedUser;
    }

    private void setUserDetails(){
        this.nearbyUserName.setText(this.user.getString("name"));
        this.nearbyUserHeight.setText(this.user.getInt("height"));
        this.nearbyUserWeight.setText(this.user.getInt("weight"));
        this.nearbyUserAge.setText(ProfileFragment.getAge(this.user.getDate("birthDate")));

        ParseFile imageFile = this.user.getParseFile("profilePic");
        imageFile.getDataInBackground(new GetDataCallback() {
            public void done(byte[] data, ParseException e) {
                if (e == null) {
                    Bitmap bmp = BitmapFactory.decodeByteArray(data, 0,data.length);
                    nearbyUserProfilePic.setImageBitmap(bmp);
                    // data has the bytes for the image
                } else {
                    // something went wrong
                }
            }
        });




    }

}