package com.example.equalopportuna;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import java.util.List;public class chat extends Fragment {
    RecyclerView RecyclerChat;

    public chat() {
        // Required empty public constructor
    }

    public static chat newInstance() {
        return new chat();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_chat, container, false);
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        RecyclerChat = view.findViewById(R.id.RecyclerChat);

        // Fetch job data from the static list
        List<Job> jobList = Job.getJobList();

        // Set up RecyclerView adapter
        JobPost_adapter adp = new JobPost_adapter(requireContext(), jobList);
        RecyclerChat.setAdapter(adp);
        RecyclerChat.setLayoutManager(new LinearLayoutManager(requireContext()));

        return view;
    }
}