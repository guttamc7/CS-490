package finalproject.com.getfit.findnearby;

/**
 * Created by Gurumukh on 2/4/15.
 */
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Handler;
import com.parse.GetDataCallback;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.ParseException;
import com.skyfishjy.library.RippleBackground;

import finalproject.com.getfit.R;
import finalproject.com.getfit.viewpager.RootFragment;

public class FindNearbyFragment extends RootFragment {
    private ImageView findNearbyImageView;
<<<<<<< HEAD
    private TextView tapMsgTextView;
    GPSTracker gps;
    private static double latitude;
    private static double longitude;
=======
>>>>>>> FETCH_HEAD

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_findnearby, container, false);
        final RippleBackground rippleBackground=(RippleBackground)rootView.findViewById(R.id.content_ripple);
        findNearbyImageView = (ImageView) rootView.findViewById(R.id.imgViewfindNearby);
<<<<<<< HEAD
        tapMsgTextView = (TextView) rootView.findViewById(R.id.tap_msg);
        ParseUser currentUser = ParseUser.getCurrentUser();
        final Handler handler=new Handler();
        ParseFile imageFile = currentUser.getParseFile("profilePic");
        imageFile.getDataInBackground(new GetDataCallback() {
            public void done(byte[] data, ParseException e) {
                if (e == null) {
                    Bitmap bmp = BitmapFactory.decodeByteArray(data, 0,
                            data.length);
                    findNearbyImageView.setImageBitmap(bmp);
                    // data has the bytes for the image
                } else {
                    // something went wrong
                }
            }
        });

        findNearbyImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tapMsgTextView.setText("");
                rippleBackground.startRippleAnimation();

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                            foundUsers();
                    }
                },4000);
=======
        findNearbyImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.pulse);
                findNearbyImageView.setAnimation(anim);
                FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                ft.replace(R.id.findnearby_frag, new FindNearbyUsersFragment());
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.addToBackStack(null);
                ft.commit();
>>>>>>> FETCH_HEAD

            }
        });
        return rootView;
    }
<<<<<<< HEAD
    public static double getLatitude() {
        return latitude;
    }

    public static double getLongitude() {
        return longitude;
    }

    private void foundUsers() {
        gps = new GPSTracker(getActivity());
        if(gps.canGetLocation()) {
            FindNearbyFragment.latitude = gps.getLatitude();
            FindNearbyFragment.longitude = gps.getLongitude();
        }
        System.out.println(latitude + "  " +longitude);
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.replace(R.id.findnearby_frag, new FindNearbyUsersFragment());
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.addToBackStack(null);
        ft.commit();
    }
=======
>>>>>>> FETCH_HEAD
}
