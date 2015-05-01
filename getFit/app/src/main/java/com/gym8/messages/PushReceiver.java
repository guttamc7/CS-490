package com.gym8.messages;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.gym8.main.HomePageActivity;
import com.gym8.main.MainActivity;
import com.gym8.main.R;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParsePushBroadcastReceiver;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ishu on 4/22/2015.
 */
public class PushReceiver extends ParsePushBroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            JSONObject message = new JSONObject(intent.getExtras().getString("com.parse.Data"));
            ChatMessaging.receiveMessage(message);
            sendNotification(context,intent, message.getString("senderName"), message.getString("message"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void sendNotification(Context context, Intent intent,String senderName, String message){
        String notificationService = context.NOTIFICATION_SERVICE;
        NotificationCompat.Builder n = new NotificationCompat.Builder(context);

        n.setContentTitle(senderName);
        n.setContentText(message);
        n.setSmallIcon(R.drawable.ic_launcher);

        Notification notification = n.build();
        notification.sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Intent notificationIntent = new Intent(context, HomePageActivity.class);
        if(ParseUser.getCurrentUser()==null){
            notificationIntent = new Intent(context, MainActivity.class);
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
        notification.contentIntent = pendingIntent;

        NotificationManager notificationManager =(NotificationManager) context.getSystemService(notificationService);
        notificationManager.notify(8, notification);
    }
}