package com.example.equalopportuna;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StoriesForumFragment extends Fragment implements ForumPostAdapter.OnCommentButtonClickListener {

    private ForumPostAdapter adapter;
    private StoryManager storyManager;

    public StoriesForumFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCommentButtonClick(int position, View view) {
        ForumPostNew currentPost = adapter.getItem(position);

        // Create a Bundle to pass data to the fragment
        Bundle bundle = new Bundle();
        bundle.putString("username", currentPost.getUsername());
        bundle.putString("message", currentPost.getMessage());

        // Navigate to StoriesCommentFragment and pass the Bundle
        NavController navController = NavHostFragment.findNavController(this);
        navController.navigate(R.id.storiesCommentFragment, bundle);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stories_forum, container, false);

        storyManager = StoryManager.getInstance(getContext());

        RecyclerView recyclerView = view.findViewById(R.id.ForumRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        List<ForumPostNew> forumPosts = StoryManager.getStoryList();

        adapter = new ForumPostAdapter(getActivity(), forumPosts);
        adapter.setOnCommentButtonClickListener(this); // Set the listener
        recyclerView.setAdapter(adapter);

        return view;
    }
}
