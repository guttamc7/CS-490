package finalproject.com.getfit.createworkout;

import android.app.AlertDialog;
import android.app.Dialog;
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

import finalproject.com.getfit.R;
/**
 * Created by Gurumukh on 4/2/15.
 */
public class CreateWorkoutDialog extends DialogFragment {
    private Button positiveButton;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static CreateWorkoutDialog newInstance(){
        CreateWorkoutDialog f = new CreateWorkoutDialog();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_create_workout, container);
        getDialog().setTitle("Create Workout");
        ViewPager pager = (ViewPager) view.findViewById(R.id.create_workout_pager);
        MyFragmentAdapter adapter = new MyFragmentAdapter(getChildFragmentManager());
        pager.setAdapter(adapter);
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
        public MyFragmentAdapter(FragmentManager fm) {
            super(fm);
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
                    CreateWorkoutExerciseFragment exerciseTab = new CreateWorkoutExerciseFragment();
                    return exerciseTab;
            }
            return null;
        }


    }

}
