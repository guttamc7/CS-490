package com.gym8.main;
/**
 * Created by Gurumukh on 2/4/15.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.res.Configuration;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.gym8.baseworkout.BaseWorkoutFragment;
import com.gym8.customworkout.CustomWorkoutFragment;
import com.gym8.messages.MessagesFragment;
import com.gym8.userprofile.EditProfileDialog;
import com.parse.ParseUser;

import java.io.FileNotFoundException;
import java.io.IOException;

public class HomePageActivity extends FragmentActivity {

    private final static String TAG_FRAGMENT = "BASE_FRAGMENT";
    private DrawerLayout mDrawerLayout;
    private HomeFragment homeFragment;
    private static ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mDrawerItems;
    private Integer[] colorId = {R.color.action_bar_blue, R.color.schedule_color, R.color.button_red, R.color.button_yellow, R.color.primary_white,R.color.delete_color};
    private Integer[] imageId = {R.drawable.ic_action_home, R.drawable.ic_action_list, R.drawable.ic_action_pin,
            R.drawable.ic_messages,R.drawable.ic_action_info, R.drawable.ic_action_arrow_left};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        boolean goToMessages = false;
        mTitle = mDrawerTitle = getTitle();
        Bundle extras = getIntent().getExtras();


        mDrawerItems = getResources().getStringArray(R.array.drawer_titles);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        mDrawerList.setBackgroundColor(getResources().getColor(R.color.navigation_drawer_color));
        // Add items to the ListView
        mDrawerList.setAdapter(new DrawerListAdapter(this, mDrawerItems, imageId,colorId));
        // Set the OnItemClickListener so something happens when a
        // user clicks on an item.
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // Enable ActionBar app icon to behave as action to toggle the NavigationDrawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayShowTitleEnabled(true);

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
        if (savedInstanceState == null) {
            navigateTo(0);
            if(extras!=null) {
                goToMessages = extras.getBoolean("Go to Messages");
            }
        } else {
            homeFragment = (HomeFragment) getSupportFragmentManager().getFragments().get(0);
            goToMessages = extras.getBoolean("Go to Messages");
        }
        if(goToMessages)
            navigateTo(3);

    }



    /*
     * If you do not have any menus, you still need this function
     * in order to open or close the NavigationDrawer when the user
     * clicking the ActionBar app icon.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
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

    @Override
    public void onBackPressed() {
        String className = getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT).getClass().getName();
        if (!homeFragment.onBackPressed() || className.equals("com.gym8.main.HomeFragment")) {
            if (!(getSupportFragmentManager().getBackStackEntryCount() == 0)) {
                super.onBackPressed();
            }
        }
    }

    @Override
    public void onActivityResult(int reqCode, int resCode, Intent data) {
        if (resCode == Activity.RESULT_OK) {
            if (reqCode == 2) {
                EditProfileDialog.setImageUri(data.getData());
                try {
                    EditProfileDialog.setImageBitmap(EditProfileDialog.getResizedBitmap(MediaStore.Images.Media.getBitmap(getContentResolver(), EditProfileDialog.getImageUri()), 400, 400));
                    EditProfileDialog.getProfilePictureImgView().setImageBitmap(EditProfileDialog.getResizedBitmap());
                    EditProfileDialog.setImageChanged(true);
                } catch (Exception e) {
                    Toast.makeText(this, "Error while reading the image", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    private class DrawerItemClickListener implements OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            navigateTo(position);
            getActionBar().setTitle(mDrawerItems[position]);
            getActionBar().setIcon(imageId[position]);
        }
    }


    public void navigateTo(int position) {
        switch (position) {
            case 0:
                homeFragment = new HomeFragment();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_frame, homeFragment, TAG_FRAGMENT).commit();
                break;
            case 1:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_frame, BaseWorkoutFragment.newInstance(), TAG_FRAGMENT).commit();
                break;
            case 2:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_frame, CustomWorkoutFragment.newInstance(), TAG_FRAGMENT).commit();
                break;
            case 3:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_frame, MessagesFragment.newInstance(), TAG_FRAGMENT).commit();
                break;
            case 4:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_frame, AboutFragment.newInstance(), TAG_FRAGMENT).commit();
                break;
            case 5: //SignOut
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Signing Out. Have a Great Day. ");
                builder.setTitle("Sign Out");
                // builder.setIcon(R.drawable.ic_action_accept);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        ParseUser.getCurrentUser().logOut();
                        finish();
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                break;
        }
        mDrawerLayout.closeDrawer(mDrawerList);
    }
}
