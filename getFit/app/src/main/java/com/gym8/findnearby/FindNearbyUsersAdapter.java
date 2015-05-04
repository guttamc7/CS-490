package com.gym8.findnearby;

import android.content.Context;
import android.graphics.drawable.LayerDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseFile;
import com.parse.ParseUser;

import java.util.Date;
import java.util.List;

import com.gym8.main.R;
import com.gym8.imageloader.ImageLoader;

/**
 * Created by Gurumukh on 3/6/15.
 */
public class FindNearbyUsersAdapter extends BaseAdapter {

    private List<ParseUser> nearbyUsers;
    private ImageLoader imageLoader;
    private LayoutInflater layoutInflater;

    public FindNearbyUsersAdapter(Context context, List<ParseUser> nearbyUsers) {
        this.nearbyUsers = nearbyUsers;
        this.layoutInflater = LayoutInflater.from(context);
        this.imageLoader = new ImageLoader(context, 140);
    }

    @Override
    public int getCount() {
        return nearbyUsers.size();
    }

    @Override
    public Object getItem(int position) {
        return nearbyUsers.get(position);
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
        } else {
            holder = (ViewHolder1) convertView.getTag();
        }

        View v = convertView.findViewById(R.id.front_findNearby);
        LayerDrawable bgDrawable = (LayerDrawable) v.getBackground();

        holder.userName.setText(nearbyUsers.get(position).getString("name"));
        long timeDiff = new Date(System.currentTimeMillis()).getTime() - nearbyUsers.get(position).getUpdatedAt().getTime();
        long diffSeconds = timeDiff / 1000;
        long diffMinutes = diffSeconds / 60;
        long diffHours = diffMinutes / 60;
        long diffDays = diffHours / 24;

        if (diffDays > 0) {
            holder.userActive.setText(diffDays + " days ago");
        } else if (diffHours > 0) {
            holder.userActive.setText(diffHours + " hours ago");
        } else if (diffMinutes > 0) {
            holder.userActive.setText(diffMinutes + " minutes ago");
        } else {
            holder.userActive.setText("1 minute ago");
        }

        int distance = (int) ParseUser.getCurrentUser().getParseGeoPoint("location").distanceInMilesTo(nearbyUsers.get(position).getParseGeoPoint("location")) + 1;
        holder.userDistance.setText("Within " + distance + " miles, ");
        ParseFile imageFile = nearbyUsers.get(position).getParseFile("profilePic");
        if (imageFile == null) {
            holder.userProfile.setImageResource(R.drawable.no_user_logo);
        } else {
            imageLoader.DisplayImage(nearbyUsers.get(position).getParseFile("profilePic").getUrl(), holder.userProfile);
        }
        return convertView;
    }

    static class ViewHolder1 {
        TextView userName;
        TextView userActive;
        TextView userDistance;
        ImageView userProfile;
    }
}