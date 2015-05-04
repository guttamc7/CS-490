package com.gym8.findnearby;

/**
 * Created by Gurumukh on 2/4/15.
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Handler;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.parse.GetDataCallback;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.ParseException;
import com.skyfishjy.library.RippleBackground;

import com.gym8.main.R;
import com.gym8.viewpager.RootFragment;

public class FindNearbyFragment extends RootFragment {
    private ImageView findNearbyImageView;
    private TextView tapMsgTextView;
    private GPSTracker gps;
    private static double latitude, longitude;
    private FloatingActionButton discoverySettings;
    private RippleBackground rippleBackground;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_findnearby, container, false);
        rippleBackground = (RippleBackground) rootView.findViewById(R.id.content_ripple);
        findNearbyImageView = (ImageView) rootView.findViewById(R.id.imgViewfindNearby);
        discoverySettings = (FloatingActionButton) rootView.findViewById(R.id.discovery_settings);
        discoverySettings.setSize(FloatingActionButton.SIZE_MINI);
        discoverySettings.setColorNormalResId(R.color.button_red);
        discoverySettings.setColorPressedResId(R.color.button_yellow);
        discoverySettings.setIcon(R.drawable.ic_action_settings);
        tapMsgTextView = (TextView) rootView.findViewById(R.id.tap_msg);
        final Handler handler = new Handler();
        ParseFile imageFile = ParseUser.getCurrentUser().getParseFile("profilePic");
        if (imageFile == null) {
            findNearbyImageView.setImageResource(R.drawable.no_user_logo);
        } else {
            imageFile.getDataInBackground(new GetDataCallback() {
                public void done(byte[] data, ParseException e) {
                    if (e == null) {
                        Bitmap bmp = BitmapFactory.decodeByteArray(data, 0,
                                data.length);
                        findNearbyImageView.setImageBitmap(bmp);
                    } else {
                        Toast.makeText(getActivity(), "Connection error", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        discoverySettings.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFrag = new DiscoveryPreferencesDialog();
                dialogFrag.show(getActivity().getFragmentManager().beginTransaction(), "dialog");
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
                        findUsers();
                    }
                }, 3000);
            }
        });
        return rootView;
    }

    static double getLatitude() {
        return latitude;
    }

    static double getLongitude() {
        return longitude;
    }

    private void findUsers() {
        gps = new GPSTracker(getActivity());
        if (gps.canGetLocation()) {
            FindNearbyFragment.latitude = gps.getLatitude();
            FindNearbyFragment.longitude = gps.getLongitude();
        }
        rippleBackground.stopRippleAnimation();
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.replace(R.id.findnearby_frag, new FindNearbyUsersFragment());
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.addToBackStack(null);
        ft.commit();
    }
}
