package finalproject.com.getfit;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * Created by rishabhmittal on 2/8/15.
 */
public class RegisterActivity extends Activity
{
    private static String mEmail;
    private static String mPass;
    private static String mName;
    private EditText fullName, emailAddress,password;
    private Button registerUser;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_account);
        final Animation animAlpha = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);
        fullName = (EditText) findViewById(R.id.txtFullName);
        emailAddress = (EditText) findViewById(R.id.txtEmail);
        password = (EditText) findViewById(R.id.register_pass);
        mEmail = emailAddress.getText().toString();
        mPass = password.getText().toString();
        mName = fullName.getText().toString();
        registerUser = (Button) findViewById(R.id.btnRegisterUser);
        registerUser.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                view.startAnimation(animAlpha);
                Intent i = new Intent(RegisterActivity.this, NewProfileActivity.class);
                startActivity(i);
                finish();
                //TODO
            }
        });
    }
}
