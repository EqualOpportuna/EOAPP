package com.example.equalopportuna;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class login_page extends AppCompatActivity {
    Button loginButton;
    EditText email,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);


        loginButton = findViewById(R.id.button2);
        email = findViewById(R.id.editTextTextEmailAddress);
        password = findViewById(R.id.editTextTextPassword);
        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                login_button(v);
            }
        });

    }

    public void login_button(View v) {
        String emailText = email.getText().toString().trim();
        String passwordText = password.getText().toString().trim();

        // verification
        if (emailText.isEmpty() || passwordText.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please fill in all fields", Toast.LENGTH_LONG).show();
            return;
        }
        if(service_login(emailText,passwordText)){
            Toast.makeText(getApplicationContext(), "Login successfully!", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getApplicationContext(), "Login Failed! Username or Password wrong", Toast.LENGTH_LONG).show();

        }
    }


    public boolean service_login(String email, String password){

        Database db = new Database();
        Connection connection = db .SQLConnection();
        if (connection != null){
            try {
                String query = "SELECT * FROM users WHERE email = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, email);

                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    String realpw = resultSet.getString("password");
                    if (realpw == password){
                        preparedStatement.close();
                        connection.close();
                        return true;
                    }else{
                        preparedStatement.close();
                        connection.close();
                        return false;
                    }




                }

                preparedStatement.close();
                connection.close();

            } catch (SQLException e) {
                return false;
            }
        }
        return false;

    }



}