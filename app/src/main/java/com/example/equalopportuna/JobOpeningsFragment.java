package com.example.equalopportuna;

import android.content.Intent;
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

public class JobOpeningsFragment extends Fragment {

    RecyclerView recycleJobPosts;

    public JobOpeningsFragment() {
        // Required empty public constructor
    }

    public static JobOpeningsFragment newInstance() {
        return new JobOpeningsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_job_openings, container, false);

        recycleJobPosts = view.findViewById(R.id.RecycleJobPosts);

        // Sample data
        String[] jobTitles = getResources().getStringArray(R.array.job_titles);
        String[] companyNames = getResources().getStringArray(R.array.company_name);
        String[] jobLocations = getResources().getStringArray(R.array.job_location);
        int[] images = {R.drawable.logo1, R.drawable.logo2, R.drawable.logo3, R.drawable.logo4, R.drawable.um_logo,
                R.drawable.logo1, R.drawable.logo2, R.drawable.logo3, R.drawable.logo4, R.drawable.um_logo};

        // Set up RecyclerView adapter
        JobPost_adapter adp = new JobPost_adapter(requireContext(), jobTitles, companyNames, jobLocations, images);
        recycleJobPosts.setAdapter(adp);
        recycleJobPosts.setLayoutManager(new LinearLayoutManager(requireContext()));



        return view;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        Button btnCreatJob = view.findViewById(R.id.btnCreateJob);
        View.OnClickListener OCLCreatJob = new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Navigation.findNavController(view).navigate(R.id.createJobFormFragment);
            }
        };
        btnCreatJob.setOnClickListener(OCLCreatJob);


    }


}
