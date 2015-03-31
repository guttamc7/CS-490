package finalproject.com.getfit.userprofile;

/**
 * Created by Gurumukh on 2/4/15.
 */
import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.Calendar;
import java.util.Date;
import android.widget.ImageView;
import android.widget.TextView;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;

import finalproject.com.getfit.EditProfileDialog;
import finalproject.com.getfit.R;
import finalproject.com.getfit.viewpager.RootFragment;

public class ProfileFragment extends RootFragment
{

    FloatingActionButton editProfileButton;
    ImageView profilePic;
    TextView userName;
    TextView userAge;
    TextView userWeight;
    TextView userHeight;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        profilePic = (ImageView) rootView.findViewById(R.id.ProfilePic);
        userName = (TextView) rootView.findViewById(R.id.username_profile);
        userAge = (TextView) rootView.findViewById(R.id.age_profile);
        userWeight = (TextView) rootView.findViewById(R.id.weight_profile);
        userHeight = (TextView) rootView.findViewById(R.id.height_profile);
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
        editProfileButton.setSize(FloatingActionButton.SIZE_NORMAL);
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

    public static int getAge(Date dateOfBirth) {
        Calendar dob = Calendar.getInstance();
        dob.setTime(dateOfBirth);
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

}