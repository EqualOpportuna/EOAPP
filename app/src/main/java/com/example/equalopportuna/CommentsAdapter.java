package com.example.equalopportuna;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentViewHolder> {

    private List<Comments> comments;

    public CommentsAdapter(List<Comments> comments) {
        this.comments = comments;
    }

    public List<Comments> getComments() {
        return comments;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_comments, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comments comment = comments.get(position);
        holder.bind(comment);
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        private TextView tvForumUsername;
        private TextView tvForumPost;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvForumUsername = itemView.findViewById(R.id.TVforumUsername);
            tvForumPost = itemView.findViewById(R.id.TVforumPost);
        }

        public void bind(Comments comment) {
            tvForumUsername.setText(comment.getUsername());
            tvForumPost.setText(comment.getMessage());
        }
    }

}
