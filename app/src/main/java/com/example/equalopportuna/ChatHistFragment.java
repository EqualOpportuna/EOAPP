package com.example.equalopportuna;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChatHistFragment extends Fragment {

    private TextView userNameTextView;
    private ListView chatListView;
    private EditText messageEditText;
    private Button sendButton;

    private UserManager userManager;
    private FirebaseDatabase db;
    private DatabaseReference usersRef;

    private ChatAdapter chatAdapter;  // Custom adapter for chat messages


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_hist, container, false);

        db = FirebaseDatabase.getInstance("https://equalopportunaapp-default-rtdb.asia-southeast1.firebasedatabase.app");
        usersRef = db.getReference("chats");

        userNameTextView = view.findViewById(R.id.userNameTextView);
        chatListView = view.findViewById(R.id.chatListView);
        messageEditText = view.findViewById(R.id.messageEditText);
        sendButton = view.findViewById(R.id.sendButton);

        userManager = UserManager.getInstance(requireContext());

        chatAdapter = new ChatAdapter(requireContext(), new ArrayList<>());
        chatListView.setAdapter(chatAdapter);


        // Set up the adapter for chat messages (you'll need to create a custom adapter)
        // ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, messageList);
        // chatListView.setAdapter(adapter);

        // Set up onClickListener for the send button
        // Assume you have the current user's username and the other user's username
        String currentUserName = userManager.getFullName();
        String otherUserName = ""; // Replace with the actual current user's username
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("key")) {
            otherUserName = bundle.getString("key");
            // Now you have the other user's username, you can set it to the UI or perform any necessary actions
            userNameTextView.setText(otherUserName);
        }
        loadChatMessages(currentUserName, otherUserName);
        String finalOtherUserName = otherUserName;
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle sending the message
                String messageText = messageEditText.getText().toString().trim();
                if (!messageText.isEmpty()) {
                    // Create a ChatMessage object
                    long timestamp = System.currentTimeMillis();
                    ChatMessage chatMessage = new ChatMessage(currentUserName, finalOtherUserName, messageText, timestamp);

                    // Dynamically create chatId based on both users' usernames
                    String chatId = getChatId(currentUserName, finalOtherUserName);

                    // Reference to the chat messages in the database
                    DatabaseReference chatRef = usersRef.child(chatId);  // Use the usersRef reference

                    // Push the new message to the database
                    chatRef.push().setValue(chatMessage);

                    // Clear the input field
                    messageEditText.getText().clear();
                }
            }
        });


        // Optional: Set up additional features like attachments

        return view;
    }

    private void loadChatMessages(String currentUserName, String otherUserName) {
        // Use Firebase Realtime Database queries to retrieve chat messages based on currentUserName and the other user's name
        // Dynamically create chatId based on both users' usernames
        String chatId = getChatId(currentUserName, otherUserName);

        DatabaseReference chatRef = db.getReference("chats").child(chatId); // Use the specific chatId reference

        // Listen for changes in the chat messages under this chatId
        chatRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<ChatMessage> chatMessages = new ArrayList<>();
                for (DataSnapshot messageSnapshot : snapshot.getChildren()) {
                    ChatMessage chatMessage = messageSnapshot.getValue(ChatMessage.class);
                    if (chatMessage != null) {
                        chatMessages.add(chatMessage);
                    }
                }
                chatAdapter.setChatMessages(chatMessages);  // Update the adapter with new messages
                chatAdapter.notifyDataSetChanged();

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Error: " + error.getMessage());
            }
        });
    }

    // Helper method to create a unique chatId based on two usernames
    private String getChatId(String user1, String user2) {
        List<String> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        Collections.sort(users);  // Sort the usernames to ensure consistency

        StringBuilder chatIdBuilder = new StringBuilder();
        for (String user : users) {
            chatIdBuilder.append(user);
        }

        return chatIdBuilder.toString();
    }

}
