package finalproject.com.getfit;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.parse.ParseFile;
import com.parse.ParseUser;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import finalproject.com.getfit.R;

/**
 * Created by rishabhmittal on 3/2/15.
 */
public class EditProfileDialog extends DialogFragment
{
    private RadioGroup gender;
    private EditText weight, height;
    private Button birthDate;
    private TextView userNameTextView;
    private ImageView profilePictureImgView;
    private Uri imageUri;
    private Bitmap resizedBitmap = null;
    private boolean imageChanged = false;
    private Button submitButton;
    private Button cancelButton;
    private String genderText;
    private int weightText = -1;
    private int heightText = -1;
    private int mYear, mMonth, mDay;
    private Date birthD = null;
    private String birthDateText;
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.dialog_edit_profile,null);
        this.imageUri = Uri.parse("android.resource://org.intracode.contactmanager/drawable/no_user_logo.png");
        gender = (RadioGroup) rootView.findViewById(R.id.radioButtonGender_edit_profile);
        userNameTextView = (TextView) rootView.findViewById(R.id.name_of_user_edit_profile);
        birthDate = (Button) rootView.findViewById(R.id.txtBirthdate_edit_profile);
        weight = (EditText) rootView.findViewById(R.id.txtWeight_edit_profile);
        height = (EditText) rootView.findViewById(R.id.txtHeight_edit_profile);
        profilePictureImgView = (ImageView) rootView.findViewById(R.id.imgViewProfilePic_edit_profile);
        submitButton = (Button) rootView.findViewById(R.id.submitButton_edit_profile);
        cancelButton= (Button) rootView.findViewById(R.id.cancelButton_edit_profile);
        ParseUser currentUser = ParseUser.getCurrentUser();
        userNameTextView.setText(currentUser.getUsername());
        birthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayDatePicker();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view){
                if (!weight.getText().toString().equals("") && weight.getText().toString().length() > 0)
                    weightText = Integer.parseInt(weight.getText().toString());

                if (!height.getText().toString().equals("") && height.getText().toString().length() > 0)
                    heightText = Integer.parseInt(height.getText().toString());

                if (!birthDate.getText().toString().equals("") && birthDate.getText().toString().length() > 0)
                    birthDateText = weight.getText().toString();
                RadioButton selectRadio = (RadioButton) rootView.findViewById(gender
                        .getCheckedRadioButtonId());
                if (!selectRadio.getText().toString().equals("") && selectRadio.getText().toString().length() > 0)
                    genderText = selectRadio.getText().toString();
                EditUserDetails(genderText, weightText, heightText, birthD);

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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

        return rootView;
    }
     //TODO Edit Profile Dialog Tasks : 1. Get Image from Gallery, 2. Create Circular Bitmap, 3. Submit and Cancel Button Transactions

  /*  public void onActivityResult(int reqCode, int resCode, Intent data) {
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
    */
    @Override
    public void onStart() {
        super.onStart();
        if (this.getDialog() == null) {
            return;
        }
        int dialogWidth = ViewGroup.LayoutParams.MATCH_PARENT;
        int dialogHeight =ViewGroup.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setLayout(dialogWidth, dialogHeight);
    }

    private void EditUserDetails(String gender, int weight, int height, Date birthDate){
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
        DatePickerDialog dpd = new DatePickerDialog(getActivity(),
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
