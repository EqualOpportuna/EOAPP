package com.example.equalopportuna;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PendingRequestsActivity extends AppCompatActivity {

    private String currentUsername;

    private FirebaseDatabase db;
    private DatabaseReference friendsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_requests);

        db = FirebaseDatabase.getInstance("https://equalopportunaapp-default-rtdb.asia-southeast1.firebasedatabase.app");
        friendsRef = db.getReference("friends");

        // Retrieve currentUsername from the intent
        if (getIntent() != null && getIntent().hasExtra("currentUsername")) {
            currentUsername = getIntent().getStringExtra("currentUsername");
        }

        // Get the list of pending users
        getListOfPendingNotifications();
    }

    public void getListOfPendingNotifications() {
        final List<String> pendingNamesList = new ArrayList<>();

        friendsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot friendSnapshot : snapshot.getChildren()) {
                    String friendName = friendSnapshot.getKey();

                    if (friendName != null && !friendName.equals(currentUsername)) {
                        // Exclude the current user
                        DataSnapshot currentUserSnapshot = friendSnapshot.child(currentUsername);

                        if (currentUserSnapshot.exists()) {
                            // Check for status under the current user's entry
                            DataSnapshot statusSnapshot = currentUserSnapshot.child("status");

                            if (statusSnapshot.exists() && statusSnapshot.getValue(String.class).equals("Pending")) {
                                // Add the friendName to the list if there is a pending request
                                pendingNamesList.add(friendName);
                            }
                        }
                    }
                }

                // Convert the list to an array
                String[] pendingNamesArray = new String[pendingNamesList.size()];
                pendingNamesList.toArray(pendingNamesArray);

                // Now, you can use pendingNamesArray to display or process the list of pending notifications
                handlePendingNotifications(pendingNamesArray);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });
    }

    private void handlePendingNotifications(String[] pendingNamesArray) {
        // Convert the array to a List for easier handling
        List<String> pendingUsersList = Arrays.asList(pendingNamesArray);

        // Create a CustomAdapter to populate the ListView with custom layout
        CustomAdapter adapter = new CustomAdapter(this, pendingUsersList, currentUsername);

        // Get the ListView from the layout
        ListView listView = findViewById(R.id.listView);

        // Set the adapter for the ListView
        listView.setAdapter(adapter);
    }



}
