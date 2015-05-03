package com.gym8.main;

/**
 * Created by Gurumukh on 2/4/15.
 */
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
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

import com.gym8.findnearby.FindNearbyFragment;
import com.gym8.main.R;
import com.gym8.trendingworkout.TrendingWorkoutFragment;
import com.gym8.userprofile.UserProfileFragment;
import com.gym8.viewpager.OnBackPressListener;

public class HomeFragment extends Fragment {

    public static final String TAG = HomeFragment.class.getSimpleName();
    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        mSectionsPagerAdapter = new SectionsPagerAdapter(
                getChildFragmentManager());
        PagerTabStrip pagerTabStrip = (PagerTabStrip) v.findViewById(R.id.pager_title_strip);
        pagerTabStrip.setDrawFullUnderline(true);
        pagerTabStrip.setTabIndicatorColor(getResources().getColor(R.color.primary_white));

        mViewPager = (ViewPager) v.findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(3);
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case UserProfileFragment.DIALOG_FRAGMENT:

                if (resultCode == Activity.RESULT_OK) {
                    System.out.println("In Activity Result");
                    Bundle bundle = data.getExtras();
                    String heightEdited = bundle.getString("height");
                    String weightEdited = bundle.getString("weight");
                    String dateEditedText = bundle.getString("birthDate");

                    UserProfileFragment.getUserHeight().setText(heightEdited + " cm");
                    UserProfileFragment.getUserWeight().setText(weightEdited + " lbs");

                    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                    Date dateEdited = null;
                    try {
                        dateEdited = formatter.parse(dateEditedText);
                    } catch (java.text.ParseException e) {
                        e.printStackTrace();
                    }
                    UserProfileFragment.getUserAge().setText(Integer.toString(UserProfileFragment.getAge(dateEdited)) + " years old");

                    // After Ok code.
                } else if (resultCode == Activity.RESULT_CANCELED) {
                    // After Cancel code.
                }

                break;
        }
    }

    public boolean onBackPressed() {
        // currently visible tab Fragment
        OnBackPressListener currentFragment = (OnBackPressListener)mSectionsPagerAdapter.getRegisteredFragment(mViewPager.getCurrentItem());
        if (currentFragment != null) {
            return currentFragment.onBackPressed();
        }

        return false;
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        SparseArray<Fragment> registeredFragments1 = new SparseArray<Fragment>();
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    TrendingWorkoutFragment trendingtab = new TrendingWorkoutFragment();
                    return trendingtab;
                case 1:
                    FindNearbyFragment nearbytab = new FindNearbyFragment();
                    return nearbytab;
                case 2:
                    UserProfileFragment profiletab = new UserProfileFragment();
                    return profiletab;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    Drawable myDrawable = getResources().getDrawable( R.drawable.ic_action_trending );
                    SpannableStringBuilder sb = new SpannableStringBuilder("    TRENDING"); // space added before text for convenience
                    myDrawable.setBounds(0, 0, myDrawable.getIntrinsicWidth(), myDrawable.getIntrinsicHeight());
                    ImageSpan span = new ImageSpan(myDrawable, ImageSpan.ALIGN_BASELINE);
                    sb.setSpan(span, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    return sb;

                case 1:
                    Drawable myDrawable1 = getResources().getDrawable( R.drawable.ic_action_circles );
                    SpannableStringBuilder sb1 = new SpannableStringBuilder("    FIND NEARBY"); // space added before text for convenience
                    myDrawable1.setBounds(0, 0, myDrawable1.getIntrinsicWidth(), myDrawable1.getIntrinsicHeight());
                    ImageSpan span1 = new ImageSpan(myDrawable1, ImageSpan.ALIGN_BASELINE);
                    sb1.setSpan(span1, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    return sb1;
                case 2:
                    Drawable myDrawable2 = getResources().getDrawable( R.drawable.ic_action_user_white );
                    SpannableStringBuilder sb2 = new SpannableStringBuilder("    PROFILE"); // space added before text for convenience
                    myDrawable2.setBounds(0, 0, myDrawable2.getIntrinsicWidth(), myDrawable2.getIntrinsicHeight());
                    ImageSpan span2 = new ImageSpan(myDrawable2, ImageSpan.ALIGN_BASELINE);
                    sb2.setSpan(span2, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    return sb2;
            }
            return null;
        }
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            registeredFragments1.put(position, fragment);
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
            registeredFragments1.remove(position);
            super.destroyItem(container, position, object);
        }


        /**
         * Get the Fragment by position
         *
         * @param position tab position of the fragment
         * @return
         */
        public Fragment getRegisteredFragment(int position) {
            return registeredFragments1.get(position);
        }

    }
}