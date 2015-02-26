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




public class WelcomeNewActivity extends Activity {

    public static Button signInButton;
    public static Button signUpButton;
    public static Button facebookButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_after);
        final Animation animAlpha = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);
        signInButton = (Button) findViewById(R.id.signInButton);
        signUpButton = (Button) findViewById(R.id.signUpButton);
        facebookButton = (Button) findViewById(R.id.facebookButton);
        signInButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                v.startAnimation(animAlpha);
                Intent i = new Intent(WelcomeNewActivity.this, HomePageActivity.class);
                startActivity(i);
                finish();
                //TODO

            }
        });
        signUpButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                v.startAnimation(animAlpha);
                Intent i = new Intent(WelcomeNewActivity.this, RegisterActivity.class);
                startActivity(i);
                finish();


            }
        });

    }




    }


