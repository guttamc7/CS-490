package com.gym8.userprofile;

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
import com.parse.ParseObject;

import java.util.Calendar;
import java.util.List;

import com.gym8.main.R;
import com.parse.ParseRelation;
import com.parse.ParseUser;

/**
 * Created by rishabhmittal on 3/30/15.
 */
public class UserProfileAdapter extends BaseSwipeAdapter
{
    private LayoutInflater inflater;
    private List<ParseObject> likedWorkouts;
    private Context context;
    private TextView title;
    private TextView description;

    public UserProfileAdapter(Context context, List<ParseObject> workoutItems)
    {
        inflater = LayoutInflater.from(context);
        this.context = context;
        likedWorkouts = workoutItems;
    }

    @Override
    public int getSwipeLayoutResourceId(int position)
    {
        return R.id.swipe_user_like;
    }

    @Override
    public View generateView(final int position,final ViewGroup parent)
    {
        View v = LayoutInflater.from(context).inflate(R.layout.user_likes_row, null);
        final SwipeLayout swipeLayout = (SwipeLayout)v.findViewById(getSwipeLayoutResourceId(position));

        swipeLayout.addSwipeListener(
                new SimpleSwipeListener()
                {
                    @Override
                    public void onOpen(SwipeLayout layout)
                    {

                    }
                });
        swipeLayout.setOnDoubleClickListener(new SwipeLayout.DoubleClickListener()
        {
            @Override
            public void onDoubleClick(SwipeLayout layout, boolean surface)
            {
                //Toast.makeText(context, "DoubleClick", Toast.LENGTH_SHORT).show();
            }
        });

        swipeLayout.findViewById(R.id.schedule_imview_user_like).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

        swipeLayout.findViewById(R.id.delete_imview_user_like).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Toast.makeText(context, "Workout Removed", Toast.LENGTH_SHORT).show();
               removeWorkout(likedWorkouts.get(position));
               likedWorkouts.remove(position);
               notifyDataSetChanged();
               swipeLayout.close(true);
            }
        });

        return v;
    }

    @Override
    public void fillValues(int position, View convertView)
    {
        ImageView thumbNail = (ImageView) convertView.findViewById(R.id.thumbnail_user_like);
        title = (TextView) convertView.findViewById(R.id.title_user_like);
        description = (TextView)convertView.findViewById(R.id.description_user_like);
        Button likes  = (Button) convertView.findViewById(R.id.like_user_like);
        ParseObject m = likedWorkouts.get(position);
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
        return likedWorkouts.size();
    }

    @Override
    public Object getItem(int location)
    {
        return likedWorkouts.get(location);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    private void getLikes(String id)
    {

    }

    private void removeWorkout(ParseObject workout)
    {
            ParseUser user = ParseUser.getCurrentUser();
            ParseRelation<ParseObject> relation = user.getRelation("likedWorkout");
            relation.remove(workout);
            user.saveInBackground();


     }
    }
