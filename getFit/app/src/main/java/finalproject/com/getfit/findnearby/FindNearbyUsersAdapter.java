package finalproject.com.getfit.findnearby;

import android.content.Context;
import android.media.Image;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

import finalproject.com.getfit.R;

/**
 * Created by Gurumukh on 3/6/15.
 */
public class FindNearbyUsersAdapter extends ParseQueryAdapter<ParseObject> {

    public FindNearbyUsersAdapter(Context context) {
        super(context, new ParseQueryAdapter.QueryFactory<ParseObject>() {
            public ParseQuery create() {
                //TODO Write Query Here
                //Save the current Users location
                ParseGeoPoint userLocation = new ParseGeoPoint(FindNearbyFragment.getLatitude(), FindNearbyFragment.getLongitude());
                ParseUser currentUser = ParseUser.getCurrentUser();
                currentUser.put("location",userLocation);
                currentUser.saveInBackground();

                ParseQuery<ParseUser> query = ParseQuery.getQuery("_User");
                query.whereNotEqualTo("objectId",currentUser.getObjectId());
                query.whereWithinMiles("location", userLocation, 50.0);
                query.setLimit(10);
                return query;
            }
        });
    }
    @Override
    public View getItemView(ParseObject object, View v, ViewGroup parent) {
        if (v == null) {
            v = View.inflate(getContext(), R.layout.find_nearby_users_row, null);
        }

        super.getItemView(object, v, parent);

        // Add and download the image
        ParseImageView profileImage = (ParseImageView) v.findViewById(R.id.grid_profile_image);
        ParseFile imageFile = object.getParseFile("profilePic");

        if (imageFile != null) {
            profileImage.setParseFile(imageFile);
            profileImage.loadInBackground();
        }

        // Add the title view
        TextView titleTextView = (TextView) v.findViewById(R.id.grid_name);
        titleTextView.setText(object.getString("name"));
        return v;
    }
}
