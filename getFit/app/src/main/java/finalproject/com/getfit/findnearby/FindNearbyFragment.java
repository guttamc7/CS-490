package finalproject.com.getfit.findnearby;

/**
 * Created by Gurumukh on 2/4/15.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import finalproject.com.getfit.R;
import finalproject.com.getfit.baseworkout.BaseWorkoutDetailsFragment;
import finalproject.com.getfit.viewpager.RootFragment;

public class FindNearbyFragment extends RootFragment {
    private ImageView findNearbyImageView;
    GPSTracker gps;
    private double latitude;
    private double longitude;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_findnearby, container, false);
        findNearbyImageView = (ImageView) rootView.findViewById(R.id.imgViewfindNearby);

        findNearbyImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gps = new GPSTracker(getActivity());
                if(gps.canGetLocation()) {
                    latitude = gps.getLatitude();
                    longitude = gps.getLongitude();
                }
                Animation anim = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.pulse);
                findNearbyImageView.setAnimation(anim);
                System.out.println(latitude + "  " +longitude);
                FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                ft.replace(R.id.findnearby_frag, new FindNearbyUsersFragment());
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.addToBackStack(null);
                ft.commit();

            }
        });
        return rootView;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
