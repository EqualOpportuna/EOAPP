package com.example.equalopportuna;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycleView);
        List<itemCourseLisiting> items = new ArrayList<itemCourseLisiting>();
        items.add(new itemCourseLisiting("Hunter's Guide",R.drawable.apex,"grate"));


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new CL_Adapter(getApplicationContext()));

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
                if (item.getItemId() == R.id.stories_icon) {
                    // Navigate to AboutAppFragment using Navigation Component
                    Navigation.findNavController(MainActivity.this, R.id.NHFMain)
                            .navigate(R.id.storiesForumFragment);
                    return true;
                }
                if (item.getItemId() == R.id.courses_icon) {
                    // Navigate to AboutAppFragment using Navigation Component
                    Navigation.findNavController(MainActivity.this, R.id.NHFMain)
                            .navigate(R.id.recycleView);
                    return true;
                }

                return false;
            }
        });
    }
}
