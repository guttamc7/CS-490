package finalproject.com.getfit.viewpager;

import android.app.Activity;

/**
 * Created by rishabhmittal on 3/4/15.
 */
public class CallerClass extends Activity
{
    public void makeDialogBox()
    {
        EditProfileDialog editProfileDialog = EditProfileDialog.newInstance();
        editProfileDialog.show(getFragmentManager(), "hello");
    }
}
