package finalproject.com.getfit.viewpager;

import android.support.v4.app.Fragment;

import finalproject.com.getfit.viewpager.BackPressImpl;
import finalproject.com.getfit.viewpager.OnBackPressListener;

/**
 * Created by Gurumukh on 3/2/15.
 */

public class RootFragment extends Fragment implements OnBackPressListener {

        @Override
        public boolean onBackPressed() {
            return new BackPressImpl(this).onBackPressed();
        }

}
