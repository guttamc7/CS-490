package finalproject.com.getfit.findnearby;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
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
    private View v;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_find_nearby_users, container, false);
        gridView = (GridView) v.findViewById(R.id.find_nearby_grid);
        nearbyUsersAdapter = new FindNearbyUsersAdapter(getActivity().getApplicationContext());
        gridView.setAdapter(nearbyUsersAdapter);
        nearbyUsersAdapter.loadObjects();
        gridView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                ft.replace(R.id.findnearby_user_frag, new FindNearbyUserProfile());
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.addToBackStack(null);
                ft.commit();

                }
            });
        return v;
    }

}
