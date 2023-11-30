package com.example.equalopportuna;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class courseCardView extends AppCompatActivity {
    private AA_recyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_courselisting);

        RecyclerView recyclerView = findViewById(R.id.CourseRecycler);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Model> course = createPlaceholderData();

        adapter = new AA_recyclerViewAdapter(this, course);
        recyclerView.setAdapter(adapter);


    }

    private List<Model> createPlaceholderData() {
        List<Model> placeHolderCourse = new ArrayList<>();
        String [] courseName = getResources().getStringArray(R.array.TVcourseName);
        String [] courseDate = getResources().getStringArray(R.array.TVcourseDate);
        String [] courseFeedback = getResources().getStringArray(R.array.TVcourseFeedback);

        for (int i = 0; i < courseName.length &&
                i < courseDate.length &&
                i < courseFeedback.length ; i++) {
            Model course = new Model(courseName[i], courseDate[i], courseFeedback[i]);
            placeHolderCourse.add(course);

        }

        return placeHolderCourse;
    }



}
