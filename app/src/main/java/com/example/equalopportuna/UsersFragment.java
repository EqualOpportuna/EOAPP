package com.example.equalopportuna;

//import static com.example.equalopportuna.Users.userList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class UsersFragment extends Fragment {
    RecyclerView RecyclerUser;

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

        RecyclerUser = view.findViewById(R.id.RecyclerUser);

        // Fetch user data from the static list
        List<Users> userList = Users.getUserList();


        // Set up RecyclerView adapter
        user_adapter adp = new user_adapter(requireContext(), userList);
        RecyclerUser.setAdapter(adp);
        RecyclerUser.setLayoutManager(new LinearLayoutManager(requireContext()));

        return view;
    }
}