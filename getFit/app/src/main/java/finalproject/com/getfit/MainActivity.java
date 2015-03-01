package finalproject.com.getfit;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;

import com.parse.Parse;
import com.parse.ParseUser;
import com.parse.ui.ParseLoginBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

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
        Parse.initialize(this, getResources().getString(R.string.parse_app_id), getResources().getString(R.string.parse_client_key));


        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        //WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        /* adapt the image to the size of the display */
        /* fill the background ImageView with the resized image */

        //Get data from text files

        ArrayList<String> arraylistExercise = new ArrayList<String>();
        ArrayList<String> arraylistMuscleArea = new ArrayList<String>();
        ArrayList<String> arraylistMaleImages = new ArrayList<String>();
        ArrayList<String> arraylistFemaleImages = new ArrayList<String>();
        ArrayList<String> arraylistAllImageLinks = new ArrayList<String>();

        int counter=0;
       /* try
        {
            AssetManager am=getAssets();
            InputStream inputStreamForExercises = am.open("exercise.txt");
            InputStream inputStreamForImages = am.open("imageLinks.txt");
            if (inputStreamForExercises != null && inputStreamForImages != null)
            {
                InputStreamReader inputStreamReaderForExercises = new InputStreamReader(inputStreamForExercises);
                InputStreamReader inputStreamReaderForImages = new InputStreamReader(inputStreamForImages);
                BufferedReader br = new BufferedReader(inputStreamReaderForExercises);
                BufferedReader br1 = new BufferedReader(inputStreamReaderForImages);
                try
                {
                    String line = br.readLine();
                    while (line != null)
                    {
                        if (counter % 2 == 0)
                        {
                            arraylistExercise.add(line);
                        } else
                        {
                            arraylistMuscleArea.add(line);
                        }
                        line = br.readLine();
                        counter++;
                    }

                }finally{}
                try
                {
                    String line = br1.readLine();
                    while (line != null)
                    {
                        arraylistAllImageLinks.add(line);
                        line = br1.readLine();
                    }
                } finally{ }
                for (int i = 0; i <= 3503; i = i + 4)
                {
                    arraylistMaleImages.add(arraylistAllImageLinks.get(i));
                    arraylistMaleImages.add(arraylistAllImageLinks.get(i + 1));
                    arraylistFemaleImages.add(arraylistAllImageLinks.get(i + 2));
                    arraylistFemaleImages.add(arraylistAllImageLinks.get(i + 3));
                }
                br1.close();
                br.close();
                inputStreamForExercises.close();
                inputStreamForImages.close();

            }

        }

        catch(IOException e)
        {
            Log.e("login activity", "File not found: " + e.toString());
        }*/
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
                int loginCount = incrementUserLoginCount();
                    if(loginCount == 1) { //User Logging in for the first time
                        Intent i = new Intent(MainActivity.this, NewProfileActivity.class);
                        startActivity(i);
                    }else{
                        Intent i = new Intent(MainActivity.this, HomePageActivity.class);
                        startActivity(i);
                    }
            }
        }
        // close this activity
        finish();
    }

    //Increment the user login count and return the latest value
    private int incrementUserLoginCount() {
        ParseUser currentUser = ParseUser.getCurrentUser();
        int currentLoginCount = currentUser.getInt("loginCount");
        currentUser.put("loginCount",++currentLoginCount);
        currentUser.saveInBackground();
        return currentLoginCount;
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