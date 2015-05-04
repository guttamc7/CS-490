package com.gym8.messages;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.gym8.main.HomePageActivity;
import com.gym8.main.MainActivity;
import com.gym8.main.R;
import com.parse.ParsePushBroadcastReceiver;
import com.parse.ParseUser;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ishu on 4/22/2015.
 */

public class PushReceiver extends ParsePushBroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            JSONObject message = new JSONObject(intent.getExtras().getString("com.parse.Data"));
            ChatMessaging.receiveMessage(context, message);
            sendNotification(context, intent, message.getString("senderName"), message.getString("message"));
        } catch (JSONException e) {
            Toast.makeText(context, "Connection error", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendNotification(Context context, Intent intent, String senderName, String message) {
        String notificationService = context.NOTIFICATION_SERVICE;
        NotificationCompat.Builder n = new NotificationCompat.Builder(context);

        n.setContentTitle(senderName);
        n.setContentText(message);
        n.setSmallIcon(R.drawable.ic_launcher);

        Notification notification = n.build();
        notification.sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Intent notificationIntent = new Intent(context, HomePageActivity.class);

        if (ParseUser.getCurrentUser() == null) {
            notificationIntent = new Intent(context, MainActivity.class);
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
        notification.contentIntent = pendingIntent;

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(notificationService);
        notificationManager.notify(8, notification);
    }
}