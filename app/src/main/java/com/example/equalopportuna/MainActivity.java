package com.example.equalopportuna;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_view);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if (item.getItemId() == R.id.home_icon) {
                    // Navigate to AboutAppFragment using Navigation Component
                    Navigation.findNavController(MainActivity.this, R.id.NHFMain)
                            .navigate(R.id.mainPageFragment);
                    return true;
                }
                if (item.getItemId() == R.id.jobs_icon) {
                    // Navigate to AboutAppFragment using Navigation Component
                    Navigation.findNavController(MainActivity.this, R.id.NHFMain)
                            .navigate(R.id.jobOpeningsFragment);
                    return true;
                }

                return false;
            }
        });
    }
}
