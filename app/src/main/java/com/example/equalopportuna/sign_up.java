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

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class sign_up extends AppCompatActivity {

    private Button signUpButton;
    private EditText etName, etEmail, etCareerDesc, etAge, etPassword, etConfPassword;

    private FirebaseDatabase db;
    private DatabaseReference usersRef;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseApp.initializeApp(this);
        db = FirebaseDatabase.getInstance("https://equalopportunaapp-default-rtdb.asia-southeast1.firebasedatabase.app");
        usersRef = db.getReference("users");



        setContentView(R.layout.sign_up);

        signUpButton = findViewById(R.id.btn_register);
        etName = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email);
        etCareerDesc = findViewById(R.id.et_career_desc);
        etAge = findViewById(R.id.et_age);
        etPassword = findViewById(R.id.et_password);
        etConfPassword = findViewById(R.id.et_confPassword);

        TextView orLoginTextView = findViewById(R.id.or_login);
        orLoginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(sign_up.this, login_page.class));
            }
        });
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInputs()) {
                    insertDataIntoDatabase();
                }
            }
        });
    }

    private boolean validateInputs() {
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String age = etAge.getText().toString().trim();
        String password = etPassword.getText().toString();
        String confPassword = etConfPassword.getText().toString();

        if (name.isEmpty() || email.isEmpty() || age.isEmpty() || password.isEmpty() || confPassword.isEmpty()) {
            showToast("Please fill in all fields");
            return false;
        }

        if (!password.equals(confPassword)) {
            showToast("Passwords do not match");
            return false;
        }

        if (isNameRegistered(name)) {
            showToast("Full name is already registered");
            return false;
        }

        if (isEmailRegistered(email)) {
            showToast("Email is already registered");
            return false;
        }

        if (!isValidDateFormat(age) || !isValidDateRange(age)) {
            showToast("Invalid date of birth format or range");
            return false;
        }

        if (!isValidPassword(password)) {
            showToast("Password must be at least 5 characters, contain at least one number, and one special symbol");
            return false;
        }

        return true;
    }

    private boolean isValidPassword(String password) {
        // Password must be at least 5 characters, contain at least one number, and one special symbol
        return password.length() >= 5 && password.matches(".*\\d.*") && password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*");
    }

    private boolean isValidDateFormat(String date) {
        // Simple regex to check if the date is in the format yyyy/mm/dd
        return date.matches("\\d{4}/(0[1-9]|1[0-2])/(0[1-9]|[12][0-9]|3[01])");
    }

    private boolean isValidDateRange(String date) {
        // Extract year, month, and day from the date string
        String[] dateParts = date.split("/");
        int year = Integer.parseInt(dateParts[0]);
        int month = Integer.parseInt(dateParts[1]);
        int day = Integer.parseInt(dateParts[2]);

        // Check if month is in the valid range (1 to 12) and day is in the valid range for the given month
        return month >= 1 && month <= 12 && day >= 1 && day <= getDaysInMonth(year, month);
    }

    private int getDaysInMonth(int year, int month) {
        // Simple logic to get the number of days in a month
        switch (month) {
            case 2:
                // Check for leap year
                return (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) ? 29 : 28;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            default:
                return 31;
        }
    }

    private boolean isEmailRegistered(String email) {
        Database database = new Database();
        Connection connection = database.SQLConnection();

        if (connection != null) {
            try {
                String query = "SELECT * FROM users WHERE email = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, email);

                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    preparedStatement.close();
                    connection.close();
                    return true;
                }

                preparedStatement.close();
                connection.close();

            } catch (SQLException e) {
                showToast("Error checking email in the database: " + e.getMessage());
            }
        } else {
            showToast("Error connecting to the database");
        }

        return false;
    }

    private void insertDataIntoDatabase() {
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String career = etCareerDesc.getText().toString().trim();
        String age = etAge.getText().toString().trim();
        String password = etPassword.getText().toString();
        String defaultAvatar = "profile_image1";
        String defaultIntro = "Please add a short introduction here!";
        String defaultExperienceEducation = "Please add your experience and education.";

        HelperClass helperClass = new HelperClass(name, email);
        //DatabaseReference newUserRef = usersRef.push(); // Create a new child under 'users'
        usersRef.child(name).setValue(helperClass);

        Database database = new Database();
        Connection connection = database.SQLConnection();

        if (connection != null) {
            try {
                String query = "INSERT INTO users (full_name, email, date_of_birth, password, career_description, avatar, short_intro, experience_education) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, email);
                preparedStatement.setString(3, age);
                preparedStatement.setString(4, password);
                preparedStatement.setString(5, career);
                preparedStatement.setString(6, defaultAvatar);
                preparedStatement.setString(7, defaultIntro);
                preparedStatement.setString(8, defaultExperienceEducation);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    showToast("Registration successful");
                } else {
                    showToast("Registration failed");
                }

                preparedStatement.close();
                connection.close();

            } catch (SQLException e) {
                showToast("Error inserting data into the database: " + e.getMessage());
            }
        } else {
            showToast("Error connecting to the database");
        }
    }

    private boolean isNameRegistered(String name) {
        Database database = new Database();
        Connection connection = database.SQLConnection();

        if (connection != null) {
            try {
                String query = "SELECT * FROM users WHERE full_name = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, name);

                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    preparedStatement.close();
                    connection.close();
                    return true;
                }

                preparedStatement.close();
                connection.close();

            } catch (SQLException e) {
                showToast("Error checking full name in the database: " + e.getMessage());
            }
        } else {
            showToast("Error connecting to the database");
        }

        return false;
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
