package finalproject.com.getfit;

/**
 * Created by Gurumukh on 2/8/15.
 */

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.parse.ParseFile;
import com.parse.ParseUser;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;


/**
 * Created by rishabhmittal on 2/8/15.
 */
public class NewProfileActivity extends Activity implements View.OnClickListener {
    private RadioGroup gender;
    private RadioButton male, female;
    private EditText birthDate, weight, height;
    private TextView profilePicText;
    private ImageView profilePictureImgView;
    private Uri imageUri;
    private boolean imageChanged = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newprofile);
        this.imageUri = Uri.parse("android.resource://org.intracode.contactmanager/drawable/no_user_logo.png");

        gender = (RadioGroup) findViewById(R.id.radioButtonGender);
        male = (RadioButton) findViewById(R.id.radioButtonMale);
        female = (RadioButton) findViewById(R.id.radioButtonFemale);
        profilePicText = (TextView) findViewById(R.id.profile_photo_text);
        birthDate = (EditText) findViewById(R.id.txtEmail);
        weight = (EditText) findViewById(R.id.txtWeight);
        height = (EditText) findViewById(R.id.txtHeight);
        profilePictureImgView = (ImageView) findViewById(R.id.imgViewProfilePic);

        final Button doneButton = (Button) findViewById(R.id.doneButton);
        final Button skipButton = (Button) findViewById(R.id.laterButton);

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                storeAdditionalUserDetails(null,-1,-1,null);
                Intent i = new Intent(NewProfileActivity.this, HomePageActivity.class);
                startActivity(i);
                finish();
                //TODO
            }
        });

        skipButton.setOnClickListener(new View.OnClickListener() {
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
                doneButton.setEnabled(String.valueOf(height.getText()).trim().length() > 0);
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
                this.imageChanged = true;
            }
        }
    }

    @Override
    public void onClick(View v) {
        int checkedRadioButtonId = gender.getCheckedRadioButtonId();

        switch (checkedRadioButtonId) {
            case R.id.radioButtonMale:
                female.setChecked(false);
                break;

            case R.id.radioButtonFemale:
                male.setChecked(false);
                break;
        }
    }

    private void storeAdditionalUserDetails(String gender, int weight, int height, Date birthDate){
        ParseUser currentUser = ParseUser.getCurrentUser();
            if(gender != null) currentUser.put("gender",gender);
            if(weight != -1)   currentUser.put("weight",weight);
            if(height != -1)   currentUser.put("height",height);
            if(birthDate != null) currentUser.put("birthDate",birthDate);

        if(this.imageChanged == true) {
            byte[] profilePic = convertImageToByte(imageUri);
            ParseFile file = new ParseFile(currentUser.get("name") + ".jpg", profilePic);
            currentUser.put("profilePic", file);
        }
        currentUser.saveInBackground();
    }

    //Should throw the exception..must be caught somewhere else..
    private byte[] convertImageToByte(Uri uri){
        byte[] data = null;
        try {
            ContentResolver cr = getBaseContext().getContentResolver();
            InputStream inputStream = cr.openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            data = baos.toByteArray();

        } catch (FileNotFoundException e) {
            e.printStackTrace(); //Problem with reading the stream
        }
        return data;
    }

}




