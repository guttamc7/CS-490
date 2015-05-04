package com.gym8.trendingworkout;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.parse.ParseObject;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.util.Calendar;
import java.util.List;

import com.gym8.main.R;

/**
 * Created by rishabhmittal on 3/29/15.
 */
public class TrendingWorkoutAdapter extends BaseSwipeAdapter {
    private LayoutInflater inflater;
    private List<ParseObject> trendingWorkouts;
    private Context context;
    private TextView title;
    private TextView description;
    private Button likes;
    private boolean liked = false;

    public TrendingWorkoutAdapter(Context context, List<ParseObject> workoutItems) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.trendingWorkouts= workoutItems;
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe_trendingWorkout;
    }

    @Override
    public View generateView(final int position, ViewGroup parent) {
        View v = LayoutInflater.from(context).inflate(R.layout.trendingworkout_row, null);
        View rowColor = v.findViewById(R.id.view_row_trendingWorkout);
        final SwipeLayout swipeLayout = (SwipeLayout) v.findViewById(getSwipeLayoutResourceId(position));

        final ImageView likeClicked = (ImageView) v.findViewById(R.id.like_imview_trendingWorkout);
        swipeLayout.findViewById(R.id.like_imview_trendingWorkout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == likeClicked) {
                    if(!liked) {
                        Toast.makeText(context, "Liked", Toast.LENGTH_SHORT).show();
                        likeWorkout(trendingWorkouts.get(position));
                        likes.setText(Integer.toString(trendingWorkouts.get(position).getInt("likes") + 1));
                        likeClicked.setImageResource(R.drawable.ic_action_dontlike);
                        liked = true;
                        swipeLayout.close(true);
                    }
                    else {
                        Toast.makeText(context, "Disliked", Toast.LENGTH_SHORT).show();
                        dislikeWorkout(trendingWorkouts.get(position));
                        likes.setText(Integer.toString(trendingWorkouts.get(position).getInt("likes") - 1));
                        likeClicked.setImageResource(R.drawable.ic_action_like_white);
                        liked = false;
                        swipeLayout.close(true);

                    }
                    notifyDataSetChanged();
                }
            }
        });
        swipeLayout.findViewById(R.id.schedule_imview_trendingWorkout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swipeLayout.close(true);
                Calendar cal = Calendar.getInstance();
                Intent calIntent = new Intent(Intent.ACTION_INSERT);
                calIntent.setType("vnd.android.cursor.item/event");
                calIntent.putExtra("title", title.getText().toString());
                calIntent.putExtra("beginTime", cal.getTimeInMillis());
                calIntent.putExtra("endTime", cal.getTimeInMillis() + 60 * 60 * 1000);
                calIntent.putExtra("description", description.getText().toString());
                calIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(calIntent);
            }
        });

        return v;
    }

    @Override
    public void fillValues(int position, View convertView) {
        ImageView thumbNail = (ImageView) convertView.findViewById(R.id.thumbnail_trendingWorkout);
        title = (TextView) convertView.findViewById(R.id.title_trendingWorkout);
        description = (TextView) convertView.findViewById(R.id.description_trendingWorkout);
        likes = (Button) convertView.findViewById(R.id.like_trendingWorkout);
        ParseObject workout = trendingWorkouts.get(position);

        // thumbnail image
        if (workout.getInt("level") == 1) {
            thumbNail.setImageResource(R.drawable.ic_level1);

        } else if (workout.getInt("level") == 2) {
            thumbNail.setImageResource(R.drawable.ic_level2);
        } else {
            thumbNail.setImageResource(R.drawable.ic_level3);
        }

        title.setText(workout.getString("name"));
        description.setText(workout.getString("description"));
        likes.setText(Integer.toString(workout.getInt("likes")));
    }

    @Override
    public int getCount() {
        return trendingWorkouts.size();
    }

    @Override
    public Object getItem(int location) {
        return trendingWorkouts.get(location);
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

    private void dislikeWorkout(ParseObject workout) {
        if (workout.getInt("likes") > 0) {
            workout.put("likes", (workout.getInt("likes") - 1));
            workout.saveInBackground();
        }

        ParseUser user = ParseUser.getCurrentUser();
        ParseRelation<ParseObject> relation = user.getRelation("likedWorkout");
        relation.remove(workout);
        user.saveInBackground();
    }
}