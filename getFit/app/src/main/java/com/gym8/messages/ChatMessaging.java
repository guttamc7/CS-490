package com.gym8.messages;

import android.content.Context;
import android.widget.Toast;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
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
    private static List<ParseUser> chatUsersDetails = new ArrayList<ParseUser>();
    private static List<ParseObject> chatUsers = new ArrayList<ParseObject>();
    private static boolean chatRetrieved = false;

    static void receiveMessage(final Context context, JSONObject receivedMessage) {
        try {
            final String senderId = receivedMessage.getString("senderId");
            final ParseObject messageObject = new ParseObject("ChatMessages");
            messageObject.put("message", receivedMessage.getString("message"));
            messageObject.put("type", "received");
            messageObject.saveEventually();
            messageObject.pinInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        ChatMessaging.saveReceivedUser(context, senderId, messageObject);
                    } else {
                        Toast.makeText(context, "Connection error", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (JSONException e) {
            Toast.makeText(context, "Connection error", Toast.LENGTH_SHORT).show();
        }
    }

    private static void saveReceivedUser(final Context context, final String userId, final ParseObject messageObject) {
        if (ChatMessaging.chatRetrieved == false) { //Retrieve all the Chat
            ParseQuery<ParseObject> query = ParseQuery.getQuery("ChatUsers");
            query.fromLocalDatastore();
            query.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> chatUsers,
                                 ParseException e) {
                    if (e == null) {
                        for (int n = 0; n < chatUsers.size(); n++) {
                            try {
                                chatUsers.get(n).getParseObject("userId").fetchFromLocalDatastore();
                                ChatMessaging.chatUsersDetails.add(chatUsers.get(n).getParseUser("userId"));
                            } catch (ParseException e1) {
                                Toast.makeText(context, "Connection error", Toast.LENGTH_SHORT).show();
                            }
                        }
                        ChatMessaging.chatUsers.addAll(chatUsers);
                        ChatMessaging.setChatRetrieved(true);
                        saveReceivedUserAfterChatRetrieved(context,userId,messageObject);
                    } else {
                        Toast.makeText(context, "Connection error", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            saveReceivedUserAfterChatRetrieved(context,userId,messageObject);
        }
    }

    private static void saveReceivedUserAfterChatRetrieved(final Context context, final String userId, final ParseObject messageObject){
        boolean userExists = false;
        for (int n = 0; n < ChatMessaging.chatUsers.size(); n++) {
            if (chatUsers.get(n).getParseObject("userId").getObjectId().equals(userId)) {
                chatUsers.get(n).getRelation("messages").add(messageObject);
                chatUsers.get(n).pinInBackground();
                chatUsers.get(n).saveEventually();
                userExists = true;
                break;
            }
        }
        if (userExists == false) {
            ParseQuery<ParseUser> query = ParseQuery.getQuery("_User");
            query.getInBackground(userId, new GetCallback<ParseUser>() {
                public void done(ParseUser user, ParseException e) {
                    if (e == null) {
                        user.pinInBackground();
                        ChatMessaging.chatUsersDetails.add(user);

                        final ParseObject chatUser = new ParseObject("ChatUsers");
                        chatUser.put("userId", user);
                        chatUser.saveEventually();
                        chatUser.pinInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null) {
                                    chatUser.getRelation("messages").add(messageObject);
                                    chatUser.pinInBackground();
                                    chatUser.saveEventually();
                                    ChatMessaging.chatUsers.add(chatUser);
                                }
                                else{
                                    Toast.makeText(context, "Connection error", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                    else{
                        Toast.makeText(context, "Connection error", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    static ParseObject getChatUser(ParseUser chatUser) {
        for (int n = 0; n < chatUsers.size(); n++) {
            if (chatUsers.get(n).getParseUser("userId").getObjectId().equals(chatUser.getObjectId())) {
                return ChatMessaging.chatUsers.get(n);
            }
        }
        return null;
    }

    static List<ParseObject> getChatUsers() {
        return ChatMessaging.chatUsers;
    }

    static void setChatUsers(List<ParseObject> chatUsers) {
        if (chatUsers != null) {
            ChatMessaging.chatUsers = chatUsers;
        }
    }

    static void addToChatUser(ParseObject chatUser) {
        if (chatUser != null) {
            ChatMessaging.chatUsers.add(chatUser);
        }
    }

    static List<ParseUser> getChatUsersDetails() {
        return ChatMessaging.chatUsersDetails;
    }

    static void setChatUsersDetails(List<ParseUser> chatUsersDetails) {
        if (chatUsersDetails != null) {
            ChatMessaging.chatUsersDetails = chatUsersDetails;
        }
    }

    static void addToChatUserDetails(ParseUser user) {
        if (user != null) {
            ChatMessaging.chatUsersDetails.add(user);
        }
    }

    static boolean isChatRetrieved() {
        return ChatMessaging.chatRetrieved;
    }

    static void setChatRetrieved(boolean chatRetrieved) {
        ChatMessaging.chatRetrieved = chatRetrieved;
    }
}