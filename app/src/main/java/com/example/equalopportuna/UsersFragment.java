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
        View view = inflater.inflate(R.layout.fragment_users, container, false);

        RecyclerUsers = view.findViewById(R.id.RecyclerUser);

        // Use the stored list of users
        List<Users> UsersList = Users.getAllUsers();

        // RecyclerView adapter
        user_adapter adp = new user_adapter(requireContext(), UsersList);
        RecyclerUsers.setAdapter(adp);

        // Use either LinearLayoutManager or GridLayoutManager based on your preference
        RecyclerUsers.setLayoutManager(new LinearLayoutManager(requireContext()));
        // or
        int spanCount = 2;
        GridLayoutManager layoutManager = new GridLayoutManager(requireContext(), spanCount);
        RecyclerUsers.setLayoutManager(layoutManager);

        return view;
    }

}