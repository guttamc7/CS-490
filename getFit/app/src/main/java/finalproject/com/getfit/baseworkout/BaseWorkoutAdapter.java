package finalproject.com.getfit.baseworkout;

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

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import java.util.Calendar;
import java.util.List;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.ParseRelation;
import android.content.Context;
import android.widget.Toast;

import finalproject.com.getfit.R;

public class BaseWorkoutAdapter extends BaseSwipeAdapter {
    private LayoutInflater inflater;
    private List<ParseObject> workoutItems;
    private Context context;
    private SwipeLayout swipeLayout;
    private TextView title;
    private TextView description;
    public BaseWorkoutAdapter(Context context, List<ParseObject> workoutItems) {
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

        swipeLayout = (SwipeLayout)v.findViewById(getSwipeLayoutResourceId(position));

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
                Toast.makeText(context, "Liked", Toast.LENGTH_SHORT).show();
                likeWorkout(workoutItems.get(position));
                
            }
        });

        swipeLayout.findViewById(R.id.schedule_imview).setOnClickListener(new View.OnClickListener() {
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
        return v;
    }

    @Override
    public void fillValues(int position, View convertView) {
        ImageView thumbNail = (ImageView) convertView
                .findViewById(R.id.thumbnail);
        title = (TextView) convertView.findViewById(R.id.title);
        description = (TextView)convertView.findViewById(R.id.description);
        Button likes  = (Button) convertView.findViewById(R.id.baseworkout_likes);
        ParseObject workout = workoutItems.get(position);

        // thumbnail image
        if(workout.getInt("level") == 1){
            thumbNail.setImageResource(R.drawable.ic_level1);
            title.setTextColor(R.string.level1_color);
        }
        else if(workout.getInt("level") == 2){
            thumbNail.setImageResource(R.drawable.ic_level2);
            title.setTextColor(R.string.level2_color);

        }
        else {
            thumbNail.setImageResource(R.drawable.ic_level3);
            title.setTextColor(R.string.level3_color);
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



    }
