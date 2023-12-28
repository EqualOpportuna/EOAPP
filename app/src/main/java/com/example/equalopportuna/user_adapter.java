package com.example.equalopportuna;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.equalopportuna.R;
import com.example.equalopportuna.Users;

import java.util.List;

public class user_adapter extends RecyclerView.Adapter<user_adapter.ViewHolder> {

    private final Context context;
    private final List<Users> userList;

    public user_adapter(Context context, List<Users> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_connections_users, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Users user = userList.get(position);

        // Set data to views
        holder.profilePic.setImageResource(user.getImageUrl());
        holder.username.setText(user.getUsername());
        holder.career_desc.setText(user.getCareerDescription());

        // set button handler
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView profilePic;
        TextView username, career_desc;
        Button BTNconnect;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profilePic = itemView.findViewById(R.id.profilePic);
            username = itemView.findViewById(R.id.username);
            career_desc = itemView.findViewById(R.id.career_desc);
            BTNconnect = itemView.findViewById(R.id.BTNconnect);
        }
    }
}
