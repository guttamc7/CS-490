package finalproject.com.getfit;

/**
 * Created by Gurumukh on 2/4/15.
 */
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import finalproject.com.getfit.viewpager.CallerClass;
import finalproject.com.getfit.viewpager.EditProfileDialog;

public class ProfileFragment extends Fragment implements View.OnClickListener //If I make this abstract then HomeFragment Stops working
{

    ImageButton hell;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        hell = (ImageButton) rootView.findViewById(R.id.imageButton);
        hell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                CallerClass callerClass = new CallerClass();
                callerClass.makeDialogBox();
                //Intent intent = new Intent();
                //intent.setType("image/*");
                //intent.setAction(Intent.ACTION_GET_CONTENT);
                //startActivityForResult(Intent.createChooser(intent, "Select Profile Picture"), 1);
            }
        });
        return rootView;
    }
}
