package finalproject.com.getfit;

/**
 * Created by Gurumukh on 2/8/15.
 */

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.RadioGroup.OnCheckedChangeListener;
import com.parse.ParseFile;
import com.parse.ParseUser;
import android.graphics.Matrix;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuffXfermode;
import android.provider.MediaStore.Images;
/**
 * Created by rishabhmittal on 2/8/15.
 */
public class NewProfileActivity extends Activity {
    private RadioGroup gender;
    private EditText  weight, height;
    private Button birthDate;
    private TextView profilePicText;
    private ImageView profilePictureImgView;
    private Uri imageUri;
    private boolean imageChanged = false;
    private Button doneButton;
    private Button skipButton;
    private String genderText;
    private  int weightText;
    private  int heightText;
    private int mYear, mMonth, mDay;
    private Date birthD;
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
                if (!weight.getText().toString().equals("") && weight.getText().toString().length() > 0)
                    weightText = Integer.parseInt(weight.getText().toString());
                else
                    weightText = -1;
                if (!height.getText().toString().equals("") && height.getText().toString().length() > 0)
                heightText = Integer.parseInt(height.getText().toString());
                else
                    heightText = -1;
                if (!birthDate.getText().toString().equals("") && birthDate.getText().toString().length() > 0)
                    birthDateText = weight.getText().toString();
                else
                    birthD = null;
                RadioButton selectRadio = (RadioButton) findViewById(gender
                        .getCheckedRadioButtonId());
                genderText = selectRadio.getText().toString();
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
                Bitmap bitmap = null;
                imageUri = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                bitmap = getResizedBitmap(bitmap,400,400);
                profilePictureImgView.setImageBitmap(bitmap);
                profilePicText.setText("");
                this.imageChanged = true;
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

    public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
        return resizedBitmap;
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




