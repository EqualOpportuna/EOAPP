package com.example.equalopportuna;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ForumPostAdapter extends RecyclerView.Adapter<ForumPostAdapter.ForumPostViewHolder> {

    private final List<ForumPostNew> forumPosts;
    private final LayoutInflater inflater;
    private OnCommentButtonClickListener onCommentButtonClickListener;

    public interface OnCommentButtonClickListener {
        void onCommentButtonClick(int position, View view);
    }

    public void setOnCommentButtonClickListener(OnCommentButtonClickListener listener) {
        this.onCommentButtonClickListener = listener;
    }

    public ForumPostAdapter(Context context, List<ForumPostNew> forumPosts) {
        this.inflater = LayoutInflater.from(context);
        this.forumPosts = forumPosts;
    }

    @NonNull
    @Override
    public ForumPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.layout_storiesforum, parent, false);
        return new ForumPostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ForumPostViewHolder holder, int position) {
        ForumPostNew currentPost = forumPosts.get(position);
        holder.bind(currentPost);

        holder.cmtBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Trigger the listener callback
                if (onCommentButtonClickListener != null) {
                    onCommentButtonClickListener.onCommentButtonClick(position, view);
                }
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

        public ForumPostViewHolder(@NonNull View itemView) {
            super(itemView);
            usernameTextView = itemView.findViewById(R.id.TVforumUsername);
            messageTextView = itemView.findViewById(R.id.TVforumPost);
            cmtBTN = itemView.findViewById(R.id.cmtBTN);
        }

        public void bind(ForumPostNew post) {
            usernameTextView.setText(post.getUsername());
            messageTextView.setText(post.getMessage());
        }
    }


}
