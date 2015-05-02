package com.gym8.main;

import android.app.Application;
import com.parse.Parse;

/**
 * Created by Ishu on 5/1/2015.
 */
public class Gym8Application extends Application {

    @Override
    public void onCreate() {
        //Connect to Parse Datastore
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, getResources().getString(R.string.parse_app_id), getResources().getString(R.string.parse_client_key));
    }
}
