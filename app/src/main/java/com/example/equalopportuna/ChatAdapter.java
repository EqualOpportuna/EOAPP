package com.example.equalopportuna;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class ChatAdapter extends ArrayAdapter<ChatMessage> {

    private Context context;
    private List<ChatMessage> chatMessages;

    public ChatAdapter(Context context, List<ChatMessage> chatMessages) {
        super(context, 0, chatMessages);
        this.context = context;
        this.chatMessages = chatMessages;
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
            senderTextView.setText(chatMessage.getSender());
            messageTextView.setText(chatMessage.getMessage());
        }

        return convertView;
    }
}

