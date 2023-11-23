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
        List<ForumPost> forumPosts = createPlaceholderData();

        adapter = new ForumPostAdapter(this, forumPosts);
        recyclerView.setAdapter(adapter);
    }

    private List<ForumPost> createPlaceholderData() {
        List<ForumPost> placeholderData = new ArrayList<>();
        String[] usernames = getResources().getStringArray(R.array.TVforumUsername);
        String[] messages = getResources().getStringArray(R.array.TVforumPost);

        for (int i = 0; i < usernames.length && i < messages.length; i++) {
            ForumPost post = new ForumPost(usernames[i], messages[i]);
            placeholderData.add(post);
        }

        return placeholderData;
    }
}


class ForumPost {
    private String username;
    private String message;

    public ForumPost(String username, String message) {
        this.username = username;
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public String getMessage() {
        return message;
    }
}

