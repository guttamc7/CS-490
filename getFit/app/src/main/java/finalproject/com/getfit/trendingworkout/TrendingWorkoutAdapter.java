package finalproject.com.getfit.trendingworkout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;

import java.util.List;

import finalproject.com.getfit.R;

/**
 * Created by rishabhmittal on 3/29/15.
 */
public class TrendingWorkoutAdapter extends BaseSwipeAdapter
{
    private LayoutInflater inflater;
    private List<TrendingWorkout> workoutItemsTrending;
    private Context context;

    public TrendingWorkoutAdapter(Context context, List<TrendingWorkout> workoutItems)
    {
        inflater = LayoutInflater.from(context);
        this.context = context;
        workoutItemsTrending = workoutItems;
    }

    @Override
    public int getSwipeLayoutResourceId(int position)
    {
        return R.id.swipe_trendingWorkout;
    }

    @Override
    public View generateView(final int position, ViewGroup parent)
    {
        View v = LayoutInflater.from(context).inflate(R.layout.trendingworkout_row, null);
        SwipeLayout swipeLayout = (SwipeLayout)v.findViewById(getSwipeLayoutResourceId(position));

        swipeLayout.addSwipeListener(
                new SimpleSwipeListener()
                {
            @Override
            public void onOpen(SwipeLayout layout)
            {
                YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.like_imview_trendingWorkout));

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
        swipeLayout.findViewById(R.id.like_imview_trendingWorkout).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(context, "Like", Toast.LENGTH_SHORT).show();
                TrendingWorkout m= workoutItemsTrending.get(position);
                updateLikes( m.getTrendingWorkoutId());
            }
        });
        return v;
    }

    @Override
    public void fillValues(int position, View convertView)
    {
        ImageView thumbNail = (ImageView) convertView.findViewById(R.id.thumbnail_trendingWorkout);
        TextView title = (TextView) convertView.findViewById(R.id.title_trendingWorkout);
        TextView description = (TextView)convertView.findViewById(R.id.description_trendingWorkout);
        TextView username = (TextView)convertView.findViewById(R.id.userName_trendingWorkout);

        TrendingWorkout m = workoutItemsTrending.get(position);
        // thumbnail image
        if(m.getTrendingWorkoutLevel().equals("1"))
        {
            thumbNail.setImageResource(R.drawable.ic_level1);
            title.setTextColor(R.string.level1_color);
        }
        else if(m.getTrendingWorkoutLevel().equals("2"))
        {
            thumbNail.setImageResource(R.drawable.ic_level2);
            title.setTextColor(R.string.level2_color);

        }
        else
        {
            thumbNail.setImageResource(R.drawable.ic_level3);
            title.setTextColor(R.string.level3_color);
        }

        // title
        title.setText(m.getTrendingWorkoutName());
        description.setText(m.getTrendingWorkoutDescription());
        username.setText(m.getTrendingWorkoutUserName());
    }

    @Override
    public int getCount()
    {
        return workoutItemsTrending.size();
    }

    @Override
    public Object getItem(int location)
    {
        return workoutItemsTrending.get(location);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    private void updateLikes(String id)
    {

    }
}
