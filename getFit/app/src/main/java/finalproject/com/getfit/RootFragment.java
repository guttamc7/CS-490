package finalproject.com.getfit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import finalproject.com.getfit.OnBackPressListener;
/**
 * Created by Gurumukh on 3/2/15.
 */

public class RootFragment extends Fragment implements OnBackPressListener{

        @Override
        public boolean onBackPressed() {
            return new BackPressImpl(this).onBackPressed();
        }

}
