package finalproject.com.getfit;

/**
 * Created by Gurumukh on 3/30/15.
 */
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CreateWorkoutDialog extends DialogFragment {
    private View rootView;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.dialog_create_workout, null);
        return rootView;
    }
}
