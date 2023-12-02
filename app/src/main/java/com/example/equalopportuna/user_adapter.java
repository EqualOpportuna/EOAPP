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

import java.util.List;

public class user_adapter extends RecyclerView.Adapter<user_adapter.ViewHolder> {

    List<Users> userList;
    Context context;

    user_adapter(Context ctx, List<Users> users) {
        context = ctx;
        userList = users;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.users_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Users user = userList.get(position);

        holder.username.setText(user.getUsername());
        holder.careerDesc.setText(user.getCareerDescription());


        holder.connectButton.setOnClickListener(v -> {
            // Handle button click

        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView username, careerDesc;
        ImageView profilePic;
        Button connectButton;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            careerDesc = itemView.findViewById(R.id.career_desc);
            profilePic = itemView.findViewById(R.id.profilePic);
            connectButton = itemView.findViewById(R.id.BTNconnect);
        }
    }
}
