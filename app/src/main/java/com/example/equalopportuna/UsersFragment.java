package com.example.equalopportuna;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UsersFragment extends Fragment {
    RecyclerView RecyclerUsers;

    public UsersFragment() {
        // Required empty public constructor
    }

    public static UsersFragment newInstance() {
        return new UsersFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_users, container, false);
        View view = inflater.inflate(R.layout.fragment_users, container, false);

        RecyclerUsers = view.findViewById(R.id.RecyclerUser);

        // Fetch job data from the static list
        List<Users> UsersList = Users.getUserList();

        // RecyclerView adapter
        user_adapter adp = new user_adapter(requireContext(), UsersList);
        RecyclerUsers.setAdapter(adp);
        RecyclerUsers.setLayoutManager(new LinearLayoutManager(requireContext()));

        //GridLayoutManager -- arrange 2 items per row
        int spanCount = 2;
        GridLayoutManager layoutManager = new GridLayoutManager(requireContext(), spanCount);
        RecyclerUsers.setLayoutManager(layoutManager);

        return view;
    }
}