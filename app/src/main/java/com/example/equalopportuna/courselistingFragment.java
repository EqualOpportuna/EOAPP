package com.example.equalopportuna;

import android.content.Intent;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class courselistingFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private AA_recyclerViewAdapter adapter;




    public courselistingFragment() {
        // Required empty public constructor
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_courselisting, container, false);
        // Inflate the layout for this fragment
        RecyclerView recyclerView = view.findViewById(R.id.CourseRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Placeholder data from res/values/strings.xml
        List<Model> ModelIs = createPlaceholderCourse();

        adapter = new AA_recyclerViewAdapter(getActivity(), ModelIs);
        recyclerView.setAdapter(adapter);


        return view;
    }

    private List<Model> createPlaceholderCourse() {
        List<Model> placeHolderCourse = new ArrayList<>();
        String[] courseName = getResources().getStringArray(R.array.TVcourseName);
        String[] courseDate = getResources().getStringArray(R.array.TVcourseDate);
        String[] courseFeedback = getResources().getStringArray(R.array.TVcourseFeedback);

        for (int i = 0; i < courseName.length &&
                i < courseDate.length &&
                i < courseFeedback.length; i++) {
            Model course = new Model(courseName[i], courseDate[i], courseFeedback[i]);
            placeHolderCourse.add(course);
        }

        return placeHolderCourse;


    }}