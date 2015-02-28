package finalproject.com.getfit;

/**
 * Created by Gurumukh on 2/7/15.
 */
import android.app.Activity;
import android.graphics.Movie;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BaseWorkoutAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<BaseWorkout> workoutItems;

    public BaseWorkoutAdapter(Context context, List<BaseWorkout> workoutItems) {
        inflater = LayoutInflater.from(context);
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


        // getting movie data for the row
        BaseWorkout m = workoutItems.get(position);

        // thumbnail image
        if(m.getWorkoutLevel().equals("1")){
            thumbNail.setImageResource(R.drawable.ic_level1);
        }
        else if(m.getWorkoutLevel().equals("2")){
            thumbNail.setImageResource(R.drawable.ic_level2);

        }
        else {
            thumbNail.setImageResource(R.drawable.ic_level3);
        }

        // title
        title.setText(m.getWorkoutName());

        return convertView;
    }

}
