package com.example.equalopportuna;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class jobOpenings extends AppCompatActivity {

    RecyclerView RecycleJobPosts;
    int userId;

    String s1[], s2[], s3[];
    int images[] = {R.drawable.logo1, R.drawable.logo2, R.drawable.logo3, R.drawable.logo4, R.drawable.um_logo,
            R.drawable.logo1, R.drawable.logo2, R.drawable.logo3, R.drawable.logo4, R.drawable.um_logo};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_openings);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.jobs_icon);

        userId = getIntent().getIntExtra("USER_ID", -1);


        // Set listener for BottomNavigationView
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.home_icon) {
                    // Navigate to MainPage
                    Intent mainPageIntent = new Intent(jobOpenings.this, MainPageActivity.class);
                    mainPageIntent.putExtra("USER_ID", userId); // Pass the user ID

                    startActivity(mainPageIntent);
                    finish(); // Finish the current activity to prevent going back to it on pressing back
                    return true;
                }
                // Add more conditions for other items if needed
                return false;
            }
        });

        RecycleJobPosts = findViewById(R.id.RecycleJobPosts);
        s1 = getResources().getStringArray(R.array.job_titles);
        s2 = getResources().getStringArray(R.array.company_name);
        s3 = getResources().getStringArray(R.array.job_location);

        adapter adp = new adapter(this, s1, s2, s3, images);
        RecycleJobPosts.setAdapter(adp);
        RecycleJobPosts.setLayoutManager(new LinearLayoutManager(this));
        findViewById(R.id.new_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to NewJobForm
                Intent newJobFormIntent = new Intent(jobOpenings.this, newJob_form.class);
                newJobFormIntent.putExtra("USER_ID", getIntent().getIntExtra("USER_ID", -1));
                startActivity(newJobFormIntent);
                finish();
            }
        });
    }







}
