package com.example.equalopportuna;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class change_pw extends AppCompatActivity {
    private EditText etconfNewPassword,etnewpassword,etcurrentpw;
    private Button btnproceed,btnback;

    @SuppressLint("WrongViewCast")
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_pw);

        etcurrentpw = findViewById(R.id.et_currentpw);
        etnewpassword = findViewById(R.id.et_newpassword);
        etconfNewPassword = findViewById(R.id.et_confNewPassword);
        btnproceed = findViewById(R.id.btn_proceed);

        TextView orLoginTextView = findViewById(R.id.btn_back);
        orLoginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(change_pw.this, login_page.class));
            }
        });

        btnproceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private boolean validateinputs() {
        String password = etcurrentpw.getText().toString();
        String NewPassword = etnewpassword.getText().toString();
        String confNewPassword = etconfNewPassword.getText().toString();


        if (password.isEmpty() || NewPassword.isEmpty() || confNewPassword.isEmpty()) {
            showToast("Please fill in all fields");
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

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


}
