package com.gym8.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Window;

import com.gym8.ErrorHandlingAlertDialogBox;
import com.gym8.main.R;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.ui.ParseLoginBuilder;
/**
 * Created by Gurumukh on 2/5/15.
 */

public class MainActivity extends Activity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;
    private Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        //WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        /* adapt the image to the size of the display */
        /* fill the background ImageView with the resized image */

        thread = new Thread() {
            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                synchronized (this) {
                    try {
                        wait(SPLASH_TIME_OUT);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                    ParseLoginBuilder builder = new ParseLoginBuilder(MainActivity.this);
                    startActivityForResult(builder.build(), 0);
                }
            };
        thread.start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == 0) {
            // The User had successfully logged in
            if (resultCode == RESULT_OK) {
                int loginCount = incrementUserLoginCount();
                //Register Push Notification and Associate user with Push notification
                ParseInstallation.getCurrentInstallation().saveInBackground();
                ParseInstallation installation = ParseInstallation.getCurrentInstallation();
                installation.put("user",ParseUser.getCurrentUser());
                installation.saveInBackground();

                    if(loginCount == 1) { //User Logging in for the first time
                        Intent i = new Intent(MainActivity.this, NewProfileActivity.class);
                        startActivity(i);
                    }else{
                        Intent i = new Intent(MainActivity.this, HomePageActivity.class);
                        startActivity(i);
                    }
            }
            else
                if(resultCode == RESULT_CANCELED) {
                    // close the activity and the app
                    finish();
                    System.exit(0);
                }
        }
        // close this activity
        //finish();
    }



    /**
     * Processes splash screen touch events
     */
    @Override
    public boolean onTouchEvent(MotionEvent evt) {
        if (evt.getAction() == MotionEvent.ACTION_DOWN || evt.getAction() == MotionEvent.ACTION_MOVE) {
            synchronized(thread){
                thread.notifyAll();
            }

        }
        return true;
    }


    //Increment the user login count and return the latest value
    private int incrementUserLoginCount() {
        ParseUser currentUser = ParseUser.getCurrentUser();
        int currentLoginCount = currentUser.getInt("loginCount");
        currentUser.increment("loginCount");
        currentUser.saveInBackground();
        return ++currentLoginCount;
    }

}