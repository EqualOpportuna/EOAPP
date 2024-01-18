package com.example.equalopportuna;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UsersFragment extends Fragment {
    RecyclerView RecyclerUsers;

    private UserManager userManager;

    public UsersFragment() {
        // Required empty public constructor
    }

    public static UsersFragment newInstance() {
        return new UsersFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_users, container, false);

        RecyclerUsers = view.findViewById(R.id.RecyclerUser);

        userManager = UserManager.getInstance(requireContext());

        String loggedInUser = userManager.getFullName();


        // Use the stored list of users
        List<Users> UsersList = Users.getAllUsers();
        for(int i = 0; i < UsersList.size(); i ++) {
            System.out.println(UsersList.get(i).getUsername());
        }

        // RecyclerView adapter
        user_adapter adp = new user_adapter(requireContext(), UsersList, loggedInUser);
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