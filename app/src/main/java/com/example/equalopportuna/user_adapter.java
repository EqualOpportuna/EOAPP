package com.example.equalopportuna;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class user_adapter extends RecyclerView.Adapter<user_adapter.ViewHolder> {

    private final Context context;
    private final List<Users> userList;

    private FirebaseDatabase db;
    private DatabaseReference friendsRef;

    private static DatabaseReference friendsRefStatic;

    private String currentUsername;

    private static String currentUsernameStatic;


    private Database database;

    public user_adapter(Context context, List<Users> userList, String loggedInUser) {
        this.context = context;
        this.userList = userList;
        this.currentUsername = loggedInUser;
        currentUsernameStatic = currentUsername;
        db = FirebaseDatabase.getInstance("https://equalopportunaapp-default-rtdb.asia-southeast1.firebasedatabase.app");
        friendsRef = db.getReference("friends");
        friendsRefStatic = friendsRef;
        database = new Database();

    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_connections_users, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Users user = userList.get(position);

        // Set data to views
        int resId = context.getResources().getIdentifier(user.getAvatarName(), "drawable", context.getPackageName());
        holder.profilePic.setImageResource(resId);
        holder.username.setText(user.getUsername());
        holder.career_desc.setText(user.getCareerDescription());

        int originalColor = holder.BTNconnect.getCurrentTextColor();

        // Set an initial status based on Firebase or your logic
        String initialStatus = "Connect";
        holder.BTNconnect.setText(initialStatus);
        holder.BTNconnect.setTextColor(originalColor);

        // Check the friend status for the logged-in user and the current user
        DatabaseReference userRef = friendsRef.child(currentUsername).child(user.getUsername());
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String status = snapshot.child("status").getValue(String.class);

                    // Set the initial status based on the Firebase data
                    holder.BTNconnect.setText(status);
                    if (status.equals("Pending")) {
                        holder.BTNconnect.setTextColor(context.getResources().getColor(android.R.color.holo_green_light));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });

        holder.BTNconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String buttonText = holder.BTNconnect.getText().toString();

                int currentID = Integer.valueOf(database.getUserIdByUsername(currentUsername));
                int otherID = Integer.valueOf(database.getUserIdByUsername(user.getUsername()));

                if (buttonText.equals("Connect")) {
                    userRef.child("status").setValue("Pending");
                    holder.BTNconnect.setText("Pending");
                    insertFriendship(currentID, otherID, "Pending");
                    holder.BTNconnect.setTextColor(context.getResources().getColor(android.R.color.holo_green_light));
                } else if (buttonText.equals("Pending")) {
                    userRef.removeValue();
                    holder.BTNconnect.setText("Connect");
                    removeFriendship(currentID, otherID);
                    holder.BTNconnect.setTextColor(originalColor);
                }
            }
        });
    }

    // Helper method to insert a new friendship entry into the MySQL database
    private void insertFriendship(long userId1, long userId2, String status) {
        try {
            if (database.con == null || database.con.isClosed()) {
                database.con = database.SQLConnection();
            }

            if (database.con != null) {
                Statement stmt = database.con.createStatement();
                String insertQuery = String.format("INSERT INTO friends (user_id1, user_id2, status, connection_date) VALUES (%d, %d, '%s', CURRENT_TIMESTAMP)", userId1, userId2, status);
                stmt.executeUpdate(insertQuery);
                stmt.close();
            }
        } catch (SQLException e) {
            Log.e("Error", "Error inserting friendship: " + e.getMessage());
        }
    }

    // Helper method to remove a friendship entry from the MySQL database
    private void removeFriendship(long userId1, long userId2) {
        try {
            if (database.con == null || database.con.isClosed()) {
                database.con = database.SQLConnection();
            }

            if (database.con != null) {
                Statement stmt = database.con.createStatement();
                String deleteQuery = String.format("DELETE FROM friends WHERE (user_id1 = %d AND user_id2 = %d) OR (user_id1 = %d AND user_id2 = %d)", userId1, userId2, userId2, userId1);
                stmt.executeUpdate(deleteQuery);
                stmt.close();
            }
        } catch (SQLException e) {
            Log.e("Error", "Error removing friendship: " + e.getMessage());
        }
    }




    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView profilePic;
        TextView username, career_desc;
        Button BTNconnect;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profilePic = itemView.findViewById(R.id.profilePic);
            username = itemView.findViewById(R.id.username);
            career_desc = itemView.findViewById(R.id.career_desc);
            BTNconnect = itemView.findViewById(R.id.BTNconnect);
        }
    }







}
