package finalproject.com.getfit;

/**
 * Created by Gurumukh on 2/10/15.
 */
import java.util.Locale;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class WorkoutLibFragment extends Fragment {

    public static final String TAG = WorkoutLibFragment.class.getSimpleName();
    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;
    public static WorkoutLibFragment newInstance() {
        return new WorkoutLibFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_workoutlib, container, false);
        mSectionsPagerAdapter = new SectionsPagerAdapter(
                getChildFragmentManager());
        PagerTabStrip pagerTabStrip = (PagerTabStrip) v.findViewById(R.id.pager_title_strip);
        pagerTabStrip.setDrawFullUnderline(true);
        pagerTabStrip.setTabIndicatorColor(Color.parseColor("#E6A457"));
        mViewPager = (ViewPager) v.findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        return v;
    }

    public boolean onBackPressed() {
        // currently visible tab Fragment
        OnBackPressListener currentFragment = (OnBackPressListener) mSectionsPagerAdapter.getRegisteredFragment(mViewPager.getCurrentItem());

        if (currentFragment != null) {
            // lets see if the currentFragment or any of its childFragment can handle onBackPressed
            return currentFragment.onBackPressed();
        }

        // this Fragment couldn't handle the onBackPressed call
        return false;
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    BaseWorkoutFragment basetab = new BaseWorkoutFragment();
                    return basetab;
                case 1:
                    CustomWorkoutFragment customtab = new CustomWorkoutFragment();
                    return customtab;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    Drawable myDrawable = getResources().getDrawable(R.drawable.ic_action_database);
                    SpannableStringBuilder sb = new SpannableStringBuilder("    BASE WORKOUTS"); // space added before text for convenience
                    myDrawable.setBounds(0, 0, myDrawable.getIntrinsicWidth(), myDrawable.getIntrinsicHeight());
                    ImageSpan span = new ImageSpan(myDrawable, ImageSpan.ALIGN_BASELINE);
                    sb.setSpan(span, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    return sb;

                case 1:
                    Drawable myDrawable1 = getResources().getDrawable(R.drawable.ic_action_brush);
                    SpannableStringBuilder sb1 = new SpannableStringBuilder("    CUSTOM WORKOUTS"); // space added before text for convenience
                    myDrawable1.setBounds(0, 0, myDrawable1.getIntrinsicWidth(), myDrawable1.getIntrinsicHeight());
                    ImageSpan span1 = new ImageSpan(myDrawable1, ImageSpan.ALIGN_BASELINE);
                    sb1.setSpan(span1, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    return sb1;
            }
            return null;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            registeredFragments.put(position, fragment);
            return fragment;
        }

        /**
         * Remove the saved reference from our Map on the Fragment destroy
         *
         * @param container
         * @param position
         * @param object
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            registeredFragments.remove(position);
            super.destroyItem(container, position, object);
        }


        /**
         * Get the Fragment by position
         *
         * @param position tab position of the fragment
         * @return
         */
        public Fragment getRegisteredFragment(int position) {
            return registeredFragments.get(position);
        }

    }



}
