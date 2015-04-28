package com.gym8.messages;

import com.parse.FindCallback;
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

import java.util.List;

/**
 * Created by Ishu on 4/28/2015.
 */
public class ChatMessaging {
    private static String senderId;
    private static List<ParseUser> chatUsersDetails;
    private static List<ParseObject> chatUsers;

     static void saveReceivedMessage(JSONObject receivedMessage){

        try{
            System.out.println("In savereceived message");
            ChatMessaging.senderId = receivedMessage.getString("senderId");

            final ParseObject message = new ParseObject("ChatMessages");
            message.put("message", receivedMessage.getString("message"));
            message.put("type","received");
            message.saveEventually();
            message.pinInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        System.out.println("In  message saved");
                        ParseQuery<ParseObject> query = ParseQuery.getQuery("ChatUsers");
                        query.whereEqualTo("userId", senderId);
                        query.fromLocalDatastore();

                        query.findInBackground(new FindCallback<ParseObject>() {
                            public void done(List<ParseObject> chatUsers,
                                             ParseException e) {
                                if (e == null) {
                                    ParseObject chatUser;
                                    if(chatUsers.size()==0){
                                        chatUser = createChatUser(senderId);
                                    }
                                    else{
                                        chatUser = chatUsers.get(0);
                                    }

                                    ParseRelation<ParseObject> messages = chatUser.getRelation("messages");
                                    messages.add(message);
                                    chatUser.pinInBackground();
                                    chatUser.saveEventually();
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
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void sendMessage(ParseUser receiverUser, String message){
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

    public static void saveSentMessage(final ParseUser receiverUser, String msg){
        final ParseObject message = new ParseObject("ChatMessages");
        message.put("message", msg);
        message.put("type","sent");
        message.saveEventually();
        message.pinInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    ParseQuery<ParseObject> query = ParseQuery.getQuery("ChatUsers");
                    query.whereEqualTo("userId", receiverUser.getObjectId());
                    query.fromLocalDatastore();
                    query.findInBackground(new FindCallback<ParseObject>() {
                        public void done(List<ParseObject> chatUsers,
                                         ParseException e) {
                            if (e == null) {
                                ParseObject chatUser;
                                if(chatUsers.size()==0){
                                    chatUser = createChatUser(receiverUser.getObjectId());
                                }
                                else{
                                    chatUser = chatUsers.get(0);
                                }

                                ParseRelation<ParseObject> messages = chatUser.getRelation("messages");
                                messages.add(message);
                                chatUser.pinInBackground();
                                chatUser.saveEventually();
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
    }

    public static void retrieveChatUsers(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("ChatUsers");
        query.fromLocalDatastore();
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> chatUsers,
                             ParseException e) {
                if (e == null) {
                    for(int n=0; n< chatUsers.size();n++){
                        ChatMessaging.chatUsersDetails.add(chatUsers.get(n).getParseUser("userId"));
                    }
                    ChatMessaging.chatUsers.addAll(chatUsers);

                } else {
                    e.printStackTrace();
                }
            }
        });
    }

    public static List<ParseUser> getChatUsersDetails(){
        return ChatMessaging.chatUsersDetails;
    }

    static List<ParseObject> getMessagesForChatUser(ParseUser chatUser) {
        for (int n = 0; n < chatUsers.size(); n++) {
            if (chatUsers.get(n).getParseUser("userId").getObjectId() == chatUser.getObjectId()) {

            }
        }
        return null;
    }

    private static ParseObject createChatUser(String senderId){
        ParseObject chatUser = new ParseObject("ChatUsers");
        ParseUser senderUser = (ParseUser) ParseUser.createWithoutData("_User",senderId);
        chatUser.put("userId", senderUser);
        chatUser.pinInBackground();
        chatUser.saveEventually();
        return chatUser;
    }


}
