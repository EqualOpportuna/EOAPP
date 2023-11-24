package com.example.equalopportuna;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class StoriesForum extends AppCompatActivity {

    private ForumPostAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stories_forum);

        RecyclerView recyclerView = findViewById(R.id.ForumRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Placeholder data from res/values/strings.xml
        List<ForumPostNew> forumPosts = createPlaceholderData();

        adapter = new ForumPostAdapter(this, forumPosts);
        recyclerView.setAdapter(adapter);
    }

    private List<ForumPostNew> createPlaceholderData() {
        List<ForumPostNew> placeholderData = new ArrayList<>();
        String[] usernames = getResources().getStringArray(R.array.TVforumUsername);
        String[] messages = getResources().getStringArray(R.array.TVforumPost);

        for (int i = 0; i < usernames.length && i < messages.length; i++) {
            ForumPostNew post = new ForumPostNew(usernames[i], messages[i]);
            placeholderData.add(post);
        }

        return placeholderData;
    }
}


