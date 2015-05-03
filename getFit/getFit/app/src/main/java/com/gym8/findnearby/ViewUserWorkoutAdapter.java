package com.gym8.findnearby;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.gym8.main.R;
import com.parse.ParseObject;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.util.Calendar;
import java.util.List;

/**
 * Created by rishabhmittal on 4/30/15.
 */
public class ViewUserWorkoutAdapter extends BaseSwipeAdapter {
    private LayoutInflater inflater;
    private List<ParseObject> workoutItems_findnearbyuser;
    private Context context;
    private SwipeLayout swipeLayout_findnearbyuser;
    private TextView title_findnearbyuser;
    private TextView description_findnearbyuser;
    private Button likes_findnearbyuser;
    private boolean liked_findnearbyuser = false;
    public ViewUserWorkoutAdapter(Context context, List<ParseObject> workoutItems) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.workoutItems_findnearbyuser = workoutItems;
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe_findnearbyusers;
    }

    @Override
    public View generateView(final int position, ViewGroup parent) {
        View v = LayoutInflater.from(context).inflate(R.layout.find_nearby_matched_user_likes, null);

        swipeLayout_findnearbyuser = (SwipeLayout)v.findViewById(getSwipeLayoutResourceId(position));

        swipeLayout_findnearbyuser.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {


            }
        });

        final ImageView likeClicked = (ImageView) v.findViewById(R.id.like_imview_findnearby);
        swipeLayout_findnearbyuser.findViewById(R.id.like_imview_findnearby).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v == likeClicked) {
                    if(!liked_findnearbyuser) {
                        Toast.makeText(context, "Liked", Toast.LENGTH_SHORT).show();
                        likeWorkout(workoutItems_findnearbyuser.get(position));
                        likes_findnearbyuser.setText(Integer.toString(workoutItems_findnearbyuser.get(position).getInt("likes") + 1));
                        likeClicked.setImageResource(R.drawable.ic_action_dontlike);
                        liked_findnearbyuser = true;

                    }
                    else {
                        Toast.makeText(context, "Disliked", Toast.LENGTH_SHORT).show();
                        dislikeWorkout(workoutItems_findnearbyuser.get(position));
                        likes_findnearbyuser.setText(Integer.toString(workoutItems_findnearbyuser.get(position).getInt("likes") - 1));
                        likeClicked.setImageResource(R.drawable.ic_action_like_white);
                        liked_findnearbyuser = false;

                    }
                    notifyDataSetChanged();
                    swipeLayout_findnearbyuser.close(true);
                }

            }
        });

        swipeLayout_findnearbyuser.findViewById(R.id.schedule_imview_findnearby).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swipeLayout_findnearbyuser.close(true);
                Calendar cal = Calendar.getInstance();
                Intent calIntent = new Intent(Intent.ACTION_INSERT);
                calIntent.setType("vnd.android.cursor.item/event");
                calIntent.putExtra("title", title_findnearbyuser.getText().toString());
                calIntent.putExtra("beginTime", cal.getTimeInMillis());
                calIntent.putExtra("endTime", cal.getTimeInMillis()+60*60*1000);
                calIntent.putExtra("description", description_findnearbyuser.getText().toString());
                calIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(calIntent);

            }
        });
        return v;
    }

    @Override
    public void fillValues(int position, View convertView) {
        ImageView thumbNail = (ImageView) convertView
                .findViewById(R.id.thumbnail_findnearbyuser);
        title_findnearbyuser = (TextView) convertView.findViewById(R.id.title_findnearbyusers);
        description_findnearbyuser = (TextView)convertView.findViewById(R.id.description_findnearbyusers);
        likes_findnearbyuser  = (Button) convertView.findViewById(R.id.nearby_userlikes_button);
        ParseObject workout = workoutItems_findnearbyuser.get(position);

        // thumbnail image
        if(workout.getInt("level") == 1){
            thumbNail.setImageResource(R.drawable.ic_level1);
        }
        else if(workout.getInt("level") == 2){
            thumbNail.setImageResource(R.drawable.ic_level2);

        }
        else {
            thumbNail.setImageResource(R.drawable.ic_level3);
        }

        // title
        title_findnearbyuser.setText(workout.getString("name"));
        description_findnearbyuser.setText(workout.getString("description"));
        likes_findnearbyuser.setText(Integer.toString(workout.getInt("likes")));
    }

    @Override
    public int getCount() {
        return workoutItems_findnearbyuser.size();
    }

    @Override
    public Object getItem(int location) {
        return workoutItems_findnearbyuser.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private void likeWorkout(ParseObject workout) {
        workout.increment("likes");
        workout.saveInBackground();

        ParseUser user = ParseUser.getCurrentUser();
        ParseRelation<ParseObject> relation = user.getRelation("likedWorkout");
        relation.add(workout);
        user.saveInBackground();
    }
    private void dislikeWorkout(ParseObject workout)
    {
        if(workout.getInt("likes")>0) {
            workout.put("likes", (workout.getInt("likes") - 1));
            workout.saveInBackground();
        }
        ParseUser user = ParseUser.getCurrentUser();
        ParseRelation<ParseObject> relation = user.getRelation("likedWorkout");
        relation.remove(workout);
        user.saveInBackground();

    }



}
