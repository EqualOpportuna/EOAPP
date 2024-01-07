package com.example.equalopportuna;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.equalopportuna.R;
import com.example.equalopportuna.Friends;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
        int resId = context.getResources().getIdentifier(user.getAvatarName(), "drawable", context.getPackageName());
        holder.profilePic.setImageResource(resId);
        holder.username.setText(user.getUsername());
        holder.career_desc.setText(user.getCareerDescription());
        holder.connection_period.setText(user.getConnectionPeriod());

        // Set click listener for the profilePic
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProfileInfoDialog(user);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView profilePic;
        TextView username, career_desc, connection_period;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profilePic = itemView.findViewById(R.id.profilePic);
            username = itemView.findViewById(R.id.username);
            career_desc = itemView.findViewById(R.id.career_desc);
            connection_period = itemView.findViewById(R.id.connection_period);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
    private void showProfileInfoDialog(Friends user) {
        // Create a custom dialog
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.custom_profile_info_dialog);

        // Initialize views in the custom dialog
        ImageView profilePic = dialog.findViewById(R.id.dialogProfilePic);
        TextView username = dialog.findViewById(R.id.dialogUsername);
        TextView careerDesc = dialog.findViewById(R.id.dialogCareerDesc);
        TextView shortInfo = dialog.findViewById(R.id.dialogShortInfo);
        TextView experienceEducation = dialog.findViewById(R.id.dialogExperienceEducation);

        // Set data to views
        int resId = context.getResources().getIdentifier(user.getAvatarName(), "drawable", context.getPackageName());
        profilePic.setImageResource(resId);
        username.setText(user.getUsername());
        careerDesc.setText(user.getCareerDescription());

        List<String> userProfile = getUserProfile(user.getUsername());
        if (userProfile.size() == 2) {
            shortInfo.setText(userProfile.get(0));
            experienceEducation.setText(userProfile.get(1));
        }

        // Show the dialog
        dialog.show();
    }

    public List<String> getUserProfile(String username) {
        List<String> userProfile = new ArrayList<>();
        Database db = new Database();
        Connection connection = db.SQLConnection();

        if (connection != null) {
            try {
                // Get user_id from the username
                String userId = db.getUserIdByUsername(username);

                if (userId != null) {
                    // Fetch user information from the database
                    Statement stmt = connection.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT short_intro, experience_education FROM users WHERE id = " + userId);

                    if (rs.next()) {
                        userProfile.add(rs.getString("short_intro"));
                        userProfile.add(rs.getString("experience_education"));
                        // Add other fields as needed
                    }

                    rs.close();
                    stmt.close();
                }
            } catch (SQLException e) {
                Log.e("Error", "Error getting user profile from the database: " + e.getMessage());
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    Log.e("Error", "Error closing database connection: " + e.getMessage());
                }
            }
        }

        return userProfile;
    }


}

