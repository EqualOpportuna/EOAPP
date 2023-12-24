package com.example.equalopportuna;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ForumPostAdapter extends RecyclerView.Adapter<ForumPostAdapter.ForumPostViewHolder> {

    private List<ForumPostNew> forumPosts;


    private final LayoutInflater inflater;
    private OnCommentButtonClickListener onCommentButtonClickListener;

    private FirebaseDatabase db;
    private DatabaseReference storiesRef;

    private String username;

    public interface OnCommentButtonClickListener {
        void onCommentButtonClick(int position, View view);
    }

    public void setForumPosts(List<ForumPostNew> forumPosts, String username) {
        this.forumPosts = forumPosts;
        this.username = username;


    }
    public void setOnCommentButtonClickListener(OnCommentButtonClickListener listener) {
        this.onCommentButtonClickListener = listener;
    }

    public ForumPostAdapter(Context context, List<ForumPostNew> forumPosts) {
        this.inflater = LayoutInflater.from(context);
        this.forumPosts = forumPosts;

        db = FirebaseDatabase.getInstance("https://equalopportunaapp-default-rtdb.asia-southeast1.firebasedatabase.app");
        storiesRef = db.getReference("stories");
    }
    public ForumPostAdapter(Context context, List<ForumPostNew> forumPosts, String username) {
        this.inflater = LayoutInflater.from(context);
        this.forumPosts = forumPosts;
        this.username = username;

        db = FirebaseDatabase.getInstance("https://equalopportunaapp-default-rtdb.asia-southeast1.firebasedatabase.app");
        storiesRef = db.getReference("stories");
    }


    @NonNull
    @Override
    public ForumPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.layout_storiesforum, parent, false);
        return new ForumPostViewHolder(view);
    }
    public ForumPostNew getItem(int position) {
        return forumPosts.get(position);
    }

    @Override
    public void onBindViewHolder(@NonNull ForumPostViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ForumPostNew currentPost = forumPosts.get(position);
        holder.bind(currentPost);
        fetchLikesCount(holder, currentPost);


        holder.cmtBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Trigger the listener callback
                if (onCommentButtonClickListener != null) {
                    onCommentButtonClickListener.onCommentButtonClick(position, view);
                }
            }
        });

        holder.downloadBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Call downloadStory with the context from itemView
                downloadStory(position, holder.itemView.getContext());
            }
        });

        holder.likeBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleLikeOnClick(holder, position);
            }
        });


    }

    private void fetchLikesCount(ForumPostViewHolder holder, ForumPostNew post) {
        DatabaseReference likesRef = storiesRef
                .child(post.getUsername())
                .child(post.getMessage())
                .child("likes");

        likesRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult().getValue() != null) {
                long likesCount = (long) task.getResult().getValue();

                // Update the likes count in the TextView directly
                holder.numberOfLikes.setText(String.valueOf(likesCount));
            }
        });
    }


    @Override
    public int getItemCount() {
        return forumPosts.size();
    }

    static class ForumPostViewHolder extends RecyclerView.ViewHolder {

        private final TextView usernameTextView;
        private final TextView messageTextView;
        private final TextView cmtBTN;
        private final TextView downloadBTN;
        private final TextView likeBTN;

        private final TextView numberOfLikes;


        public ForumPostViewHolder(@NonNull View itemView) {
            super(itemView);
            usernameTextView = itemView.findViewById(R.id.TVforumUsername);
            messageTextView = itemView.findViewById(R.id.TVforumPost);
            cmtBTN = itemView.findViewById(R.id.cmtBTN);
            downloadBTN = itemView.findViewById(R.id.downloadBTN);
            likeBTN = itemView.findViewById(R.id.likeBTN);
            numberOfLikes = itemView.findViewById(R.id.numberOfLikes);
        }

        public void bind(ForumPostNew post) {
            usernameTextView.setText(post.getUsername());
            messageTextView.setText(post.getMessage());
        }
    }

    private void downloadStory(int position, Context context) {
        ForumPostNew currentPost = forumPosts.get(position);

        // Get the data to be included in the PDF
        String publisher = currentPost.getUsername();
        String story = currentPost.getMessage();

        // Create a PDF document
        createPdfDocument(context, publisher, story);
    }

    private void createPdfDocument(Context context, String publisher, String story) {
        PdfDocument pdfDocument = new PdfDocument();

        // Create a page
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(300, 500, 1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);

        // Get the Canvas object and set the content
        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(12);

        // Add content to the PDF
        canvas.drawText("Publisher: " + publisher, 50, 50, paint);
        canvas.drawText("Story: " + story, 50, 70, paint);

        // Finish the page
        pdfDocument.finishPage(page);

        // Generate a unique filename based on the current timestamp
        String timestamp = String.valueOf(System.currentTimeMillis());
        String fileName = "story_" + timestamp + ".pdf";

        // Define the file path where the PDF will be saved in the "Downloads" folder
        String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + fileName;

        try {
            // Save the document to the specified file path
            FileOutputStream fos = new FileOutputStream(filePath);
            pdfDocument.writeTo(fos);
            pdfDocument.close();
            fos.close();

            // Display a Toast to notify the user that the story has been downloaded
            Toast.makeText(context, "Story Downloaded: " + filePath, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleLikeOnClick(ForumPostViewHolder holder, int position) {
        ForumPostNew currentPost = forumPosts.get(position);

        if (currentPost == null || currentPost.getUsername() == null || currentPost.getMessage() == null) {
            // Handle the case where currentPost or its properties are null
            System.out.println("Error: currentPost or its properties are null");
            return;
        }

        String currentUser = username; // Replace with your logic to get the current user's username

        if (currentUser == null) {
            // Handle the case where currentUser is null
            System.out.println("Error: currentUser is null");
            return;
        }

        DatabaseReference userRef = storiesRef.child(currentPost.getUsername());
        System.out.println("1");

        if (userRef == null) {
            // Handle the case where userRef is null
            System.out.println("Error: userRef is null");
            return;
        }

        DatabaseReference postRef = userRef.child(currentPost.getMessage());
        System.out.println("2");

        if (postRef == null) {
            // Handle the case where postRef is null
            System.out.println("Error: postRef is null");
            return;
        }

        DatabaseReference likesRef = postRef.child("likes");
        System.out.println("3");
        DatabaseReference likesFromUsersRef = postRef.child("likesFromUsers");
        System.out.println("4");

        if (likesRef == null || likesFromUsersRef == null) {
            // Handle the case where likesRef or likesFromUsersRef is null
            System.out.println("Error: likesRef or likesFromUsersRef is null");
            return;
        }

        // Check if the current user has already liked the post
        likesFromUsersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                System.out.println("5");
                if (dataSnapshot.getValue() != null && dataSnapshot.child(currentUser).exists()) {
                    // User has already liked the post, remove the like
                    System.out.println("User has already liked the post, removing the like");
                    likesFromUsersRef.child(currentUser).removeValue();

                    // Update the likes count in Firebase
                    likesRef.get().addOnCompleteListener(task1 -> {
                        if (task1.isSuccessful() && task1.getResult() != null) {
                            long likesCount = (long) task1.getResult().getValue();

                            // Update the likes count in Firebase
                            postRef.child("likes").setValue(likesCount - 1);

                            // Update the likes count in the TextView
                            holder.numberOfLikes.setText(String.valueOf(likesCount - 1));
                            System.out.println("Like removed successfully");
                        }
                    });
                } else {
                    // User hasn't liked the post, add the like
                    System.out.println("User hasn't liked the post, adding the like");
                    likesFromUsersRef.child(currentUser).setValue(true);

                    // Update the likes count in Firebase
                    likesRef.get().addOnCompleteListener(task1 -> {
                        if (task1.isSuccessful() && task1.getResult() != null) {
                            long likesCount = (long) task1.getResult().getValue();

                            // Update the likes count in Firebase
                            postRef.child("likes").setValue(likesCount + 1);

                            // Update the likes count in the TextView
                            holder.numberOfLikes.setText(String.valueOf(likesCount + 1));
                            System.out.println("Like added successfully");
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("Error: Database operation canceled");
            }
        });
    }




}
