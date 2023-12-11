// StoriesForum.java
package com.example.equalopportuna;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StoriesForum extends AppCompatActivity {

    private ForumPostAdapter adapter;
    private StoryManager storyManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stories_forum);

        StrictMode.ThreadPolicy tp = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(tp);

        storyManager = StoryManager.getInstance(getApplicationContext());

        RecyclerView recyclerView = findViewById(R.id.ForumRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<ForumPostNew> forumPosts = StoryManager.getStoryList();

        adapter = new ForumPostAdapter(this, forumPosts);
        recyclerView.setAdapter(adapter);
    }
}
