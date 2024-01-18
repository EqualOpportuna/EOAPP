package com.example.equalopportuna;

import android.annotation.SuppressLint;
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

public class change_pw2 extends AppCompatActivity {
    private EditText etemail,etconfNewPassword,etnewpassword;
    private Button btnproceed,btnback;

    @SuppressLint("WrongViewCast")
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_pw2);

        etemail = findViewById(R.id.et_email);
        etnewpassword = findViewById(R.id.et_newpassword);
        etconfNewPassword = findViewById(R.id.et_confNewPassword);
        btnproceed = findViewById(R.id.btn_proceed);

        TextView orLoginTextView = findViewById(R.id.btn_back);
        orLoginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(change_pw2.this, login_page.class));
                finish();
            }
        });

        btnproceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateinputs()){
                    changeDatainDatabase();
                    startActivity(new Intent(change_pw2.this, login_page.class));
                    finish();
                }
            }
        });
    }

    private boolean validateinputs() {
        String email = etemail.getText().toString();
        String NewPassword = etnewpassword.getText().toString();
        String confNewPassword = etconfNewPassword.getText().toString();

        if (email.isEmpty() || NewPassword.isEmpty() || confNewPassword.isEmpty()) {
            showToast("Please fill in all fields");
            return false;
        }

        if (!isEmailRegistered(email)) {
            showToast("Email is already registered");
            return false;
        }

        if (!NewPassword.equals(confNewPassword)) {
            showToast("New Passwords do not match");
            return false;
        }

        if (!isValidPassword(NewPassword)) {
            showToast("New Password must be at least 5 characters, contain at least one number, and one special symbol");
            return false;
        }

        if (!isValidPassword(confNewPassword)) {
            showToast("New Password must be at least 5 characters, contain at least one number, and one special symbol");
            return false;
        }

        return true;
    }

    private boolean isValidPassword(String password) {
        // Password must be at least 5 characters, contain at least one number, and one special symbol
        return password.length() >= 5 && password.matches(".*\\d.*") && password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*");
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



    private void changeDatainDatabase() {
        String email = etemail.getText().toString().trim();
        String newpw = etnewpassword.getText().toString().trim();

        Database database = new Database();
        Connection connection = database.SQLConnection();

        if (connection != null) {
            try {
                String query0 = "UPDATE users \n SET password = ? \n WHERE email = ?";
                /* String query1 = "SET password = '" + newpw + "'";
                String query2 = "WHERE email = " + email; */
                PreparedStatement preparedStatement = connection.prepareStatement(query0);
                preparedStatement.setString(1, newpw);
                preparedStatement.setString(2, email);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    showToast("Password Changed!");
                } else {
                    showToast("Changing password failed");
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

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


}
