package com.gym8.messages;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import java.util.List;
import com.gym8.main.R;
import com.parse.ParseQuery;
import com.parse.ParseUser;

/**
 * Created by Jai Nalwa on 4/10/15.
 */
public class MessagesFragment extends Fragment {
    private static ParseUser selectedUser;
    private ListView listView;
    private View v;
    private MessagesAdapter adapter;

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
        getChatUsers();
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
                MessagesFragment.selectedUser = (ParseUser) listView.getItemAtPosition(position);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.add(R.id.frag_messages, new ChatFragment());
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
    }

    public void onResume() {
        super.onResume();
    }

    private void chatUsersRetrieved() {
        adapter = new MessagesAdapter(getActivity().getApplicationContext());
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void getChatUsers() {
        if (ChatMessaging.isChatRetrieved() == false) {
            ParseQuery<ParseObject> query = ParseQuery.getQuery("ChatUsers");
            query.fromLocalDatastore();
            query.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> chatUsers,
                                 ParseException e) {
                    if (e == null) {
                        try {
                            for (int n = 0; n < chatUsers.size(); n++) {
                                chatUsers.get(n).getParseObject("userId").fetchFromLocalDatastore();
                                ChatMessaging.addToChatUserDetails(chatUsers.get(n).getParseUser("userId"));
                            }
                            ChatMessaging.setChatUsers(chatUsers);
                            ChatMessaging.setChatRetrieved(true);
                        } catch (ParseException e2) {
                            Toast.makeText(getActivity(), "Connection error", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Connection error", Toast.LENGTH_SHORT).show();
                    }
                    chatUsersRetrieved();
                }
            });
        } else {
            chatUsersRetrieved();
        }
    }

    static ParseUser getSelectedUser() {
        return MessagesFragment.selectedUser;
    }
}
