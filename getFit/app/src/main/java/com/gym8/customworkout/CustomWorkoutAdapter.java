package com.gym8.customworkout;

/**
 * Created by Gurumukh on 4/3/15.
 */

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
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;

import java.util.Calendar;
import java.util.List;

import com.gym8.main.R;
import com.parse.ParseQuery;
import com.parse.ParseRelation;

public class CustomWorkoutAdapter extends BaseSwipeAdapter
{
    private LayoutInflater inflater;
    private List<ParseObject> userWorkouts;
    private Context context;
    private TextView title, description;
    private SwipeLayout swipeLayout;

    public CustomWorkoutAdapter(Context context, List<ParseObject> userWorkouts)
    {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.userWorkouts = userWorkouts;
    }

    @Override
    public int getSwipeLayoutResourceId(int position)
    {
        return R.id.swipe_custom_workout;
    }

    @Override
    public View generateView(final int position, ViewGroup parent)
    {
        View v = LayoutInflater.from(context).inflate(R.layout.custom_workout_row, null);
        swipeLayout = (SwipeLayout)v.findViewById(getSwipeLayoutResourceId(position));
        swipeLayout.setOnDoubleClickListener(new SwipeLayout.DoubleClickListener()
        {
            @Override
            public void onDoubleClick(SwipeLayout layout, boolean surface)
            {
                Toast.makeText(context, "DoubleClick", Toast.LENGTH_SHORT).show();
            }
        });

        swipeLayout.findViewById(R.id.schedule_imview_custom_workout).setOnClickListener(new View.OnClickListener() {
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

        swipeLayout.findViewById(R.id.delete_imview_custom_workout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Workout Removed", Toast.LENGTH_SHORT).show();
                removeWorkout(userWorkouts.get(position));
                userWorkouts.remove(position);
                notifyDataSetChanged();
                swipeLayout.close(true);

            }
        });

        return v;
    }

    @Override
    public void fillValues(int position, View convertView)
    {
        ImageView thumbNail = (ImageView) convertView.findViewById(R.id.thumbnail_custom_workout);
        title = (TextView) convertView.findViewById(R.id.title_custom_workout);
        description = (TextView)convertView.findViewById(R.id.description_custom_workout);
        Button likes  = (Button) convertView.findViewById(R.id.like_custom_workout);
        ParseObject m = userWorkouts.get(position);
        // thumbnail image
        if(m.getInt("level") == 1)
        {
            thumbNail.setImageResource(R.drawable.ic_level1);

        }
        else if(m.getInt("level") == 2)
        {
            thumbNail.setImageResource(R.drawable.ic_level2);


        }
        else
        {
            thumbNail.setImageResource(R.drawable.ic_level3);

        }

        title.setText(m.getString("name"));
        description.setText(m.getString("description"));
        likes.setText(Integer.toString(m.getInt("likes")));
    }

    @Override
    public int getCount()
    {
        return userWorkouts.size();
    }

    @Override
    public Object getItem(int location)
    {
        return userWorkouts.get(location);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }


    private void removeWorkout(ParseObject workout)
    {
        ParseRelation<ParseObject> relation = workout.getRelation("exercises");
        ParseQuery<ParseObject> query = relation.getQuery();
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> workoutList, ParseException e) {
                if (e == null) {
                    for (int i = 0; i < workoutList.size(); i++) {
                        ParseObject obj = workoutList.get(i);
                        obj.deleteInBackground();
                        obj.saveInBackground();
                    }
                } else {
                    Toast.makeText(context, "Connection error", Toast.LENGTH_SHORT).show();
                }
            }
        });
        workout.deleteInBackground();
        workout.saveInBackground();
    }
}