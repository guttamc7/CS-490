package finalproject.com.getfit;

/**
 * Created by Gurumukh on 2/8/15.
 */

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.parse.ParseFile;
import com.parse.ParseUser;
import android.graphics.Matrix;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

public class NewProfileActivity extends Activity {
    private RadioGroup gender;
    private EditText  weight, height;
    private Button birthDate;
    private TextView profilePicText;
    private ImageView profilePictureImgView;
    private Uri imageUri;
    private Bitmap resizedBitmap = null;
    private boolean imageChanged = false;
    private Button doneButton;
    private Button skipButton;
    private String genderText;
    private int weightText = -1;
    private int heightText = -1;
    private int mYear, mMonth, mDay;
    private Date birthD = null;
    private String birthDateText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newprofile);
        this.imageUri = Uri.parse("android.resource://org.intracode.contactmanager/drawable/no_user_logo.png");
        gender = (RadioGroup) findViewById(R.id.radioButtonGender);
        profilePicText = (TextView) findViewById(R.id.profile_photo_text);
        birthDate = (Button) findViewById(R.id.txtBirthdate);
        weight = (EditText) findViewById(R.id.txtWeight);
        height = (EditText) findViewById(R.id.txtHeight);
        profilePictureImgView = (ImageView) findViewById(R.id.imgViewProfilePic);
        doneButton = (Button) findViewById(R.id.doneButton);
        skipButton= (Button) findViewById(R.id.notNowButton);
        birthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    displayDatePicker();
            }
        });

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view){
                if (weight.getText().toString() == null || (!weight.getText().toString().equals("") && weight.getText().toString().length() > 0))
                    weightText = Integer.parseInt(weight.getText().toString());

                if (height.getText().toString() == null || (!height.getText().toString().equals("") && height.getText().toString().length() > 0))
                heightText = Integer.parseInt(height.getText().toString());

                if (birthDate.getText().toString() == null || (!birthDate.getText().toString().equals("") && birthDate.getText().toString().length() > 0))
                    birthDateText = weight.getText().toString();
                RadioButton selectRadio = null;
                Log.d("gender: ",Integer.toString(gender.getCheckedRadioButtonId()));
                if(gender.getCheckedRadioButtonId() == -1)
                    genderText = null;
                else
                {
                    selectRadio = (RadioButton) findViewById(gender.getCheckedRadioButtonId());
                    Log.d("selectRadio: ", selectRadio.getText().toString());
                    if (selectRadio.getText().toString() == null || (!selectRadio.getText().toString().equals("") && selectRadio.getText().toString().length() > 0))
                        genderText = selectRadio.getText().toString();
                }
                storeAdditionalUserDetails(genderText, weightText, heightText, birthD);
                Intent i = new Intent(NewProfileActivity.this, HomePageActivity.class);
                startActivity(i);
                finish();
             }
        });

        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view){
                Intent i = new Intent(NewProfileActivity.this, HomePageActivity.class);
                startActivity(i);
                finish();
                }
        });
        profilePictureImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Profile Picture"), 1);
                }
        });
    }

    public void onActivityResult(int reqCode, int resCode, Intent data) {
        if (resCode == RESULT_OK) {
            if (reqCode == 1) {
                imageUri = data.getData();
                try {
                    resizedBitmap = getResizedBitmap(MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri),400,400);
                    profilePictureImgView.setImageBitmap(resizedBitmap);
                    profilePicText.setText("");
                    this.imageChanged = true;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void storeAdditionalUserDetails(String gender, int weight, int height, Date birthDate){
        ParseUser currentUser = ParseUser.getCurrentUser();
            if(gender != null) currentUser.put("gender",gender);
            if(weight != -1)   currentUser.put("weight",weight);
            if(height != -1)   currentUser.put("height",height);
            if(birthDate != null) currentUser.put("birthDate",birthDate);

        if(this.imageChanged == true) {
            byte[] profilePic = convertBitmapToBytes();
            ParseFile file = new ParseFile(currentUser.get("name") + ".jpg", profilePic);
            currentUser.put("profilePic", file);
        }
        currentUser.saveInBackground();
    }

    //Should throw the exception..must be caught somewhere else..
    private byte[] convertBitmapToBytes(){
        byte[] data = null;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap resizedBitmap1 = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
        return resizedBitmap1;
    }

    public void displayDatePicker() {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        // Launch Date Picker Dialog
        DatePickerDialog dpd = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                       birthD = new Date(year-1900, monthOfYear, dayOfMonth);
                       birthDateText = Integer.toString(monthOfYear+1) + "/" + Integer.toString(dayOfMonth)+ "/" + Integer.toString(year);
                       birthDate.setText(birthDateText);

                    }
                }, mYear, mMonth, mDay);
        dpd.show();
    }

}




