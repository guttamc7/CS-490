package finalproject.com.getfit;

/**
 * Created by Gurumukh on 2/4/15.
 */
import java.util.Locale;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        pagerTabStrip.setTabIndicatorColor(Color.parseColor("#34678a"));

        mViewPager = (ViewPager) v.findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        return v;
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    TrendingFragment trendingtab = new TrendingFragment();
                    return trendingtab;
                case 1:
                    FindNearbyFragment nearbytab = new FindNearbyFragment();
                    return nearbytab;
                case 2:
                    ProfileFragment profiletab = new ProfileFragment();
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
                    Drawable myDrawable2 = getResources().getDrawable( R.drawable.ic_action_user );
                    SpannableStringBuilder sb2 = new SpannableStringBuilder("    PROFILE"); // space added before text for convenience
                    myDrawable2.setBounds(0, 0, myDrawable2.getIntrinsicWidth(), myDrawable2.getIntrinsicHeight());
                    ImageSpan span2 = new ImageSpan(myDrawable2, ImageSpan.ALIGN_BASELINE);
                    sb2.setSpan(span2, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    return sb2;
            }
            return null;
        }
    }
}