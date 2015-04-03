package finalproject.com.getfit.customworkout;

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

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import finalproject.com.getfit.R;

public class CustomWorkoutAdapter extends BaseSwipeAdapter
{
    private LayoutInflater inflater;
    private ArrayList<ParseObject> likedWorkouts;
    private Context context;
    private TextView title;
    private TextView description;

    public CustomWorkoutAdapter(Context context, ArrayList<ParseObject> workoutItems)
    {
        inflater = LayoutInflater.from(context);
        this.context = context;
        likedWorkouts = workoutItems;
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
        SwipeLayout swipeLayout = (SwipeLayout)v.findViewById(getSwipeLayoutResourceId(position));

        swipeLayout.addSwipeListener(
                new SimpleSwipeListener()
                {
                    @Override
                    public void onOpen(SwipeLayout layout)
                    {
                        YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.schedule_imview_custom_workout));
                        YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.delete_imview_custom_workout));
                    }
                });
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
                //TODO : Delete user like

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
        ParseObject m = likedWorkouts.get(position);
        // thumbnail image
        if(m.getInt("level") == 1)
        {
            thumbNail.setImageResource(R.drawable.ic_level1);
            title.setTextColor(R.string.level1_color);
        }
        else if(m.getInt("level") == 2)
        {
            thumbNail.setImageResource(R.drawable.ic_level2);
            title.setTextColor(R.string.level2_color);

        }
        else
        {
            thumbNail.setImageResource(R.drawable.ic_level3);
            title.setTextColor(R.string.level3_color);
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


    public void addWorkout(){
     ParseObject workout = new ParseObject("Workout");
     workout.add("name",""/**/);
     workout.add("workoutType","custom");
     workout.add("level",-1/**/);
     workout.add("likes",0);
     workout.add("userId", ParseUser.getCurrentUser());
     workout.add("description",""/**/);

     ParseRelation<ParseObject> relation = workout.getRelation("exercises");

     int numOfExercises = 0;
    for(int n=0;n <numOfExercises;n++){
        ParseObject workoutExercises = new ParseObject("WorkoutExercises");
        workoutExercises.add("sets",0/**/);
        workoutExercises.add("reps",0/**/);
        //workoutExercises.add("exerciseId",/*ParseObject of exerrcise*/); //Uncomment
        workoutExercises.saveInBackground();
        relation.add(workoutExercises);

    }

    workout.saveInBackground();
    }

    public void retrieveCustomWorkout(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Workout");
        query.whereEqualTo("userId", ParseUser.getCurrentUser());
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> workoutList, ParseException e) {
                if (e == null) {

                    for(int n=0;n<workoutList.size();n++){
                        //name
                        //description
                        //likes
                        //level
                        ParseRelation<ParseObject> exercises = workoutList.get(n).getRelation("exercises");
                        List<ParseObject> workoutExercises = null;
                        try {
                            workoutExercises = exercises.getQuery().find();

                            for(int m=0;n<workoutExercises.size();m++){
                                workoutExercises.get(m).get("reps");
                                workoutExercises.get(m).get("sets");
                                ParseObject exercise = workoutExercises.get(m).getParseObject("exerciseId");
                            }

                        } catch (ParseException e1) {
                            e1.printStackTrace();
                        }


                    }

                } else {
                  //  Log.d("score", "Error: " + e.getMessage());
                }
            }
        });


    }
}