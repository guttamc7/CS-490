package com.gym8.messages;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import com.gym8.main.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;

import android.widget.EditText;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gurumukh on 4/13/15.
 */
public class ChatFragment extends Fragment {

    private ListView listView;
    private ArrayList<ParseObject> chatList = new ArrayList<>();
    private View v;
    private ChatAdapter adapter;
    private EditText message;
    private ImageButton sendButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_chat, container, false);
        listView = (ListView) v.findViewById(R.id.chat_list);
        message = (EditText) v.findViewById(R.id.message_edit);
        sendButton = (ImageButton) v.findViewById(R.id.send_message_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(message.getText().toString() == null || message.getText().toString().length() == 0) {


                }
                else {
                    //SEND MESSAGE
                    String messageText = message.getText().toString();
                    ChatMessaging.sendMessage(MessagesFragment.selectedUser,messageText);
                    adapter.notifyDataSetChanged();

                    //adapter = new ChatAdapter(getActivity().getApplicationContext(), chatList);
                    //listView.setAdapter(adapter);
                }
            }
        });

        return v;
    }

    public ChatFragment() {
        // Auto-generated constructor stub
    }

    public static ChatFragment newInstance() {
        return new ChatFragment();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getChatMessages();
    }

    public void getChatMessages(){
        ParseObject chatUser = ChatMessaging.getChatUser(MessagesFragment.selectedUser);

        ParseRelation<ParseObject> relation = chatUser.getRelation("messages");
        ParseQuery<ParseObject> query = relation.getQuery();
        query.fromLocalDatastore();
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> messages, ParseException e) {
                if (e == null) {
                        adapter = new ChatAdapter(getActivity().getApplicationContext(), messages);
                        listView.setAdapter(adapter);
                } else {
                    e.printStackTrace();
                }

            }
        });


    }

}
