// login_page.java
package com.example.equalopportuna;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class login_page extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button loginButton;
    private TextView registerTextView, forgotPasswordTextView;

    private UserManager userManager;
    private Job jobs;
    private StoryManager storyManager; // Add StoryManager instance

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        loginButton = findViewById(R.id.btnLogin);
        registerTextView = findViewById(R.id.tv_or_signup);
        forgotPasswordTextView = findViewById(R.id.tv_forgot_password);

        // Initialize instances
        userManager = UserManager.getInstance(this);
        storyManager = StoryManager.getInstance(this); // Initialize StoryManager

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the login logic here
                if (validateInputs()) {
                    // Inputs are valid, proceed with login
                    login();
                }
            }
        });

        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToSignupActivity();
            }
        });

        forgotPasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToForgotPassword();
            }
        });
    }

    private boolean validateInputs() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            showToast("Please fill in all fields");
            return false;
        }

        return true;
    }

    private void login() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString();

        Database database = new Database();
        Connection connection = database.SQLConnection();

        if (connection != null) {
            try {
                String query = "SELECT id, full_name, email, date_of_birth FROM users WHERE email = ? AND password = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, password);

                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    int userId = resultSet.getInt("id");
                    String fullName = resultSet.getString("full_name");
                    String userEmail = resultSet.getString("email");
                    String dateOfBirth = resultSet.getString("date_of_birth");

                    // Set user information in UserViewModel
                    userManager.saveUserInfo(userId, fullName, userEmail, dateOfBirth);
                    jobs.fetchAndStoreJobData(connection);

                    // Fetch and store stories using StoryManager
                    storyManager.fetchAndStoreStoryData(this, connection);

                    showToast("Logged in successfully");
                    navigateToMainPage();
                } else {
                    showToast("Invalid email or password");
                }

                preparedStatement.close();
                connection.close();

            } catch (SQLException e) {
                showToast("Error checking login credentials: " + e.getMessage());
            }
        } else {
            showToast("Error connecting to the database");
        }
    }

    private void navigateToMainPage() {
        // Start the MainPageActivity
        Intent intent = new Intent(login_page.this, MainActivity.class);
        startActivity(intent);
    }

    private void navigateToForgotPassword() {
        // Start the MainPageActivity
        Intent intent = new Intent(login_page.this, forgot_password.class);
        startActivity(intent);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void navigateToSignupActivity() {
        // Start the SignupActivity
        Intent intent = new Intent(login_page.this, sign_up.class);
        startActivity(intent);
    }
}
