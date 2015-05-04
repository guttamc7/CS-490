package com.gym8.messages;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.ListView;

import com.gym8.main.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Gurumukh on 4/13/15.
 */
public class ChatFragment extends Fragment {

    private ListView listView;
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
                if (!(message.getText().toString() == null || message.getText().toString().length() == 0)) { //SEND MESSAGE
                    adapter.notifyDataSetChanged();
                    sendMessage(message.getText().toString());
                    View views = getActivity().getCurrentFocus();
                    if (views != null) {
                        InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                    message.setText("");
                }
            }
        });

        return v;
    }

    public static ChatFragment newInstance() {
        return new ChatFragment();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getChatMessages();
    }

    private void messagesRetrieved(List<ParseObject> messages) {
        adapter = new ChatAdapter(getActivity().getApplicationContext(), messages);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void getChatMessages() {
        ParseObject chatUser = ChatMessaging.getChatUser(MessagesFragment.getSelectedUser());
        ParseRelation<ParseObject> relation = chatUser.getRelation("messages");
        ParseQuery<ParseObject> query = relation.getQuery();
        query.addDescendingOrder("createdAt");
        query.fromLocalDatastore();
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> messages, ParseException e) {
                if (e == null) {
                    messagesRetrieved(messages);
                } else {
                    Toast.makeText(getActivity(), "Connection error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendMessage(String message) {
        // Create our Installation query
        ParseQuery pushQuery = ParseInstallation.getQuery();
        pushQuery.whereEqualTo("user", MessagesFragment.getSelectedUser());

        // Send push notification to query
        ParsePush push = new ParsePush();
        push.setQuery(pushQuery); // Set our Installation query
        try {
            JSONObject messageData = new JSONObject();
            messageData.put("message", message);
            messageData.put("senderId", ParseUser.getCurrentUser().getObjectId());
            messageData.put("senderName", ParseUser.getCurrentUser().getString("name"));
            push.setData(messageData);
            push.sendInBackground();
            saveSentMessage(message);

        } catch (JSONException e) {
            Toast.makeText(getActivity(), "Connection error", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveSentMessage(String message) {
        final ParseObject chatMessage = new ParseObject("ChatMessages");
        chatMessage.put("message", message);
        chatMessage.put("type", "sent");
        chatMessage.saveEventually();
        chatMessage.pinInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    List<ParseObject> chatMessages = adapter.getChatMessages();
                    chatMessages.add(chatMessage);
                    messagesRetrieved(chatMessages);

                    List<ParseObject> chatUsers = ChatMessaging.getChatUsers();
                    for (int n = 0; n < chatUsers.size(); n++) {
                        if (chatUsers.get(n).getParseObject("userId").getObjectId().equals(MessagesFragment.getSelectedUser().getObjectId())) {
                            chatUsers.get(n).getRelation("messages").add(chatMessage);
                            chatUsers.get(n).pinInBackground();
                            chatUsers.get(n).saveEventually();
                            break;
                        }
                    }
                } else {
                    Toast.makeText(getActivity(), "Connection error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
