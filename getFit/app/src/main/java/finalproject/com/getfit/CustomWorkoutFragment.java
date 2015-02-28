package finalproject.com.getfit;

/**
 * Created by Gurumukh on 2/10/15.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ListView;

import junit.runner.Version;

public class CustomWorkoutFragment extends Fragment {

    public final static String TAG = BaseWorkoutFragment.class.getSimpleName();
    private ListView bWorkoutList;
    private String[] mItems = {"Novice","Intermediate","Advanced","Professional", "Example", "Example", "example"};
    private Integer[] imageId = {R.drawable.ic_logocenter,R.drawable.ic_logocenter,R.drawable.ic_logocenter,R.drawable.ic_logocenter,R.drawable.ic_logocenter,R.drawable.ic_logocenter,R.drawable.ic_logocenter};

    public CustomWorkoutFragment() {
        // TODO Auto-generated constructor stub
    }

    public static BaseWorkoutFragment newInstance() {
        return new BaseWorkoutFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_baseworkout, container, false);
        bWorkoutList = (ListView)v.findViewById(R.id.base_list);
        //bWorkoutList.setAdapter(new BaseWorkoutAdapter(getActivity(), mItems, imageId));
        return v;
    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        //TODO
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
