package com.gym8.messages;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.gym8.main.R;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gurumukh on 4/13/15.
 */
public class ChatFragment extends Fragment {

    private ListView listView;
    private ArrayList<ParseObject> chatList = new ArrayList<>();
    private View v;
    private ChatAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_chat, container, false);
        listView = (ListView) v.findViewById(R.id.chat_list);
        return v;
    }

    public ChatFragment() {
        // Auto-generated constructor stub
    }

    public static ChatFragment newInstance() {
        return new ChatFragment();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ChatAdapter(getActivity().getApplicationContext(), chatList);
        listView.setAdapter(adapter);
    }

}
