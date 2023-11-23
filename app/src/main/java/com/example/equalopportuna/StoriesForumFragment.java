package com.example.equalopportuna;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class StoriesForumFragment extends Fragment {

    private ForumPostAdapter adapter;

    public StoriesForumFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stories_forum, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.ForumRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Placeholder data from res/values/strings.xml
        List<ForumPostNew> forumPosts = createPlaceholderData();

        adapter = new ForumPostAdapter(getActivity(), forumPosts);
        recyclerView.setAdapter(adapter);

        return view;
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
