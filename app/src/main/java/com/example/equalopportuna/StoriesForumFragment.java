package com.example.equalopportuna;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
                if(insertStory()){
                storyManager.fetchAndStoreStoryData(requireContext(), connection);


                // Notify the adapter that the data has changed
                adapter.notifyDataSetChanged();
            }}
        });





        return view;
    }

    public boolean insertStory() {
        // Get the message from the EditText
        EditText etMessage = requireView().findViewById(R.id.ETMessage);
        String message = etMessage.getText().toString().trim();

        // Check if the message length is more than 250 characters
        if (message.length() > 250) {
            // Show a toast or dialog indicating that the message is too long
            Toast.makeText(requireContext(), "Message is too long (max 250 characters)", Toast.LENGTH_SHORT).show();
            return false; // Do not continue if the message is too long
        }

        // Replace invalid characters in the message with spaces
        message = message.replaceAll("[.#$\\[\\]]", " ");

        // Insert the story into the database
        if (!message.isEmpty()) {
            Database database = new Database();
            String username = userManager.getFullName(); // Assuming you have userManager initialized
            database.insertStory(username, message);

            // Clear the EditText after sending the story
            etMessage.getText().clear();
            DatabaseReference userStoriesRef = storiesRef.child(username).push();

            userStoriesRef.child("message").setValue(message);
            userStoriesRef.child("likes").setValue(0);

            // You may also want to update your RecyclerView with the new data
            // Assuming StoryManager.getStoryList() gets the updated list of stories
            List<ForumPostNew> updatedForumPosts = StoryManager.getStoryList();
            adapter.setForumPosts(updatedForumPosts, username);
            adapter.notifyDataSetChanged();
            return true;
        }
        return false;
    }




}
