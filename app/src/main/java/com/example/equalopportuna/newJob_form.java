package com.example.equalopportuna;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class newJob_form extends AppCompatActivity {
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newjob_form);

        ImageButton imageButton = findViewById(R.id.imageButton);
        userId = getIntent().getIntExtra("USER_ID", -1);

        // Set click listener for imageButton
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to jobOpenings
                Intent jobOpeningsIntent = new Intent(newJob_form.this, jobOpenings.class);
                jobOpeningsIntent.putExtra("USER_ID", getIntent().getIntExtra("USER_ID", -1));
                startActivity(jobOpeningsIntent);
                finish(); // Finish the current activity to prevent going back to it on pressing back
            }
        });
    }
}
