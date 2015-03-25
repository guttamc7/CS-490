package finalproject.com.getfit;

/**
 * Created by Gurumukh on 2/4/15.
 */
import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.preference.DialogPreference;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageButton;

import com.getbase.floatingactionbutton.FloatingActionButton;

import finalproject.com.getfit.viewpager.RootFragment;

public class ProfileFragment extends RootFragment
{

    FloatingActionButton editProfileButton;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        editProfileButton = (FloatingActionButton) rootView.findViewById(R.id.edit_profile_fab);
        editProfileButton.setSize(FloatingActionButton.SIZE_NORMAL);
        editProfileButton.setColorNormalResId(R.color.button_red);
        editProfileButton.setColorPressedResId(R.color.button_yellow);
        editProfileButton.setIcon(R.drawable.ic_action_edit);
        editProfileButton.setStrokeVisible(false);
        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                DialogFragment dialogFrag = new EditProfileDialog();
                dialogFrag.show(getActivity().getFragmentManager().beginTransaction(), "dialog");

                     }
        });
        return rootView;
    }



}
