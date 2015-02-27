package finalproject.com.getfit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import com.parse.Parse;
import com.parse.ui.ParseLoginBuilder;

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
        Parse.initialize(this,getResources().getString(R.string.parse_app_id), getResources().getString(R.string.parse_client_key));

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
                ParseLoginBuilder builder = new ParseLoginBuilder(MainActivity.this);
                startActivityForResult(builder.build(), 0);
            }

        }, SPLASH_TIME_OUT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == 0) {
            // The User had successfully logged in
            if (resultCode == RESULT_OK) {
                Intent i = new Intent(MainActivity.this, HomePageActivity.class);
                startActivity(i);
            }
        }
        // close this activity
        finish();
    }

    /*
    private void getBaseWorkOuts(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Workout");
        query.whereEqualTo("userId", getResources().getString(R.string.baseUserId));
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> workoutList, ParseException e) {
                if (e == null) {
                    //All the base workouts retrieved
                } else {
                    //Exception
                }
            }
        });
     }
     */

    /*
    private void getExercisesForWorkout(String workoutId) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("WorkoutExercises");
        query.whereEqualTo("workOutId", workoutId);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> exerciseList, ParseException e) {
                if (e == null) {
                    //All the exercises for the specified workout retrieved
                } else {
                    //Exception
                }
            }
        });
    }*/
}
