package com.gym8.createworkout;

import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.Window;
import android.widget.Button;

import com.viewpagerindicator.CirclePageIndicator;

import com.gym8.main.R;
/**
 * Created by Gurumukh on 4/2/15.
 */
public class CreateWorkoutDialog extends DialogFragment {
    private Button positiveButton;
    private static CreateWorkoutDialog f;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static CreateWorkoutDialog newInstance(){
        f = new CreateWorkoutDialog();
        return f;
    }
    public static CreateWorkoutDialog getInstance() {
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_create_workout, container);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        ViewPager pager = (ViewPager) view.findViewById(R.id.create_workout_pager);
        MyFragmentAdapter adapter = new MyFragmentAdapter(getChildFragmentManager());
        pager.setAdapter(adapter);
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position)
            {
            }
            @Override
            public void onPageScrollStateChanged(int state)
            {
            }
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {
                System.out.println("On Page Scrolled");
            }
        });
        CirclePageIndicator mIndicator = (CirclePageIndicator)view.findViewById(R.id.indicator);
        mIndicator.setViewPager(pager);
        mIndicator.setCurrentItem(0);
        return view;
    }

    public void onStart() {
        super.onStart();
        if (this.getDialog() == null) {
            return;
        }
        int dialogWidth = ViewGroup.LayoutParams.MATCH_PARENT;
        int dialogHeight =ViewGroup.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setLayout(dialogWidth, dialogHeight);

    }


    public class MyFragmentAdapter extends FragmentPagerAdapter {
        private final FragmentManager mFragmentManager;
        private Fragment mFragmentAtPos0;
        CalendarPageListener listener = new CalendarPageListener();;

        private final class CalendarPageListener implements
                CalendarPageFragmentListener {
            public void onSwitchToNextFragment() {
                mFragmentManager.beginTransaction().remove(mFragmentAtPos0)
                        .commit();
                if (mFragmentAtPos0 instanceof CreateWorkoutExerciseFragment){
                    mFragmentAtPos0 = ExerciseListFragment.newInstance(listener);
                }else{ // Instance of NextFragment
                    mFragmentAtPos0 = CreateWorkoutExerciseFragment.newInstance(listener);
                }
                notifyDataSetChanged();
            }

        }
        public MyFragmentAdapter(FragmentManager fm) {
            super(fm);
            mFragmentManager = fm;
        }
        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    CreateWorkoutInformationFragment informationTab = new CreateWorkoutInformationFragment();
                    return informationTab;
                case 1:
                    if (mFragmentAtPos0 == null) {
                        mFragmentAtPos0 =CreateWorkoutExerciseFragment.newInstance(listener);
                    }
                    return mFragmentAtPos0;
            }
            return null;
        }
        @Override
        public int getItemPosition(Object object) {
            if (object instanceof CreateWorkoutExerciseFragment && mFragmentAtPos0 instanceof ExerciseListFragment)
                return POSITION_NONE;
            if (object instanceof ExerciseListFragment && mFragmentAtPos0 instanceof CreateWorkoutExerciseFragment)
                return POSITION_NONE;
            return POSITION_UNCHANGED;
        }



    }
    public interface CalendarPageFragmentListener {
        void onSwitchToNextFragment();
    }

    public void onDismiss(DialogInterface dialog)
    {
        ExerciseListDetailsDialog.exerciseWorkoutList = null;
        CreateWorkoutInformationFragment.workoutDescription = "";
        CreateWorkoutInformationFragment.workoutName = "";
        CreateWorkoutInformationFragment.workoutLevel = 0;
    }

}
