package com.example.equalopportuna;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class StoriesCommentFragment extends Fragment {

    private String username;
    private String message;

    private FirebaseDatabase db;
    private DatabaseReference usersRef;

    private EditText etMessage;
    private Button btnSend;

    private UserManager userManager;

    private CommentsAdapter commentsAdapter;
    private RecyclerView recyclerView;


    public StoriesCommentFragment() {
        // Required empty public constructor
    }

    public static StoriesCommentFragment newInstance(String param1, String param2) {
        StoriesCommentFragment fragment = new StoriesCommentFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseDatabase.getInstance("https://equalopportunaapp-default-rtdb.asia-southeast1.firebasedatabase.app");
        usersRef = db.getReference("comments");
        if (getArguments() != null) {
            username = getArguments().getString("username");
            message = getArguments().getString("message");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stories_comment, container, false);

        // Set the retrieved username to the TextView
        setUsername(view, username);
        setMessage(view, message);

        userManager = UserManager.getInstance(requireContext());

        etMessage = view.findViewById(R.id.ETMessage);
        btnSend = view.findViewById(R.id.BTNsend);

        recyclerView = view.findViewById(R.id.rvComments);

        // Initialize the adapter and set it to the RecyclerView
        commentsAdapter = new CommentsAdapter(new ArrayList<>());
        recyclerView.setAdapter(commentsAdapter);

        loadCommentsFromFirebase();


        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessageToFirebase();
            }
        });

        return view;
    }


    public void setUsername(View view, String username) {
        TextView tvUsername = view.findViewById(R.id.TVUsername);
        if (tvUsername != null) {
            tvUsername.setText(username);
        }
    }

    public void setMessage(View view, String message){
        TextView tvStory = view.findViewById(R.id.TVStory);
        if(tvStory != null){
            tvStory.setText(message);
        }
    }


    private void sendMessageToFirebase() {
        String comment = etMessage.getText().toString().trim();

        if (!comment.isEmpty()) {
            // Assuming you have the username and storyMessage already set

            // Use a random username for the commenter (you can replace this with the actual logic)
            String commenterUsername = userManager.getFullName();

            // Generate a unique comment ID
            String commentId = usersRef.child(username).child(message).push().getKey();

            // Create a new node under "comments" using the generated comment ID
            DatabaseReference commentsRef = usersRef.child(username).child(message).child(commentId);

            // Set the comment message
            commentsRef.child("commenterUsername").setValue(commenterUsername);
            commentsRef.child("comment").setValue(comment);

            Comments newComment = new Comments(commenterUsername, comment);

            // Add the new comment to your RecyclerView
            commentsAdapter.getComments().add(newComment);
            commentsAdapter.notifyItemInserted(commentsAdapter.getComments().size() - 1);

            // Clear the EditText after sending the comment
            etMessage.getText().clear();
        }
    }
    private void loadCommentsFromFirebase() {
        // Assuming you have the username and message already set
        DatabaseReference commentsRef = usersRef.child(username).child(message);

        commentsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                commentsAdapter.getComments().clear(); // Clear existing comments
                for (DataSnapshot commentSnapshot : dataSnapshot.getChildren()) {
                    String commenterUsername = commentSnapshot.child("commenterUsername").getValue(String.class);
                    String comment = commentSnapshot.child("comment").getValue(String.class);

                    Comments loadedComment = new Comments(commenterUsername, comment);

                    // Add loaded comments to the adapter
                    commentsAdapter.getComments().add(loadedComment);
                }
                commentsAdapter.notifyDataSetChanged(); // Notify adapter about data change
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }

}
