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
import com.example.equalopportuna.Friends;

import java.util.List;

public class Friends_adapter extends RecyclerView.Adapter<Friends_adapter.ViewHolder> {

    private final Context context;
    private final List<Friends> userList;

    public Friends_adapter(Context context, List<Friends> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_connections_friends, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Friends user = userList.get(position);

        // Set data to views
        holder.profilePic.setImageResource(user.getImageUrl());
        holder.username.setText(user.getUsername());
        holder.career_desc.setText(user.getCareerDescription());
        holder.connection_period.setText("Connected " + user.getConnectionPeriod() + " months ago");

        //set onClickListener for button
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView profilePic;
        TextView username, career_desc, connection_period;
        Button chatBTN;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profilePic = itemView.findViewById(R.id.profilePic);
            username = itemView.findViewById(R.id.username);
            career_desc = itemView.findViewById(R.id.career_desc);
            connection_period = itemView.findViewById(R.id.connection_period);
            chatBTN = itemView.findViewById(R.id.chatBTN);
        }
    }
}
