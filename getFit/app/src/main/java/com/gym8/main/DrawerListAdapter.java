package com.gym8.main;

/**
 * Created by Gurumukh on 2/4/15.
 */
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DrawerListAdapter extends ArrayAdapter<String>{
    private final Activity context;
    private final String[] web;
    private final Integer[] imageId;
    private final Integer[] colorId;
    public DrawerListAdapter(Activity context,
                      String[] web, Integer[] imageId , Integer[] colorId) {
        super(context, R.layout.drawer_list_item, web);
        this.context = context;
        this.web = web;
        this.imageId = imageId;
        this.colorId = colorId;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.drawer_list_item, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.drawer_txt);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.drawer_img);
        View rowColor = (View) rowView.findViewById(R.id.color_view);
        txtTitle.setText(web[position]);
        imageView.setImageResource(imageId[position]);
        rowColor.setBackgroundResource(colorId[position]);
        return rowView;
    }
}
