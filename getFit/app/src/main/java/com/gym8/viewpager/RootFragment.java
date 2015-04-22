package com.gym8.viewpager;

import android.support.v4.app.Fragment;

/**
 * Created by Gurumukh on 3/2/15.
 */

public class RootFragment extends Fragment implements OnBackPressListener {

        @Override
        public boolean onBackPressed() {
            return new BackPressImpl(this).onBackPressed();
        }


}
