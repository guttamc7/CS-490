package com.gym8.messages;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.parse.ParseObject;

import java.util.List;

import com.gym8.main.R;
import com.gym8.imageloader.ImageLoader;
import com.parse.ParseUser;

/**
 * Created by Jai Nalwa on 4/10/15.
 */
public class MessagesAdapter extends BaseSwipeAdapter {
    private LayoutInflater inflater;
    private List<ParseUser> messageItems;
    private Context context;
    private SwipeLayout swipeLayout;
    private TextView name;
    private ImageView profilePic;
    ImageLoader imageLoader;
    public MessagesAdapter(Context context, List<ParseUser> workoutItems) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.messageItems = workoutItems;
        imageLoader = new ImageLoader(context,64);
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe_messages_row;
    }

    @Override
    public View generateView(final int position, ViewGroup parent) {
        View v = inflater.inflate(R.layout.messages_row, null);

        swipeLayout = (SwipeLayout)v.findViewById(getSwipeLayoutResourceId(position));

        swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {


            }
        });
        swipeLayout.setOnDoubleClickListener(new SwipeLayout.DoubleClickListener() {
            @Override
            public void onDoubleClick(SwipeLayout layout, boolean surface) {
                Toast.makeText(context, "DoubleClick", Toast.LENGTH_SHORT).show();
            }
        });

        swipeLayout.findViewById(R.id.delete_imview_messages_row).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();

            }
        });
        return v;
    }

    @Override
    public void fillValues(int position, View convertView) {
        profilePic = (ImageView) convertView
                .findViewById(R.id.profilePic_messages_row);
        name = (TextView) convertView.findViewById(R.id.username_messages_row);

        ParseUser user = messageItems.get(position);
        // title
        //TODO
        name.setText(user.getString("name"));
        imageLoader.DisplayImage(user.getParseFile("profilePic").getUrl(),profilePic);
    }

    @Override
    public int getCount() {
        return messageItems.size();
    }

    @Override
    public Object getItem(int location) {
        return messageItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
