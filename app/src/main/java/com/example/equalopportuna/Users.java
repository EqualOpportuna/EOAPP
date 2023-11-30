package com.example.equalopportuna;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import java.util.List;public class Users extends Fragment {
    RecyclerView RecyclerUser;

    public Users() {
        // Required empty public constructor
    }

    public static Users newInstance() {
        return new Users();
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

        // Fetch job data from the static list
        List<Job> jobList = Job.getJobList();

        // Set up RecyclerView adapter
        JobPost_adapter adp = new JobPost_adapter(requireContext(), jobList);
        RecyclerUser.setAdapter(adp);
        RecyclerUser.setLayoutManager(new LinearLayoutManager(requireContext()));

        return view;
    }
}