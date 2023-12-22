package com.example.equalopportuna;

import static java.lang.Integer.parseInt;

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
import java.util.Random;


public class forgot_password extends AppCompatActivity {

    int code;
    public EditText mEmail, mSubject, mMessage;

    private Button btnproceed, btnchange;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_pw);

        mEmail = (EditText)findViewById(R.id.et_email);
        btnproceed = findViewById(R.id.btn_proceed);
        btnchange = findViewById(R.id.btn_change);
        TextView orLoginTextView = findViewById(R.id.or_login);

        btnproceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMail();
                findViewById(R.id.box1).setVisibility(View.INVISIBLE);
                findViewById(R.id.box2).setVisibility(View.VISIBLE);
                /*if(validateinputs()){
                    sendMail();
                    findViewById(R.id.box1).setVisibility(View.GONE);
                    findViewById(R.id.box2).setVisibility(View.VISIBLE);
                }*/
            }
        });

        btnchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCode();
            }
        });

        orLoginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(forgot_password.this, login_page.class));
            }
        });
    }

    private boolean validateinputs() {
        String email = mEmail.getText().toString();

        if (email.isEmpty()) {
            showToast("Please fill in all fields");
            return false;
        }

        if (isEmailRegistered(email)) {
            showToast("Email is already registered");
            return true;
        }

        return false;
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
                    return false;
                }

                preparedStatement.close();
                connection.close();

            } catch (SQLException e) {
                showToast("Error checking email in the database: " + e.getMessage());
            }
        } else {
            showToast("Error connecting to the database");
        }

        return true;
    }

    private void sendMail() {

        String mail = mEmail.getText().toString().trim();
        String subject = "EqualOpportuna OTP Code";

        //Generate random code
        Random random = new Random();
        code = random.nextInt(899999)+100000;
        String temp = String.valueOf(code);

        //Send Mail
        JavaMailAPI javaMailAPI = new JavaMailAPI(this,mail,subject,temp);

        javaMailAPI.execute();
    }

    public void checkCode(){
        String inputCode = String.valueOf(findViewById(R.id.et_otp));
        //if(inputCode.equals(String.valueOf(code))){
        if(code.equals == parseInt(String.valueOf(findViewById(R.id.et_otp)))){
            Toast.makeText(this,"Success",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(forgot_password.this,change_pw.class));
        }else{
            Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show();
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
