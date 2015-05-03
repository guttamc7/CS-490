package com.gym8.userprofile;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.support.v4.app.DialogFragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gym8.main.R;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by rishabhmittal on 3/2/15.
 */
public class EditProfileDialog extends DialogFragment {
    private RadioGroup gender;
    private RadioButton male;
    private RadioButton female;
    private EditText weight, height;
    private Button birthDate;
    private TextView userNameTextView;
    private static ImageView profilePictureImgView;
    private static Uri imageUri;
    private static Bitmap resizedBitmap = null;
    private static boolean imageChanged = false;
    private Button submitButton;
    private Button cancelButton;
    private String genderText;
    private int weightText = -1;
    private int heightText = -1;
    private int mYear;
    private int mMonth;
    private int mDay;
    private Date birthD = null;
    private String birthDateText;
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.dialog_edit_profile, null);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        this.imageUri = Uri.parse("android.resource://org.intracode.contactmanager/drawable/no_user_logo.png");
        gender = (RadioGroup) rootView.findViewById(R.id.radioButtonGender_edit_profile);
        userNameTextView = (TextView) rootView.findViewById(R.id.name_of_user_edit_profile);
        birthDate = (Button) rootView.findViewById(R.id.txtBirthdate_edit_profile);
        weight = (EditText) rootView.findViewById(R.id.txtWeight_edit_profile);
        height = (EditText) rootView.findViewById(R.id.txtHeight_edit_profile);
        profilePictureImgView = (ImageView) rootView.findViewById(R.id.imgViewProfilePic_edit_profile);
        submitButton = (Button) rootView.findViewById(R.id.submitButton_edit_profile);
        cancelButton = (Button) rootView.findViewById(R.id.cancelButton_edit_profile);
        male = (RadioButton) rootView.findViewById(R.id.radioButtonMale_edit_profile);
        female = (RadioButton) rootView.findViewById(R.id.radioButtonFemale_edit_profile);
        setUserAttributes();

        birthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayDatePicker();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                RadioButton selectRadio = (RadioButton) rootView.findViewById(gender
                                                        .getCheckedRadioButtonId());
                                                if (weight.getText().toString() == null || weight.getText().toString().equals("")) {
                                                    Toast.makeText(getActivity(), "Please Enter your Weight", Toast.LENGTH_SHORT).show();
                                                } else if (height.getText().toString() == null || height.getText().toString().equals("")) {
                                                    Toast.makeText(getActivity(), "Please Enter your Height", Toast.LENGTH_SHORT).show();
                                                } else if (birthDate.getText().toString() == null || birthDate.getText().toString().equals("")) {
                                                    Toast.makeText(getActivity(), "Please Enter your Birth Date", Toast.LENGTH_SHORT).show();
                                                } else if (selectRadio.getText().toString() == null || selectRadio.getText().toString().length() == 0) {
                                                    Toast.makeText(getActivity(), "Please Select a Gender", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    try {
                                                        weightText = Integer.parseInt(weight.getText().toString());
                                                    } catch (NumberFormatException e) {
                                                        Toast.makeText(getActivity(), "Please Enter Weight as a Number", Toast.LENGTH_SHORT).show();
                                                    }
                                                    try {
                                                        heightText = Integer.parseInt(height.getText().toString());
                                                    } catch (NumberFormatException e) {
                                                        Toast.makeText(getActivity(), "Please Enter Height as a Number", Toast.LENGTH_SHORT).show();
                                                    }
                                                    birthDateText = birthDate.getText().toString();
                                                    genderText = selectRadio.getText().toString();
                                                    Intent i = new Intent()
                                                            .putExtra("weight", weight.getText().toString())
                                                            .putExtra("gender", genderText)
                                                            .putExtra("birthDate", birthDateText)
                                                            .putExtra("height", height.getText().toString());

                                                    getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, i);
                                                    EditUserDetails(genderText, weightText, heightText, birthD);
                                                    getDialog().dismiss();
                                                }
                                            }
                                        }
        );

        cancelButton.setOnClickListener(new View.OnClickListener()

                                        {
                                            @Override
                                            public void onClick(View view) {
                                                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_CANCELED, getActivity().getIntent());
                                                getDialog().dismiss();
                                            }
                                        }

        );
        profilePictureImgView.setOnClickListener(new View.OnClickListener()

                                                 {
                                                     @Override
                                                     public void onClick(View v) {
                                                         Intent intent = new Intent();
                                                         intent.setType("image/*");
                                                         intent.setAction(Intent.ACTION_GET_CONTENT);

                                                         getActivity().startActivityForResult(Intent.createChooser(intent, "Select Profile Picture"), 2);
                                                     }
                                                 }

        );

        return rootView;
    }


    private void setUserAttributes() {
        ParseUser currentUser = ParseUser.getCurrentUser();
        ParseFile imageFile = currentUser.getParseFile("profilePic");
        if(imageFile == null){
            profilePictureImgView.setImageResource(R.drawable.no_user_logo);
        }
        else {
            imageFile.getDataInBackground(new GetDataCallback() {
                public void done(byte[] data, ParseException e) {
                    if (e == null) {
                        Bitmap bmp = BitmapFactory.decodeByteArray(data, 0,
                                data.length);
                        profilePictureImgView.setImageBitmap(bmp);
                        // data has the bytes for the image
                    } else {
                        Toast.makeText(getActivity(), "Connection error", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        if (currentUser.getString("name") == null) {
            userNameTextView.setText("");
        } else {
            userNameTextView.setText(currentUser.getString("name"));
        }
        if (!(currentUser.getString("gender") == null)) {
            if (currentUser.getString("gender").equals("Male"))
                male.setChecked(true);
            else
                female.setChecked(true);
        }

        if (Integer.toString(currentUser.getInt("weight")).isEmpty()) {
            weight.setText("");
        } else {
            weight.setText(Integer.toString(currentUser.getInt("weight")));
        }
        if (Integer.toString(currentUser.getInt("height")).isEmpty()) {
            height.setText("");
        } else {
            height.setText(Integer.toString(currentUser.getInt("height")));
        }
        Date date = currentUser.getDate("birthDate");
        if (!(date == null)) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);
            String dateString = Integer.toString(month) + "/" + Integer.toString(day) + "/" + Integer.toString(year);
            birthDate.setText(dateString);
        }


    }

    @Override
    public void onStart() {
        super.onStart();
        if (this.getDialog() == null) {
            return;
        }
        int dialogWidth = ViewGroup.LayoutParams.MATCH_PARENT;
        int dialogHeight = ViewGroup.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setLayout(dialogWidth, dialogHeight);
    }

    private void EditUserDetails(String gender, int weight, int height, Date birthDate) {
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (gender != null) {
            currentUser.put("gender", gender);
        }
        ;
        if (weight != -1) {
            currentUser.put("weight", weight);
        }
        if (height != -1) {
            currentUser.put("height", height);
        }
        if (birthDate != null) {
            currentUser.put("birthDate", birthDate);
        }
        if (this.imageChanged == true) {
            byte[] profilePic = convertBitmapToBytes();
            ParseFile file = new ParseFile(currentUser.get("name") + ".jpg", profilePic);
            currentUser.put("profilePic", file);
        }
        currentUser.saveInBackground();
    }

    //Should throw the exception..must be caught somewhere else..
    private byte[] convertBitmapToBytes() {
        byte[] data = null;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
    }

    public static Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
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
                        birthD = new Date(year - 1900, monthOfYear, dayOfMonth);
                        birthDateText = Integer.toString(monthOfYear + 1) + "/" + Integer.toString(dayOfMonth) + "/" + Integer.toString(year);
                        birthDate.setText(birthDateText);

                    }
                }, mYear, mMonth, mDay);
        c.set(mYear - 16, mMonth, mDay);
        Date date = c.getTime();
        dpd.getDatePicker().setMaxDate(date.getTime());
        c.set(mYear - 75, mMonth, mDay);
        date = c.getTime();
        dpd.getDatePicker().setMinDate(date.getTime());
        dpd.show();

    }

    public static Bitmap getResizedBitmap() {
        return resizedBitmap;
    }

    public static Uri getImageUri() {
        return imageUri;
    }

    public static ImageView getProfilePictureImgView() {
        return profilePictureImgView;
    }

    public static void setImageUri(Uri uri) {
        imageUri = uri;
    }

    public static void setImageBitmap(Bitmap bm) {
        resizedBitmap = bm;
    }

    public static void setImageChanged(boolean changed) {
        imageChanged = changed;
    }

}
