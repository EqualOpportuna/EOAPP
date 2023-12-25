package com.example.equalopportuna;

import android.content.ActivityNotFoundException;
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
    String code_string;
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
                checkCode(code_string);
                /*startActivity(new Intent(forgot_password.this, change_pw2.class));*/
                try{
                    if(checkCode(code_string)){
                        startActivity(new Intent(forgot_password.this, change_pw2.class));
                    }
                }catch(ActivityNotFoundException e){
                    showToast("problematic");
                }
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
        code_string = String.valueOf(code);

        //Send Mail
        JavaMailAPI javaMailAPI = new JavaMailAPI(this,mail,subject,code_string);

        javaMailAPI.execute();
    }

    public boolean checkCode(String code_string){
        EditText editText = findViewById(R.id.et_otp);
        String inputCode = editText.getText().toString();
        if(inputCode.equals(code_string)){
            showToast("Success");
            return true;
        }else{
            showToast("Failed");
            return false;
        }
    }

    /*public boolean checkCode(int inputCode){
        try{
            if(inputCode == code){
                Toast.makeText(this,"Success",Toast.LENGTH_SHORT).show();
                return true;
            }else{
                Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show();
                return false;
            }
        }catch(NumberFormatException e){
            Toast.makeText(this,"problematic",Toast.LENGTH_SHORT).show();
            return false;
        }
    }*/

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
