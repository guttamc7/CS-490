package com.gym8.messages;

import android.support.v4.app.FragmentTransaction;

import com.gym8.main.R;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ishu on 4/28/2015.
 */

public class ChatMessaging {
    public static List<ParseUser> chatUsersDetails = new ArrayList<ParseUser>();
    public static List<ParseObject> chatUsers= new ArrayList<ParseObject>();

     static void saveReceivedMessage(JSONObject receivedMessage){
        try{
            System.out.println("In savereceived message");

            final String senderId = receivedMessage.getString("senderId");
            final ParseObject messageObject = new ParseObject("ChatMessages");
            messageObject.put("message", receivedMessage.getString("message"));
            messageObject.put("type", "received");
            messageObject.saveEventually();
            messageObject.pinInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        createUser(senderId);
                        ParseQuery<ParseObject> query = ParseQuery.getQuery("ChatUsers");
                        query.fromLocalDatastore();
                        query.whereEqualTo("userId", senderId);
                        query.findInBackground(new FindCallback<ParseObject>() {
                            public void done(List<ParseObject> chatUsers,
                                             ParseException e) {
                                if (e == null) {
                                    ParseRelation<ParseObject> messages = chatUsers.get(0).getRelation("messages");
                                    messages.add(messageObject);
                                    chatUsers.get(0).pinInBackground();
                                    chatUsers.get(0).saveEventually();
                                } else {
                                    e.printStackTrace();
                                }
                            }
                        });
                    } else {
                        e.printStackTrace();
                    }
                }
            });

        }catch (JSONException e) {
            e.printStackTrace();
        }
    }

    static void sendMessage(ParseUser receiverUser, String message){
        // Create our Installation query
        ParseQuery pushQuery = ParseInstallation.getQuery();
        pushQuery.whereEqualTo("user", receiverUser);

        // Send push notification to query
        ParsePush push = new ParsePush();
        push.setQuery(pushQuery); // Set our Installation query
        JSONObject messageData = new JSONObject();
        try {
            messageData.put("message",message);
            messageData.put("senderId",ParseUser.getCurrentUser().getObjectId());
            push.setData(messageData);
            push.sendInBackground();
            saveSentMessage(receiverUser,message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    static void saveSentMessage(final ParseUser receiverUser, String msg){
        final ParseObject message = new ParseObject("ChatMessages");
        message.put("message", msg);
        message.put("type","sent");
        message.saveEventually();
        message.pinInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    for (int n = 0; n < ChatMessaging.chatUsers.size(); n++) {
                        if (chatUsers.get(n).getString("userId").equals(receiverUser.getObjectId())) {
                            chatUsers.get(n).getRelation("messages").add(message);
                        }
                    }
                } else {
                    e.printStackTrace();
                }
            }
        });
    }

    static ParseObject getChatUser(ParseUser chatUser) {
        for (int n = 0; n < chatUsers.size(); n++) {
            if (chatUsers.get(n).getParseUser("userId").getObjectId().equals(chatUser.getObjectId())) {
                return ChatMessaging.chatUsers.get(n);
            }
        }
        return null;
    }

    private static void createUser(final String userId){
        ParseQuery<ParseUser> query = ParseQuery.getQuery("_User");
        query.fromLocalDatastore();
        query.getInBackground(userId, new GetCallback<ParseUser>() {
            public void done(ParseUser user, ParseException e) {
                if (e != null) {
                    ParseQuery<ParseUser> query = ParseQuery.getQuery("_User");
                    query.getInBackground(userId, new GetCallback<ParseUser>() {
                        public void done(ParseUser user, ParseException e) {
                            if (e != null) {
                                ChatMessaging.saveUserLocally(user);
                            }
                        }
                    });
                }
            }
        });
    }

    public static void saveUserLocally(ParseUser user){
        user.pinInBackground();
        ParseObject chatUser = new ParseObject("ChatUsers");
        chatUser.put("userId", user);
        chatUser.pinInBackground();
        chatUser.saveEventually();
    }

    public static List<ParseObject> getChatUsers(){
        return ChatMessaging.chatUsers;
    }

    static void setChatUsers(List<ParseObject> chatUsers){
        if(chatUsers!=null) {
            ChatMessaging.chatUsers = chatUsers;
        }
    }

    public static List<ParseUser> getChatUsersDetails(){
        return ChatMessaging.chatUsersDetails;
    }

    static void setChatUsersDetails(List<ParseUser> chatUsersDetails){
        if(chatUsersDetails!=null) {
            ChatMessaging.chatUsersDetails = chatUsersDetails;
        }
    }


}