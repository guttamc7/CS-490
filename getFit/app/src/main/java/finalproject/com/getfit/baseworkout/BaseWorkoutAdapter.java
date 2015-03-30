package finalproject.com.getfit.baseworkout;

/**
 * Created by Gurumukh on 2/7/15.
 */
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import java.util.List;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.ParseRelation;


import android.content.Context;
import android.widget.Toast;

import finalproject.com.getfit.R;
import finalproject.com.getfit.baseworkout.BaseWorkout;

public class BaseWorkoutAdapter extends BaseSwipeAdapter {
    private LayoutInflater inflater;
    private List<BaseWorkout> workoutItems;
    private Context context;
    public BaseWorkoutAdapter(Context context, List<BaseWorkout> workoutItems) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.workoutItems = workoutItems;
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    @Override
    public View generateView(final int position, ViewGroup parent) {
        View v = LayoutInflater.from(context).inflate(R.layout.baseworkout_row, null);
        SwipeLayout swipeLayout = (SwipeLayout)v.findViewById(getSwipeLayoutResourceId(position));

        swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
                YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.like_imview));
                YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.schedule_imview));

            }
        });
        swipeLayout.setOnDoubleClickListener(new SwipeLayout.DoubleClickListener() {
            @Override
            public void onDoubleClick(SwipeLayout layout, boolean surface) {
                Toast.makeText(context, "DoubleClick", Toast.LENGTH_SHORT).show();
            }
        });
        swipeLayout.findViewById(R.id.like_imview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Like", Toast.LENGTH_SHORT).show();
                BaseWorkout m= workoutItems.get(position);

                updatelikes( m.getWorkoutId());
                updateWorkout(m.getWorkoutId());
                retrieveLikedWorkout();
            }
        });

        swipeLayout.findViewById(R.id.schedule_imview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Add to Schedule", Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }

    @Override
    public void fillValues(int position, View convertView) {
        ImageView thumbNail = (ImageView) convertView
                .findViewById(R.id.thumbnail);
        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView description = (TextView)convertView.findViewById(R.id.description);
        BaseWorkout m = workoutItems.get(position);
        // thumbnail image
        if(m.getWorkoutLevel().equals("1")){
            thumbNail.setImageResource(R.drawable.ic_level1);
            title.setTextColor(R.string.level1_color);
        }
        else if(m.getWorkoutLevel().equals("2")){
            thumbNail.setImageResource(R.drawable.ic_level2);
            title.setTextColor(R.string.level2_color);

        }
        else {
            thumbNail.setImageResource(R.drawable.ic_level3);
            title.setTextColor(R.string.level3_color);
        }

        // title
        title.setText(m.getWorkoutName());
        description.setText(m.getWorkoutDescription());
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

    void updatelikes(String id)
    {
        ParseObject workout = ParseObject.createWithoutData("Workout", id);
        workout.increment("likes");
        workout.saveInBackground();


    }
    void updateWorkout (String id)
    {
        ParseObject workout = ParseObject.createWithoutData("Workout", id);
        ParseUser user = ParseUser.getCurrentUser();
        ParseRelation<ParseObject> relation = user.getRelation("likedWorkout");
        relation.add(workout);
        workout.saveInBackground();
        user.saveInBackground();

    }

    void retrieveLikedWorkout ()
    {
        ParseUser user = ParseUser.getCurrentUser();
        ParseRelation<ParseObject> relation = user.getRelation("likedWorkout");
        ParseQuery<ParseObject> query = relation.getQuery();
        query.findInBackground(new FindCallback<ParseObject>() {

            public void done(List<ParseObject> workoutList, ParseException e) {
                if (e == null) {
                    for (int i = 0; i < workoutList.size(); i++) {
                        ParseObject obj = workoutList.get(i);
                        System.out.println(obj.getObjectId());
                        System.out.println(obj.getString("name"));

                    }


                    //All the base workouts retrieved
                } else {
                    System.out.println(e.getMessage());
                    //Exception
                }
            }
        });



    }


}
