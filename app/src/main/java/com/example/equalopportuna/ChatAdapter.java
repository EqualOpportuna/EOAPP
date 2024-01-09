package com.example.equalopportuna;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import java.util.List;

public class ChatAdapter extends ArrayAdapter<ChatMessage> {

    private Context context;
    private List<ChatMessage> chatMessages;

    private String currentUsername;

    public ChatAdapter(Context context, List<ChatMessage> chatMessages, String currentUsername) {
        super(context, 0, chatMessages);
        this.context = context;
        this.chatMessages = chatMessages;
        this.currentUsername = currentUsername;
    }

    public void setChatMessages(List<ChatMessage> chatMessages) {
        if (chatMessages != null) {
            this.chatMessages.clear(); // Clear the existing data
            this.chatMessages.addAll(chatMessages);
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_chat_message, parent, false);
        }

        // Get the current chat message
        ChatMessage chatMessage = getItem(position);

        // Update UI elements with chat message details
        TextView senderTextView = convertView.findViewById(R.id.senderTextView);
        TextView messageTextView = convertView.findViewById(R.id.messageTextView);

        if (chatMessage != null) {
            if(chatMessage.getSender().equals(currentUsername)){
                senderTextView.setText("You");
                senderTextView.setTextColor(ContextCompat.getColor(context, android.R.color.holo_green_dark));

            }
            else {
                senderTextView.setText(chatMessage.getSender());
                senderTextView.setTextColor(ContextCompat.getColor(context, android.R.color.holo_blue_dark));

            }
            messageTextView.setText(chatMessage.getMessage());
        }

        return convertView;
    }
}

