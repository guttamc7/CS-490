package com.gym8.messages;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gym8.imageloader.ImageLoader;
import com.gym8.main.R;
import com.parse.ParseObject;

import java.util.ArrayList;

/**
 * Created by Gurumukh on 4/13/15.
 */
public class ChatAdapter extends BaseAdapter {
    private ArrayList<ParseObject> listData;
    ImageLoader imageLoader;
    private LayoutInflater layoutInflater;

    public ChatAdapter(Context context, ArrayList<ParseObject> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(context);
        imageLoader = new ImageLoader(context,100);
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
        ParseObject message = listData.get(position);
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.chat_row, null);
            holder = new ViewHolder1();
            holder.singleMessage = (TextView) convertView.findViewById(R.id.singleMessage);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder1) convertView.getTag();
        }
        //Set all the values in the list

        //holder.singleMessage.setText(message.getString("name"));
        return convertView;
    }

    static class ViewHolder1 {
        TextView singleMessage;
    }
}

