package com.example.equalopportuna;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Connection;
import java.util.List;

public class StoriesForumFragment extends Fragment implements ForumPostAdapter.OnCommentButtonClickListener {

    private ForumPostAdapter adapter;
    private StoryManager storyManager;
    private UserManager userManager;
    private List<ForumPostNew> forumPosts;

    private FirebaseDatabase db;
    private DatabaseReference storiesRef;

    public StoriesForumFragment() {
        // Required empty public constructor
    }
    public void setForumPosts(List<ForumPostNew> forumPosts) {
        this.forumPosts = forumPosts;
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

        userManager = UserManager.getInstance(requireContext());

        storyManager = StoryManager.getInstance(getContext());

        RecyclerView recyclerView = view.findViewById(R.id.ForumRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        List<ForumPostNew> forumPosts = StoryManager.getStoryList();

        adapter = new ForumPostAdapter(getActivity(), forumPosts, userManager.getFullName());
        adapter.setOnCommentButtonClickListener(this); // Set the listener
        recyclerView.setAdapter(adapter);

        adapter.setOnCommentButtonClickListener(this); // Set the listener

        db = FirebaseDatabase.getInstance("https://equalopportunaapp-default-rtdb.asia-southeast1.firebasedatabase.app");
        storiesRef = db.getReference("stories");


        Button btnSend = view.findViewById(R.id.BTNsend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Insert a new story into the database
                Database database = new Database();
                Connection connection = database.SQLConnection();
                insertStory();
                storyManager.fetchAndStoreStoryData(requireContext(), connection);


                // Notify the adapter that the data has changed
                adapter.notifyDataSetChanged();
            }
        });





        return view;
    }

    public void insertStory() {
        // Get the message from the EditText
        EditText etMessage = requireView().findViewById(R.id.ETMessage);
        String message = etMessage.getText().toString().trim();

        // Insert the story into the database
        if (!message.isEmpty()) {
            Database database = new Database();
            String username = userManager.getFullName(); // Assuming you have userManager initialized
            database.insertStory(username, message);

            // Clear the EditText after sending the story
            etMessage.getText().clear();
            storiesRef.child(username).child(message).child("likes").setValue(0);

            // You may also want to update your RecyclerView with the new data
            // Assuming StoryManager.getStoryList() gets the updated list of stories
            List<ForumPostNew> updatedForumPosts = StoryManager.getStoryList();
            adapter.setForumPosts(updatedForumPosts, username);
            adapter.notifyDataSetChanged();
        }
    }


}
