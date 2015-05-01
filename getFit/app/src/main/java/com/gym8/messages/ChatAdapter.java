package com.gym8.messages;

        import android.content.Context;
        import android.view.Gravity;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.TextView;

        import com.gym8.imageloader.ImageLoader;
        import com.gym8.main.R;
        import com.parse.ParseObject;

        import java.util.ArrayList;
        import java.util.List;

/**
 * Created by Gurumukh on 4/13/15.
 */
public class ChatAdapter extends BaseAdapter {
    private List<ParseObject> listData;
    ImageLoader imageLoader;
    private LayoutInflater layoutInflater;

    public ChatAdapter(Context context, List<ParseObject> listData) {
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
            holder.wrapper = (LinearLayout) convertView.findViewById(R.id.singleMessageContainer);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder1) convertView.getTag();
        }
        System.out.println("In getView");
        //Set all the values in the list
        if(message.getString("type").equals("received")) {
            holder.singleMessage.setBackgroundResource(R.drawable.speech_bubble_yellow);
            holder.wrapper.setGravity(Gravity.LEFT);
        }
        else {
            holder.singleMessage.setBackgroundResource(R.drawable.speech_bubble_red);
            holder.wrapper.setGravity(Gravity.RIGHT);
        }


        holder.singleMessage.setText(message.getString("message"));
        return convertView;
    }

    static class ViewHolder1 {
        TextView singleMessage;
        LinearLayout wrapper;
    }
}