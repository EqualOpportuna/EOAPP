package com.example.equalopportuna;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.equalopportuna.databinding.ActivityCourseDetailsBinding;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class activity_course_details extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
private ActivityCourseDetailsBinding binding;

    String link;
    String description;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        int receivedValue = 0;
        // Receive the passed value from the previous activity
        if (getIntent().hasExtra("id")) {
            receivedValue = getIntent().getIntExtra("id",0); // Replace "YOUR_KEY" with your key
        }

        Database database = new Database();
        Connection connection = database.SQLConnection();
        String query = "SELECT course_name, description, youtube_link FROM courses where id = " + receivedValue ;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                // 获取每一行的数据
                link = resultSet.getString("description");
                description = resultSet.getString("youtube_link");
                name  = resultSet.getString("course_name");
                System.out.println(description);
                TextView heading = findViewById(R.id.heading);
                TextView content = findViewById(R.id.description);
                heading.setText(name);
                content.setText(description);
                Button watch = findViewById(R.id.watchBtn);
                watch.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Uri uri = Uri.parse(link);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }
                });
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }



}