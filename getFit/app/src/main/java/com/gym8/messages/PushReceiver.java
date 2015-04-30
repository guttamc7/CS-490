package com.gym8.messages;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

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
    private static final String TAG = "PushReceiver";
    private String senderId;

    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("In message receive");
        try {
            ChatMessaging.receiveMessage(new JSONObject(intent.getExtras().getString("com.parse.Data")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onPushOpen(Context context, Intent intent) {
        System.out.println("IN on Push Click");
    }

    private void generateNotificaiton(){

    }
}