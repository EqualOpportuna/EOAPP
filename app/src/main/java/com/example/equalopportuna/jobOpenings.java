package com.example.equalopportuna;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class jobOpenings extends AppCompatActivity {

    RecyclerView RecycleJobPosts;

    String s1[], s2[], s3[];
    int images[] = {R.drawable.logo1, R.drawable.logo2, R.drawable.logo3, R.drawable.logo4, R.drawable.um_logo,
            R.drawable.logo1, R.drawable.logo2, R.drawable.logo3, R.drawable.logo4, R.drawable.um_logo};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_openings);

        RecycleJobPosts = findViewById(R.id.RecycleJobPosts);
        s1=getResources().getStringArray(R.array.job_titles);
        s2=getResources().getStringArray(R.array.company_name);
        s3=getResources().getStringArray(R.array.job_location);

        adapter adp = new adapter(this, s1, s2, s3, images);
        RecycleJobPosts.setAdapter(adp);
        RecycleJobPosts.setLayoutManager(new LinearLayoutManager(this));


    }
}

