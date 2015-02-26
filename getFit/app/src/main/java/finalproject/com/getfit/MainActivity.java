package finalproject.com.getfit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import com.parse.Parse;

/**
 * Created by Gurumukh on 2/5/15.
 */
public class MainActivity extends Activity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Connect to Parse Datastore
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "jlPrAU7g3Mx6bH2cFMiEzNlgNSaJ44JmwNdRk2pX", "JDIEhCILA6iSVsRCEPth8LKcMugkQ2I4sPGZti9s");

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                //WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
    /* adapt the image to the size of the display */


    /* fill the background ImageView with the resized image */


        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(MainActivity.this, HomePageActivity.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

}


