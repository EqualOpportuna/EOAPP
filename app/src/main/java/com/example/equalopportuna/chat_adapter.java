package com.example.equalopportuna;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class chat_adapter extends RecyclerView.Adapter<chat_adapter.ChatViewHolder> {

    private List<chat> chatList;
    private Context context;

    public chat_adapter(List<chat> chatList, Context context) {
        this.chatList = chatList;
        this.context = context;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_connection_chat, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        chat chatItem = chatList.get(position);

        holder.username.setText(chatItem.getUsername());
        holder.chatPreview.setText(chatItem.getChatPreview());
        holder.chatDate.setText(chatItem.getCareerDescription());
        int resId = context.getResources().getIdentifier(chatItem.getAvatarName(), "drawable", context.getPackageName());
        holder.profilePic.setImageResource(resId);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToChatHistFragment(chatItem.getUsername());
            }
        });
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    private void navigateToChatHistFragment(String username) {
        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();

        ChatHistFragment chatHistFragment = new ChatHistFragment();

        // Create a Bundle and add the username to it
        Bundle bundle = new Bundle();
        bundle.putString("key", username);
        chatHistFragment.setArguments(bundle);

        // Begin the transaction
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_community, chatHistFragment)
                .addToBackStack(null)
                .commit();
    }


    public static class ChatViewHolder extends RecyclerView.ViewHolder {
        ImageView profilePic;
        TextView username, chatPreview, chatDate;
        CardView cardView;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            profilePic = itemView.findViewById(R.id.profilePic);
            username = itemView.findViewById(R.id.username);
            chatPreview = itemView.findViewById(R.id.chat_preview);
            chatDate = itemView.findViewById(R.id.chat_date);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
