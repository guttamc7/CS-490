package finalproject.com.getfit.createworkout;

/**
 * Created by Gurumukh on 3/30/15.
 */
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.support.v4.app.Fragment;
import finalproject.com.getfit.R;

public class CreateWorkoutInformationFragment extends Fragment {
    private View rootView;
    private Button nextButton;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_create_workout_information, null);

        return rootView;
    }

}
