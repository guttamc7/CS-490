package finalproject.com.getfit.viewpager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by rishabhmittal on 3/2/15.
 */
public class EditProfileDialog extends DialogFragment
{
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setMessage("This is my dialog").setPositiveButton("Ok",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog = builder.create();
        return dialog;
    }

    public static EditProfileDialog newInstance()
    {
        EditProfileDialog f = new EditProfileDialog();
        return f;
    }
}
