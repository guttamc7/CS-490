package finalproject.com.getfit.userprofile;

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
 * Created by rishabhmittal on 3/30/15.
 */
public class UserProfileAdapter extends BaseSwipeAdapter
{
    private LayoutInflater inflater;
    private List<UserProfileWorkout> likedWorkoutsProfile;
    private Context context;

    public UserProfileAdapter(Context context, List<UserProfileWorkout> workoutItems)
    {
        inflater = LayoutInflater.from(context);
        this.context = context;
        likedWorkoutsProfile = workoutItems;
    }

    @Override
    public int getSwipeLayoutResourceId(int position)
    {
        return R.id.swipe_user_like;
    }

    @Override
    public View generateView(final int position, ViewGroup parent)
    {
        View v = LayoutInflater.from(context).inflate(R.layout.user_likes_row, null);
        SwipeLayout swipeLayout = (SwipeLayout)v.findViewById(getSwipeLayoutResourceId(position));

        swipeLayout.addSwipeListener(
                new SimpleSwipeListener()
                {
                    @Override
                    public void onOpen(SwipeLayout layout)
                    {
                        YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.like_imview_user_like));
                        YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.delete_imview_user_like));
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
                UserProfileWorkout m= likedWorkoutsProfile.get(position);
                //TODO :get likes method
                getLikes("");
            }
        });
        return v;
    }

    @Override
    public void fillValues(int position, View convertView)
    {
        ImageView thumbNail = (ImageView) convertView.findViewById(R.id.thumbnail_user_like);
        TextView title = (TextView) convertView.findViewById(R.id.title_user_like);
        TextView description = (TextView)convertView.findViewById(R.id.description_user_like);

        UserProfileWorkout m = likedWorkoutsProfile.get(position);
        // thumbnail image
        if(m.getUserProfileWorkoutLevel().equals("1"))
        {
            thumbNail.setImageResource(R.drawable.ic_level1);
            title.setTextColor(R.string.level1_color);
        }
        else if(m.getUserProfileWorkoutLevel().equals("2"))
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
        title.setText(m.getUserProfileWorkoutName());
        description.setText(m.getUserProfileWorkoutDescription());
    }

    @Override
    public int getCount()
    {
        return likedWorkoutsProfile.size();
    }

    @Override
    public Object getItem(int location)
    {
        return likedWorkoutsProfile.get(location);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    private void getLikes(String id)
    {

    }
}