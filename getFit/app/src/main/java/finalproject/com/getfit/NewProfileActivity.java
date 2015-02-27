package finalproject.com.getfit;

/**
 * Created by Gurumukh on 2/8/15.
 */

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * Created by rishabhmittal on 2/8/15.
 */
public class NewProfileActivity extends Activity implements View.OnClickListener {
    private RadioGroup gender;
    private RadioButton male, female;
    private EditText fullName, emailAddress, birthDate, weight, height;
    private TextView profilePicText;
    private ImageView profilePictureImgView;
    Uri imageUri = Uri.parse("android.resource://org.intracode.contactmanager/drawable/no_user_logo.png");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newprofile);
        gender = (RadioGroup) findViewById(R.id.radioButtonGender);
        male = (RadioButton) findViewById(R.id.radioButtonMale);
        female = (RadioButton) findViewById(R.id.radioButtonFemale);
        profilePicText = (TextView) findViewById(R.id.profile_photo_text);
        birthDate = (EditText) findViewById(R.id.txtEmail);
        weight = (EditText) findViewById(R.id.txtWeight);
        height = (EditText) findViewById(R.id.txtHeight);
        profilePictureImgView = (ImageView) findViewById(R.id.imgViewProfilePic);

        final Button registerUser = (Button) findViewById(R.id.doneButton);
        registerUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(NewProfileActivity.this, HomePageActivity.class);
                startActivity(i);
                finish();
                //TODO
            }
        });

        height.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                registerUser.setEnabled(String.valueOf(height.getText()).trim().length() > 0);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        profilePictureImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Profile Picture"), 1);
            }
        });

        male.setOnClickListener(this);
        female.setOnClickListener(this);
    }

    public void onActivityResult(int reqCode, int resCode, Intent data) {
        if (resCode == RESULT_OK) {
            if (reqCode == 1) {
                imageUri = data.getData();
                profilePictureImgView.setImageURI(data.getData());
                profilePicText.setText("");
            }
        }
    }

    @Override
    public void onClick(View v) {
        int checkedRadioButtonId = gender.getCheckedRadioButtonId();

        switch (checkedRadioButtonId) {
            case R.id.radioButtonMale:
                if (male.isChecked()) {
                    //TODO
                }
                break;

            case R.id.radioButtonFemale:
                if (male.isChecked()) {
                    //TODO
                }
                break;
        }
    }
}





