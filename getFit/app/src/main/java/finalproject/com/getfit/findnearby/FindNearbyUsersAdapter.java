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

import finalproject.com.getfit.R;
import finalproject.com.getfit.imageloader.ImageLoader;

/**
 * Created by Gurumukh on 3/6/15.
 */
public class FindNearbyUsersAdapter extends BaseAdapter {

    private ArrayList<FindNearbyUsers> listData;
    ImageLoader imageLoader;
    private LayoutInflater layoutInflater;

    public FindNearbyUsersAdapter(Context context, ArrayList<FindNearbyUsers> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(context);
        imageLoader = new ImageLoader(context);
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
        System.out.println(listData.get(position).getNearbyUserName());
        holder.userName.setText(listData.get(position).getNearbyUserName());
        //holder.userDistance.setText(listData.get(position).getNearbyUserDistance());
        //holder.userActive.setText(listData.get(position).getNearbyUserActive());
        imageLoader.DisplayImage(listData.get(position).getNearbyUserProfile(),holder.userProfile);
        return convertView;
    }
    static class ViewHolder1 {
        TextView userName;
        TextView userActive;
        TextView userDistance;
        ImageView userProfile;
    }

}

