package com.example.equalopportuna;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
public class StoriesForum extends AppCompatActivity {

    RecyclerView ForumRecycler;
    String s1[], s2[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stories_forum);

        ForumRecycler = findViewById(R.id.ForumRecycler);
        s1=getResources().getStringArray(R.array.TVforumUsername);
        s2=getResources().getStringArray(R.array.TVforumPost);

        ForumPostAdapter Fadp = new ForumPostAdapter(this, s1, s2);
        ForumRecycler.setAdapter(Fadp);
        ForumRecycler.setLayoutManager(new LinearLayoutManager(this));

    }




}