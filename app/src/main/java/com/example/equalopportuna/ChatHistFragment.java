package com.example.equalopportuna;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
    private ImageView imageView;
    private ListView chatListView;
    private EditText messageEditText;
    private Button sendButton;

    private UserManager userManager;
    private FirebaseDatabase db;
    private DatabaseReference usersRef;

    private ChatAdapter chatAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_hist, container, false);

        db = FirebaseDatabase.getInstance("https://equalopportunaapp-default-rtdb.asia-southeast1.firebasedatabase.app");
        usersRef = db.getReference("chats");

        userNameTextView = view.findViewById(R.id.userNameTextView);
        imageView = view.findViewById(R.id.image);
        chatListView = view.findViewById(R.id.chatListView);
        messageEditText = view.findViewById(R.id.messageEditText);
        sendButton = view.findViewById(R.id.sendButton);

        userManager = UserManager.getInstance(requireContext());
        String currentUserName = userManager.getFullName();

        chatAdapter = new ChatAdapter(requireContext(), new ArrayList<>(), currentUserName);
        chatListView.setAdapter(chatAdapter);



        String otherUserName = "";
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("key_username") && bundle.containsKey("key_avatar")) {
            otherUserName = bundle.getString("key_username");
            userNameTextView.setText(otherUserName);

            // Get the avatar name from the bundle
            String avatarName = bundle.getString("key_avatar");

            // Check if avatarName is not null before setting the image resource
            if (avatarName != null) {
                // Set the image resource dynamically based on the avatar name
                int resId = getResources().getIdentifier(avatarName, "drawable", requireContext().getPackageName());
                imageView.setImageResource(resId);
            } else {
                // Handle the case where avatarName is null (optional)
                // For example, you can set a default image or log an error message.
                // imageView.setImageResource(R.drawable.default_avatar);
                // Log.e("ChatHistFragment", "AvatarName is null");
            }
        }
        loadChatMessages(currentUserName, otherUserName);
        String finalOtherUserName = otherUserName;
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageEditText.getText().toString().trim();
                if (!messageText.isEmpty()) {
                    long timestamp = System.currentTimeMillis();
                    ChatMessage chatMessage = new ChatMessage(currentUserName, finalOtherUserName, messageText, timestamp);

                    String chatId = getChatId(currentUserName, finalOtherUserName);

                    DatabaseReference chatRef = usersRef.child(chatId);

                    chatRef.push().setValue(chatMessage);

                    messageEditText.getText().clear();
                }
            }
        });

        return view;
    }

    private void loadChatMessages(String currentUserName, String otherUserName) {

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
