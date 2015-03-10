package finalproject.com.getfit;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import finalproject.com.getfit.R;

/**
 * Created by rishabhmittal on 3/2/15.
 */
public class EditProfileDialog extends DialogFragment
{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Drawable textlines = getResources().getDrawable( R.drawable.textlines);
        ColorFilter filter = new LightingColorFilter( Color.parseColor("#eaeff3"), Color.parseColor("#eaeff3"));
        textlines.setColorFilter(filter);
        return inflater.inflate(R.layout.dialog_edit_profile,null);
    }

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
}
