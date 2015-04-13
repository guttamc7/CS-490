package finalproject.com.getfit.messages;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

import finalproject.com.getfit.R;
import finalproject.com.getfit.baseworkout.BaseWorkoutAdapter;
import finalproject.com.getfit.baseworkout.BaseWorkoutFragment;


/**
 * Created by Jai Nalwa on 4/10/15.
 */
public class MessagesFragment extends Fragment {


    private List<ParseObject> messagesList = new ArrayList<>();
    private ListView listView;
    View v;
    public static String webLink;
    private Context mContext;
    private BaseWorkoutAdapter adapter;

    public MessagesFragment() {
        // Auto-generated constructor stub
    }

    public static MessagesFragment newInstance() {
        return new MessagesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_messages, container, false);
        listView = (ListView) v.findViewById(R.id.messages_list);
        return v;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //TODO
                ParseObject message = (ParseObject)listView.getItemAtPosition(position);
                FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                //ft.replace(R.id.frag_messages, new BaseWorkoutDetailsFragment());
                //ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                //ft.addToBackStack(null);
                //ft.commit();
            }
        });
    }

    public void onResume() {
        super.onResume();

    }
}
