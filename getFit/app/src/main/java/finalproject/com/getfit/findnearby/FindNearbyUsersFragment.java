package finalproject.com.getfit.findnearby;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseObject;
import com.parse.ParseQueryAdapter;
import android.widget.AdapterView.OnItemClickListener;
import finalproject.com.getfit.R;
import finalproject.com.getfit.viewpager.RootFragment;

/**
 * Created by Gurumukh on 3/6/15.
 */
public class FindNearbyUsersFragment extends RootFragment {

    private ParseQueryAdapter<ParseObject> mainAdapter;
    private FindNearbyUsersAdapter nearbyUsersAdapter;
    private GridView gridView;
    static final String[] numbers = new String[] {
            "A", "B", "C", "D", "E",
            "F", "G", "H", "I", "J",
            "K", "L", "M", "N", "O",
            "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z"};


    private View v;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_find_nearby_users, container, false);
        gridView = (GridView) v.findViewById(R.id.find_nearby_grid);
        nearbyUsersAdapter = new FindNearbyUsersAdapter(getActivity().getApplicationContext());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),
                android.R.layout.simple_list_item_1,numbers);

        gridView.setAdapter(adapter);
        nearbyUsersAdapter.loadObjects();
        gridView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                //TODO

                }
            });
        return v;
    }

}
