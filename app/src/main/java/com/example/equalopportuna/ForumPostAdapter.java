package com.example.equalopportuna;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ForumPostAdapter extends RecyclerView.Adapter<ForumPostAdapter.viewHolder>{

    String username[], forumPost[];
    Context context;

    ForumPostAdapter(Context ctx, String[] s1, String[] s2){
        context = ctx;
        username = s1;
        forumPost = s2;

    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater  = LayoutInflater.from(context);
        View ForumView = inflater.inflate(R.layout.activity_storiesforum_layout, parent, false);
        return new viewHolder(ForumView);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.username.setText(username[position]);
        holder.forumPost.setText(forumPost[position]);
    }

    @Override
    public int getItemCount() {

        return forumPost.length;
    }

    class viewHolder extends RecyclerView.ViewHolder{
        public TextView username, forumPost;
        public viewHolder(@NonNull View itemView){
            super (itemView);
            username = itemView.findViewById(R.id.TVforumUsername);
            forumPost = itemView.findViewById(R.id.TVforumPost);
        }
    }
}
