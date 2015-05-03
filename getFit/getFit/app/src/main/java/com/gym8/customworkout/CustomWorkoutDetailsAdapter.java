package com.gym8.customworkout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.parse.ParseException;
import com.parse.GetCallback;
import com.parse.ParseObject;
import java.util.ArrayList;
import java.util.List;
import com.gym8.main.R;
import com.gym8.imageloader.ImageLoader;

/**
 * Created by Gurumukh on 4/3/15.
 */
public class CustomWorkoutDetailsAdapter extends BaseAdapter {

    private List<ParseObject> exercises;
    private ImageLoader imageLoader;
    private LayoutInflater layoutInflater;

    public CustomWorkoutDetailsAdapter(Context context, List<ParseObject> exercises) {
        this.layoutInflater = LayoutInflater.from(context);
        this.imageLoader = new ImageLoader(context, 100);
        this.exercises = new ArrayList<ParseObject>();
        this.exercises.addAll(exercises);
    }

    @Override
    public int getCount() {
        return exercises.size();
    }


    @Override
    public Object getItem(int position) {
        return exercises.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder1 holder;
        final ParseObject workoutExercise = exercises.get(position);

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.create_workout_exercise_row, null);
            holder = new ViewHolder1();
            holder.nameView = (TextView) convertView.findViewById(R.id.workout_exercise_name);
            holder.repsView = (TextView) convertView.findViewById(R.id.workout_exercise_rep);
            holder.setsView = (TextView) convertView.findViewById(R.id.workout_exercise_set);
            holder.exerciseImage1 = (ImageView) convertView.findViewById(R.id.exercise_workout_img1);
            holder.exerciseImage2 = (ImageView) convertView.findViewById(R.id.exercise_workout_img2);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder1) convertView.getTag();
        }

        workoutExercise.getParseObject("exercise").fetchIfNeededInBackground(new GetCallback<ParseObject>() {

            public void done(ParseObject exercisename, ParseException e) {
                setHolder(holder, exercisename.getString("name"), exercisename.getString("maleImg1"), exercisename.getString("maleImg2"), workoutExercise.getInt("sets"),workoutExercise.getInt("reps"));

            }
        });

        return convertView;
    }

    private void setHolder(ViewHolder1 holder, String exerciseName, String maleImg1, String maleImg2, int sets, int reps) {
        holder.nameView.setText(exerciseName);
        holder.setsView.setText(Integer.toString(sets) + " sets");
        holder.repsView.setText(Integer.toString(reps) + " reps");
        imageLoader.DisplayImage(maleImg1, holder.exerciseImage1);
        imageLoader.DisplayImage(maleImg2, holder.exerciseImage2);

    }

    static class ViewHolder1 {
        TextView nameView;
        TextView setsView;
        TextView repsView;
        ImageView exerciseImage1;
        ImageView exerciseImage2;
    }
}
