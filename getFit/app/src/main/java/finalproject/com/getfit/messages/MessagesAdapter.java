package finalproject.com.getfit.messages;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.parse.ParseObject;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.util.Calendar;
import java.util.List;

import finalproject.com.getfit.R;
import finalproject.com.getfit.imageloader.ImageLoader;

/**
 * Created by Jai Nalwa on 4/10/15.
 */
public class MessagesAdapter extends BaseSwipeAdapter {
    private LayoutInflater inflater;
    private List<ParseObject> messageItems;
    private Context context;
    private SwipeLayout swipeLayout;
    private TextView name;
    private ImageView profilePic;
    ImageLoader imageLoader;
    public MessagesAdapter(Context context, List<ParseObject> workoutItems) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.messageItems = workoutItems;
        imageLoader = new ImageLoader(context,64);
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    @Override
    public View generateView(final int position, ViewGroup parent) {
        View v = LayoutInflater.from(context).inflate(R.layout.messages_row, null);

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
        ImageView profilePic = (ImageView) convertView
                .findViewById(R.id.profilePic_messages_row);
        name = (TextView) convertView.findViewById(R.id.username_messages_row);

        ParseObject message = messageItems.get(position);
        // title
        //TODO
        name.setText(message.getString("name"));
        imageLoader.DisplayImage(message.getParseFile("profilePic").getUrl(),profilePic);
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
