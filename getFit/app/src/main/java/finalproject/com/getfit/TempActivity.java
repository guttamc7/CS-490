package finalproject.com.getfit;

/**
 *  The program is responsible for the user signing in, signing up and signing in with Facebook
 * @author Gurumukh Uttamchandani
 *
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import com.parse.ui.ParseLoginBuilder;

public class TempActivity extends Activity {

    public static Button signInButton;
    public static Button signUpButton;
    public static Button facebookButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Enable Local Datastore.
        /*Parse.enableLocalDatastore(this);

        Parse.initialize(this, "jlPrAU7g3Mx6bH2cFMiEzNlgNSaJ44JmwNdRk2pX", "JDIEhCILA6iSVsRCEPth8LKcMugkQ2I4sPGZti9s");

        ParseObject testObject = new ParseObject("TestObject");
        testObject.put("foo", "bar");
        testObject.saveInBackground();*/

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);
        final Animation animAlpha = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);
        signInButton = (Button) findViewById(R.id.signInButton);
        signUpButton = (Button) findViewById(R.id.signUpButton);
        facebookButton = (Button) findViewById(R.id.facebookButton);

        signInButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                v.startAnimation(animAlpha);
                ParseLoginBuilder builder = new ParseLoginBuilder(TempActivity.this);
                startActivityForResult(builder.build(), 0);
                finish();
            }
        });
        signUpButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                v.startAnimation(animAlpha);
                Intent i = new Intent(TempActivity.this, RegisterActivity.class);
                startActivity(i);
                finish();
            }
        });
    }



}


