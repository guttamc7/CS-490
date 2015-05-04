package com.gym8.createworkout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseObject;

import java.util.ArrayList;

import com.gym8.main.R;
import com.gym8.imageloader.ImageLoader;

/**
 * Created by Gurumukh on 4/3/15.
 */
public class CreateWorkoutExerciseAdapter extends BaseAdapter {

    private ArrayList<ParseObject> listData;
    private ImageLoader imageLoader;
    private LayoutInflater layoutInflater;

    public CreateWorkoutExerciseAdapter(Context context, ArrayList<ParseObject> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(context);
        imageLoader = new ImageLoader(context, 100);
    }

    @Override
    public int getCount() {
        return listData.size();
    }


    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder1 holder;
        ParseObject exercise = listData.get(position);
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
        //Set all the values in the list
        holder.nameView.setText(exercise.getString("name"));
        holder.setsView.setText(Integer.toString(exercise.getInt("sets")) + " sets");
        holder.repsView.setText(Integer.toString(exercise.getInt("reps")) + " reps");
        imageLoader.DisplayImage(exercise.getString("maleImg1"), holder.exerciseImage1);
        imageLoader.DisplayImage(exercise.getString("maleImg2"), holder.exerciseImage2);
        return convertView;
    }

    static class ViewHolder1 {
        TextView nameView;
        TextView setsView;
        TextView repsView;
        ImageView exerciseImage1;
        ImageView exerciseImage2;
    }
}
