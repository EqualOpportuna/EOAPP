package com.example.equalopportuna;

import android.content.Intent;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class courselistingFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private AA_recyclerViewAdapter adapter;

    private FirebaseDatabase db;
    private DatabaseReference coursesRef;

    private UserManager userManager;




    public courselistingFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_courselisting, container, false);
        // Inflate the layout for this fragment
        userManager = UserManager.getInstance(requireContext());

        RecyclerView recyclerView = view.findViewById(R.id.CourseRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        db = FirebaseDatabase.getInstance("https://equalopportunaapp-default-rtdb.asia-southeast1.firebasedatabase.app");
        coursesRef = db.getReference("courses");
        coursesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> courseNames = new ArrayList<>();
                List<String> courseDates = new ArrayList<>();
                List<String> courseFeedbacks = new ArrayList<>();
                List<String> courseLinks = new ArrayList<>();
                List<String> courseDescriptions = new ArrayList<>();
                List<String> recommendedCourses = new ArrayList<>();

                for (DataSnapshot courseSnapshot : dataSnapshot.getChildren()) {
                    // For each course, get values for name, date, and description
                    String name = courseSnapshot.child("name").getValue(String.class);
                    String date = courseSnapshot.child("date").getValue(String.class);
                    String description = courseSnapshot.child("description").getValue(String.class);
                    String feedback = courseSnapshot.child("feedback").getValue(String.class);
                    String link = courseSnapshot.child("link").getValue(String.class);
                    String recommended = courseSnapshot.child("recommend").getValue(String.class);

                    // Add values to the respective lists
                    courseNames.add(name);
                    courseDates.add(date);
                    courseFeedbacks.add(feedback);
                    courseLinks.add(link);
                    courseDescriptions.add(description);
                    recommendedCourses.add(recommended);
                }

                // Create placeholder courses using the fetched data
                List<Model> modelList = createPlaceholderCourse(courseNames, courseDates, courseFeedbacks, courseLinks, courseDescriptions, recommendedCourses);

                adapter = new AA_recyclerViewAdapter(getActivity(), modelList, userManager.getFullName());

                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
                Toast.makeText(getActivity(), "Failed to fetch data from Firebase", Toast.LENGTH_SHORT).show();
            }
        });



        return view;
    }

    private List<Model> createPlaceholderCourse(List<String> courseNames, List<String> courseDates, List<String> courseFeedbacks, List<String> courseLinks, List<String> courseDescriptions, List<String> recommendedCourses) {
        List<Model> placeHolderCourse = new ArrayList<>();


        for (int i = 0; i < courseNames.size(); i++) {
            Model course = new Model(courseNames.get(i), courseDates.get(i), courseFeedbacks.get(i), courseLinks.get(i), courseDescriptions.get(i), recommendedCourses.get(i));
            placeHolderCourse.add(course);
        }

        return placeHolderCourse;
    }

}