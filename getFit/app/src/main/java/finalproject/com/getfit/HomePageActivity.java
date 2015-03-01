package finalproject.com.getfit;
/**
 * Created by Gurumukh on 2/4/15.
 */
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.content.res.Configuration;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class HomePageActivity extends FragmentActivity {

    private static final String TAG = HomePageActivity.class.getSimpleName();

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mDrawerItems;
    private Integer[] imageId = {R.drawable.ic_action_home, R.drawable.ic_action_list,R.drawable.ic_action_calendar_month,R.drawable.ic_action_add,
            R.drawable.ic_messages, R.drawable.ic_action_settings, R.drawable.ic_action_arrow_left};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        mTitle = mDrawerTitle = getTitle();

        mDrawerItems = getResources().getStringArray(R.array.drawer_titles);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,  GravityCompat.START);
        mDrawerList.setBackgroundColor(Color.parseColor("#34678a"));
        // Add items to the ListView
        mDrawerList.setAdapter(new DrawerListAdapter(this,mDrawerItems,imageId));
        // Set the OnItemClickListener so something happens when a
        // user clicks on an item.
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // Enable ActionBar app icon to behave as action to toggle the NavigationDrawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        //getActionBar().setIcon(R.color.transparent);
        getActionBar().setDisplayShowTitleEnabled(true);
        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#34678a")));

        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                R.drawable.ic_drawer,
                R.string.drawer_open,
                R.string.drawer_close
        ) {
            public void onDrawerClosed(View view) {
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu
            }

            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        // Set the default content area to item 0
        // when the app opens for the first time
        if(savedInstanceState == null) {
            navigateTo(0);
        }

    }

    /*
     * If you do not have any menus, you still need this function
     * in order to open or close the NavigationDrawer when the user
     * clicking the ActionBar app icon.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
     * When using the ActionBarDrawerToggle, you must call it during onPostCreate()
     * and onConfigurationChanged()
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private class DrawerItemClickListener implements OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            navigateTo(position);
            getActionBar().setTitle(mDrawerItems[position]);
            getActionBar().setIcon(imageId[position]);
        }
    }

    private void navigateTo(int position) {
        //System.out.println("In Home Page Activity");

        switch(position) {
            case 0:

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_frame, HomeFragment.newInstance(), HomeFragment.TAG).commit();
                break;
            case 1:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_frame, WorkoutLibFragment.newInstance(), WorkoutLibFragment.TAG).commit();
                break;
            case 2: //TODO
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_frame, BaseWorkoutFragment.newInstance(), BaseWorkoutFragment.TAG).commit();
                break;
            case 3: //TODO
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_frame, BaseWorkoutFragment.newInstance(), BaseWorkoutFragment.TAG).commit();
                break;

        }
        mDrawerLayout.closeDrawer(mDrawerList);
    }





}


