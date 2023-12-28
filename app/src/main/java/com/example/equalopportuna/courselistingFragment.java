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

        List<String> courseNames = new ArrayList<>();
        List<String> courseDates = new ArrayList<>();
        List<String> courseFeedbacks = new ArrayList<>();
        Database database = new Database();
        Connection connection = database.SQLConnection();
        String query = "SELECT course_name, course_date, course_feedback FROM courses";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                // 获取每一行的数据
                String courseName = resultSet.getString("course_name");
                String courseDate = resultSet.getString("course_date");
                String courseFeedback = resultSet.getString("course_feedback");
                System.out.println(courseName);

                // 将数据存入相应的列表
                courseNames.add(courseName);
                courseDates.add(courseDate);
                courseFeedbacks.add(courseFeedback);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Placeholder data from res/values/strings.xml
        List<Model> ModelIs = createPlaceholderCourse(courseNames,courseDates,courseFeedbacks);



        adapter = new AA_recyclerViewAdapter(getActivity(), ModelIs);

        recyclerView.setAdapter(adapter);


        return view;
    }

    private List<Model> createPlaceholderCourse(List<String> courseNames, List<String> courseDates, List<String> courseFeedbacks) {
        List<Model> placeHolderCourse = new ArrayList<>();

        int minLength = Math.min(Math.min(courseNames.size(), courseDates.size()), courseFeedbacks.size());

        for (int i = 0; i < minLength; i++) {
            Model course = new Model(courseNames.get(i), courseDates.get(i), courseFeedbacks.get(i));
            placeHolderCourse.add(course);
        }

        return placeHolderCourse;
    }

}