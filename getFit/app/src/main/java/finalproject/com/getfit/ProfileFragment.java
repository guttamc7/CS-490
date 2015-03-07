package finalproject.com.getfit;

/**
 * Created by Gurumukh on 2/4/15.
 */
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import finalproject.com.getfit.viewpager.EditProfileDialog;
import finalproject.com.getfit.viewpager.RootFragment;

public class ProfileFragment extends RootFragment
{

    ImageButton editProfileButton;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        editProfileButton = (ImageButton) rootView.findViewById(R.id.imageButton);
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
