package finalproject.com.getfit;

/**
 * Created by Gurumukh on 2/4/15.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BaseWorkoutFragment extends Fragment {

    public final static String TAG = BaseWorkoutFragment.class.getSimpleName();

    public BaseWorkoutFragment() {
        // TODO Auto-generated constructor stub
    }

    public static BaseWorkoutFragment newInstance() {
        return new BaseWorkoutFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_workouts, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
