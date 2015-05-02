package com.gym8.baseworkout;

/**
 * Created by Gurumukh on 2/7/15.
 */
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.ParseRelation;
import android.content.Context;
import android.widget.Toast;
import com.gym8.main.R;

public class BaseWorkoutAdapter extends BaseSwipeAdapter {
    private LayoutInflater inflater;
    private List<ParseObject> workoutItems;
    private Context context;
    private SwipeLayout swipeLayout;
    private TextView title;
    private TextView description;
    private Button likes;
    private boolean liked = false;
    public BaseWorkoutAdapter(Context context, List<ParseObject> baseWorkouts)
    {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.workoutItems = new ArrayList<ParseObject>();
        workoutItems.addAll(baseWorkouts);

    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    @Override
    public View generateView(final int position, ViewGroup parent) {
        View v = LayoutInflater.from(context).inflate(R.layout.baseworkout_row, null);

        swipeLayout = (SwipeLayout)v.findViewById(getSwipeLayoutResourceId(position));


        swipeLayout.setOnDoubleClickListener(new SwipeLayout.DoubleClickListener() {
            @Override
            public void onDoubleClick(SwipeLayout layout, boolean surface) {
                Toast.makeText(context, "DoubleClick", Toast.LENGTH_SHORT).show();
            }
        });
        final ImageView likeClicked = (ImageView) v.findViewById(R.id.like_imview);
        swipeLayout.findViewById(R.id.like_imview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v == likeClicked) {
                    if(!liked) {
                        Toast.makeText(context, "Liked", Toast.LENGTH_SHORT).show();
                        likeWorkout(workoutItems.get(position));
                        likes.setText(Integer.toString(workoutItems.get(position).getInt("likes") + 1));
                        likeClicked.setImageResource(R.drawable.ic_action_dontlike);
                        liked = true;

                    }
                    else {
                        Toast.makeText(context, "Disliked", Toast.LENGTH_SHORT).show();
                        dislikeWorkout(workoutItems.get(position));
                        likes.setText(Integer.toString(workoutItems.get(position).getInt("likes") - 1));
                        likeClicked.setImageResource(R.drawable.ic_action_like_white);
                        liked = false;

                    }
                    notifyDataSetChanged();
                    swipeLayout.close(true);
                }

            }
        });

        swipeLayout.findViewById(R.id.schedule_imview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swipeLayout.close(true);
                Calendar cal = Calendar.getInstance();
                Intent calIntent = new Intent(Intent.ACTION_INSERT);
                calIntent.setType("vnd.android.cursor.item/event");
                calIntent.putExtra("title", title.getText().toString());
                calIntent.putExtra("beginTime", cal.getTimeInMillis());
                calIntent.putExtra("endTime", cal.getTimeInMillis()+60*60*1000);
                calIntent.putExtra("description", description.getText().toString());
                calIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(calIntent);

            }
        });
        return v;
    }

    @Override
    public void fillValues(int position, View convertView) {
        ImageView thumbNail = (ImageView) convertView
                .findViewById(R.id.thumbnail);
        title = (TextView) convertView.findViewById(R.id.title);
        description = (TextView)convertView.findViewById(R.id.description);
        likes  = (Button) convertView.findViewById(R.id.baseworkout_likes);
        ParseObject workout = workoutItems.get(position);

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
        title.setText(workout.getString("name"));
        description.setText(workout.getString("description"));
        likes.setText(Integer.toString(workout.getInt("likes")));
    }

    @Override
    public int getCount() {
        return workoutItems.size();
    }

    @Override
    public Object getItem(int location) {
        return workoutItems.get(location);
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
