package com.example.equalopportuna;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

// CustomAdapter.java
public class CustomAdapter extends ArrayAdapter<String> {
    private Context context;
    private List<String> pendingUsersList;

    private FirebaseDatabase db;
    private DatabaseReference friendsRef;

    private String currentUsername;

    Database database;

    public CustomAdapter(Context context, List<String> pendingUsersList, String currentUsername) {
        super(context, 0, pendingUsersList);
        this.context = context;
        this.pendingUsersList = pendingUsersList;
        this.currentUsername = currentUsername;
        this.database = new Database();
        db = FirebaseDatabase.getInstance("https://equalopportunaapp-default-rtdb.asia-southeast1.firebasedatabase.app");
        friendsRef = db.getReference("friends");
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_notification, parent, false);
        }

        // Get the current user name
        final String userName = getItem(position);

        // Set the senderNameTextView text
        TextView senderNameTextView = convertView.findViewById(R.id.senderNameTextView);
        senderNameTextView.setText(userName);

        // Get the acceptButton
        Button acceptButton = convertView.findViewById(R.id.acceptButton);

        // Set OnClickListener for acceptButton
        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateStatusInDatabase(userName, "Connected");
                updateStatusInFirebase(userName, "Connected");
                Connection connection = database.SQLConnection();

                List<Friends> allFriends = Friends.getAllFriendsFromDatabase(connection, currentUsername);
                Friends.setAllFriends(allFriends);
                chat.getAllChatList();


                    List<Users> allUsers = Users.getAllUsersFromDatabase(connection, currentUsername);
                    for(int i = 0; i < allUsers.size(); i++){
                        for(int j = 0; j  < allFriends.size(); j++){
                            if(allUsers.get(i).getUsername().equals(allFriends.get(j).getUsername())){
                                allUsers.remove(i);
                                break;
                            }
                        }

                    Users.setAllUsers(allUsers);
                }

                // Create a modifiable list from the unmodifiable one
                List<String> modifiableList = new ArrayList<>(pendingUsersList);



                // Remove the accepted user from the list
                modifiableList.remove(userName);
                pendingUsersList = modifiableList; // Update the reference

                notifyDataSetChanged();

                // Check if there are no more friend requests and finish the activity
                if (pendingUsersList.isEmpty()) {
                    ((Activity) context).finish();
                }
            }
        });

        // Get the denyButton
        Button denyButton = convertView.findViewById(R.id.denyButton);

        // Set OnClickListener for denyButton
        denyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteEntryInDatabase(userName);
                deleteEntryInFirebase(userName);



                List<String> modifiableList = new ArrayList<>(pendingUsersList);

                // Remove the accepted user from the list
                modifiableList.remove(userName);
                pendingUsersList = modifiableList; // Update the reference

                notifyDataSetChanged();

                // Check if there are no more friend requests and finish the activity
                if (pendingUsersList.isEmpty()) {
                    ((Activity) context).finish();
                }
            }
        });

        return convertView;
    }

    private void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    private void updateStatusInFirebase(String userName, String newStatus) {
        // Update the 'status' field in Firebase
        friendsRef.child(userName).child(currentUsername).child("status").setValue(newStatus);
        showToast(userName + " Accepted");
    }

    private void deleteEntryInFirebase(String userName) {
        // Delete the entry from Firebase
        friendsRef.child(userName).child(currentUsername).removeValue();
        showToast(userName + " Denied");
    }

    private void updateStatusInDatabase(String userName, String newStatus) {
        // Get user IDs for the given usernames
        int userId1 = Integer.parseInt(database.getUserIdByUsername(currentUsername));
        int userId2 = Integer.parseInt(database.getUserIdByUsername(userName));

        // Update the 'status' field in the local MySQL database
        database.updateStatus(userId1, userId2, newStatus);
        showToast(userName + " Accepted");
    }

    private void deleteEntryInDatabase(String userName) {
        // Get user IDs for the given usernames
        int userId1 = Integer.parseInt(database.getUserIdByUsername(currentUsername));
        int userId2 = Integer.parseInt(database.getUserIdByUsername(userName));

        // Delete the entry from the local MySQL database
        database.deleteEntry(userId1, userId2);
        showToast(userName + " Denied");
    }

}