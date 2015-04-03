package finalproject.com.getfit.findnearby;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Date;

import finalproject.com.getfit.R;
import finalproject.com.getfit.imageloader.ImageLoader;

/**
 * Created by Gurumukh on 3/6/15.
 */
public class FindNearbyUsersAdapter extends BaseAdapter {

    private ArrayList<ParseUser> listData;
    ImageLoader imageLoader;
    private LayoutInflater layoutInflater;

    public FindNearbyUsersAdapter(Context context, ArrayList<ParseUser> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(context);
        imageLoader = new ImageLoader(context,140);
    }
    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder1 holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.find_nearby_users_row, null);
            holder = new ViewHolder1();
            holder.userName = (TextView) convertView.findViewById(R.id.grid_UserName);
            holder.userActive = (TextView) convertView.findViewById(R.id.grid_UserActive);
            holder.userDistance = (TextView) convertView.findViewById(R.id.grid_UserDistance);
            holder.userProfile = (ImageView) convertView.findViewById(R.id.grid_profile_image);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder1) convertView.getTag();
        }
        //Set all the values in the list
        holder.userName.setText(listData.get(position).getString("name"));

        long timeDiff = new Date(System.currentTimeMillis()).getTime() - listData.get(position).getUpdatedAt().getTime();
        long diffSeconds = timeDiff / 1000;
        long diffMinutes = diffSeconds / 60;
        long diffHours = diffMinutes / 60;
        long diffDays = diffHours / 24;

        if(diffDays>0) {
            holder.userActive.setText(diffDays+" days ago");
        }
        else
        if(diffHours>0){
            holder.userActive.setText(diffHours+" hours ago");
        }
        else
        if(diffMinutes>0){
            holder.userActive.setText(diffMinutes+" minutes ago");
        }
        else{
            holder.userActive.setText("1 minute ago");
        }

        int distance = (int)ParseUser.getCurrentUser().getParseGeoPoint("location").distanceInMilesTo(listData.get(position).getParseGeoPoint("location"))+1;
        holder.userDistance.setText("Within "+distance+" miles, ");
        imageLoader.DisplayImage(listData.get(position).getParseFile("profilePic").getUrl(),holder.userProfile);
        return convertView;

    }
    static class ViewHolder1 {
        TextView userName;
        TextView userActive;
        TextView userDistance;
        ImageView userProfile;
    }

}

