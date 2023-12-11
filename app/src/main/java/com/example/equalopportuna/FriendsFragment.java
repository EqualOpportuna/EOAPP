package com.example.equalopportuna;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FriendsFragment extends Fragment {
    RecyclerView RecyclerFriends;

    public FriendsFragment() {
        // Required empty public constructor
    }

    public static FriendsFragment newInstance() {
        return new FriendsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_Friends, container, false);
        View view = inflater.inflate(R.layout.fragment_friends, container, false);

        RecyclerFriends = view.findViewById(R.id.RecyclerFriends);

        // Fetch job data from the static list
        List<Friends> FriendsList = Friends.getUserList();

        // RecyclerView adapter
        Friends_adapter adp = new Friends_adapter(requireContext(), FriendsList);
        RecyclerFriends.setAdapter(adp);
        RecyclerFriends.setLayoutManager(new LinearLayoutManager(requireContext()));

        return view;
    }
}