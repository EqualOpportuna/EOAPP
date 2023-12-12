package com.example.equalopportuna;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class chat_adapter extends RecyclerView.Adapter<chat_adapter.ChatViewHolder> {

    private List<chatFragment> chatList;
    private Context context;

    public chat_adapter(List<chatFragment> chatList, Context context) {
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
        chatFragment chatItem = chatList.get(position);

        holder.username.setText(chatItem.getUsername());
        holder.chatPreview.setText(chatItem.getChatPreview());
        holder.chatDate.setText(chatItem.getChatDate());

    }

    @Override
    public int getItemCount() {
        return chatList.size();
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
