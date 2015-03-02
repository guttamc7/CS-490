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

import java.util.List;

import android.content.Context;

import finalproject.com.getfit.R;
import finalproject.com.getfit.baseworkout.BaseWorkout;

public class BaseWorkoutAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<BaseWorkout> workoutItems;
    private Context context;
    public BaseWorkoutAdapter(Context context, List<BaseWorkout> workoutItems) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.workoutItems = workoutItems;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        if (convertView == null)
            convertView = inflater.inflate(R.layout.baseworkout_row, null);
       ImageView thumbNail = (ImageView) convertView
                .findViewById(R.id.thumbnail);
        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView description = (TextView)convertView.findViewById(R.id.description);
        // getting movie data for the row

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

        return convertView;
    }

}
