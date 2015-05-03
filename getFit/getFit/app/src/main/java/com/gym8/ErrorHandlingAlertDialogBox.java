package com.gym8;

import android.app.AlertDialog;
import android.content.Context;


/**
 * Created by rishabhmittal on 4/30/15.
 */
public class ErrorHandlingAlertDialogBox
{

    public static void showDialogBox(Context context)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Error in retrieving data from database.");
        AlertDialog alert = builder.create();
        alert.show();
    }

}

