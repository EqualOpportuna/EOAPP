package com.example.equalopportuna;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainPageActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private TextView welcomeTextView;
    private ImageView profileImageView;
    private TextView uploadButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        welcomeTextView = findViewById(R.id.welcomeTextView);
        profileImageView = findViewById(R.id.profileImageView);
        uploadButton = findViewById(R.id.uploadButton);

        // Retrieve user ID from the intent
        int userId = getIntent().getIntExtra("USER_ID", -1);

        if (userId != -1) {
            // Fetch full name based on user ID
            String fullName = getFullName(userId);

            if (fullName != null) {
                // Display the welcome message
                welcomeTextView.setText("Welcome, " + fullName + "!");
            } else {
                showToast("Error fetching user information");
            }
        } else {
            showToast("User ID not found");
        }

        // Set up click listener for the upload button
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open an image picker
                openImagePicker();
            }
        });
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int itemId = menuItem.getItemId();

                if (itemId == R.id.home_icon) {
                    // Handle Home click
                } else if (itemId == R.id.jobs_icon) {
                    // Open jobOpenings activity and stay on Jobs in navigation
                    Intent jobsIntent = new Intent(MainPageActivity.this, jobOpenings.class);
                    jobsIntent.putExtra("USER_ID", userId); // Pass the user ID if needed
                    startActivity(jobsIntent);
                } else if (itemId == R.id.courses_icon) {
                    // Handle Courses click
                    // You can open the Courses activity or perform any other action
                } else if (itemId == R.id.stories_icon) {
                    // Handle Stories click
                    // You can open the Stories activity or perform any other action
                }

                return true;
            }
        });


    }

    private void openImagePicker() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Handle the selected image
            handleSelectedImage(data.getData());
        }
    }

    private void handleSelectedImage(android.net.Uri imageUri) {
        try {
            // Remove the current image
            profileImageView.setImageDrawable(null);

            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            // Set the selected image to the profileImageView
            profileImageView.setImageBitmap(bitmap);
        } catch (Exception e) {
            showToast("Error handling selected image: " + e.getMessage());
        }
    }

    private String getFullName(int userId) {
        Database database = new Database();
        Connection connection = database.SQLConnection();

        if (connection != null) {
            try {
                String query = "SELECT full_name FROM users WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, userId);

                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    return resultSet.getString("full_name");
                } else {
                    showToast("User not found");
                }

                preparedStatement.close();
                connection.close();

            } catch (SQLException e) {
                showToast("Error fetching user information: " + e.getMessage());
            }
        } else {
            showToast("Error connecting to the database");
        }

        return null;
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
