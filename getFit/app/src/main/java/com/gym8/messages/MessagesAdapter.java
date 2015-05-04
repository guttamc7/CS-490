package com.gym8.messages;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.gym8.main.R;
import com.gym8.imageloader.ImageLoader;
import com.parse.ParseFile;
import com.parse.ParseUser;

/**
 * Created by Jai Nalwa on 4/10/15.
 */

public class MessagesAdapter extends BaseSwipeAdapter {
    private LayoutInflater inflater;
    private Context context;
    private SwipeLayout swipeLayout;
    private TextView name;
    private ImageView profilePic;
    private ImageLoader imageLoader;

    public MessagesAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        imageLoader = new ImageLoader(context,64);
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe_messages_row;
    }

    @Override
    public View generateView(final int position, ViewGroup parent) {
        View v = LayoutInflater.from(context).inflate(R.layout.messages_row, null);

        swipeLayout = (SwipeLayout)v.findViewById(getSwipeLayoutResourceId(position));
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

        ParseUser user = ChatMessaging.getChatUsersDetails().get(position);
        name.setText(user.getString("name"));
        ParseFile imageFile = user.getParseFile("profilePic");
        if(imageFile == null) {
            profilePic.setImageResource(R.drawable.no_user_logo);
        }
        else {
            imageLoader.DisplayImage(user.getParseFile("profilePic").getUrl(), profilePic);
        }
    }

    @Override
    public int getCount() {
        return ChatMessaging.getChatUsersDetails().size();
    }

    @Override
    public Object getItem(int location) {
        return ChatMessaging.getChatUsersDetails().get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
