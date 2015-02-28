package finalproject.com.getfit;

/**
 * Created by Gurumukh on 2/10/15.
 */
import java.util.ArrayList;
import java.util.List;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.support.v4.view.ViewPager.OnPageChangeListener;

import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseException;
import com.parse.Parse;
import com.parse.ParseUser;
import com.parse.ui.ParseLoginBuilder;

public class WorkoutLibFragment extends Fragment {

    public static final String TAG = WorkoutLibFragment.class.getSimpleName();
    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;
    int level = 0;
    public static ArrayList<BaseWorkout> baseworkoutresults;
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
        pagerTabStrip.setTabIndicatorColor(Color.parseColor("#34678a"));
        WorkoutLibFragment w;
        w = new WorkoutLibFragment();
        w.getBaseWorkOuts();
        mViewPager = (ViewPager) v.findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

            // This method will be invoked when a new page becomes selected.
            @Override
            public void onPageSelected(int position) {
                System.out.println("Page position" + position);
                WorkoutLibFragment w = new WorkoutLibFragment();
                if(position == 0){
                   w.getBaseWorkOuts();
                }

            }

            // This method will be invoked when the current page is scrolled
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                System.out.println("Page Scrolled" + position);
                WorkoutLibFragment w = new WorkoutLibFragment();
                if(position == 0) {
                        w.getBaseWorkOuts();
                    System.out.println("Base Workouts Called");
                }
                // Code goes here
            }

            // Called when the scroll state changes:
            // SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING, SCROLL_STATE_SETTLING
            @Override
            public void onPageScrollStateChanged(int state) {
                // Code goes here
            }
        });

        return v;
    }

    public void getBaseWorkOuts() {
        baseworkoutresults = new ArrayList<BaseWorkout>();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Workout");
        query.whereEqualTo("userId", ParseUser.getCurrentUser());
        query.findInBackground(new FindCallback<ParseObject>() {

            public void done(List<ParseObject> workoutList, ParseException e) {
                if ( e == null) {
                    BaseWorkout baseWorkoutData;
                    for(int i=0; i< workoutList.size();i++) {
                        baseWorkoutData= new BaseWorkout();
                        ParseObject obj = workoutList.get(i);
                        int level  = obj.getInt("level");
                        baseWorkoutData.setBaseWorkoutLevel(Integer.toString(level));
                        baseWorkoutData.setBaseWorkoutDescription(obj.getString("Description"));
                        baseWorkoutData.setBaseWorkoutName(obj.getString("workoutName"));
                        baseworkoutresults.add(baseWorkoutData);
                    }
                    System.out.println(baseworkoutresults.get(0));

                    //All the base workouts retrieved
                } else {
                    System.out.println(e.getMessage());
                    //Exception
                }
            }
        });
    }

    public ArrayList<BaseWorkout> getWorkoutData() {
        return baseworkoutresults;
    }



    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:

                    System.out.println("Base workout created here?");
                    //System.out.println(baseworkoutresults.size());
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

    }



}
