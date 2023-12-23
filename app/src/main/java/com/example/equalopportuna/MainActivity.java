package com.example.equalopportuna;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FirebaseDatabase db;
    private DatabaseReference friendsRef;

    private UserManager userManager;

    private String currentUsername;

    private MenuItem bellMenuItem;

    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Runnable checkPendingStatusRunnable = new Runnable() {
        @Override
        public void run() {
            // Check for pending status in Firebase
            checkPendingStatus();
            // Repeat the task every 10 seconds (adjust the delay as needed)
            handler.postDelayed(this, 1000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userManager = UserManager.getInstance(this);

        currentUsername = userManager.getFullName();

        db = FirebaseDatabase.getInstance("https://equalopportunaapp-default-rtdb.asia-southeast1.firebasedatabase.app");
        friendsRef = db.getReference("friends");
        Toolbar toolbar = findViewById(R.id.toolbar);
        bellMenuItem = toolbar.getMenu().findItem(R.id.bell);

        // Check for pending status in Firebase
        handler.post(checkPendingStatusRunnable);



        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_view);


        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId() == R.id.Community){
                    Navigation.findNavController(MainActivity.this, R.id.NHFMain)
                            .navigate(R.id.fragment_community);
                    return true;
                }
                if (item.getItemId() == R.id.bell) {
                    // Handle bell action
                    if (item.getIcon().getConstantState().equals(getResources().getDrawable(R.drawable.belldisactive).getConstantState())) {
                        // Check if the bell is in the disactive state
                        showToast("No notifications");
                        return true;
                    } else {
                        // Handle other bell actions here
                        Intent intent = new Intent(MainActivity.this, PendingRequestsActivity.class);
                        intent.putExtra("currentUsername", currentUsername);
                        MainActivity.this.startActivity(intent);
                        return true;
                    }
                }

                return false;
            }
        });

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
                            .navigate(R.id.courselistingFragment);
                    return true;
                }

                return false;
            }
    });
}

    private void checkPendingStatus() {
        friendsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean hasPendingRequests = false;

                for (DataSnapshot friendSnapshot : snapshot.getChildren()) {
                    if (!friendSnapshot.getKey().equals(currentUsername)) {
                        // Exclude the current user
                        DataSnapshot currentUserSnapshot = friendSnapshot.child(currentUsername);

                        if (currentUserSnapshot.exists()) {
                            // Check for status under the current user's entry
                            DataSnapshot statusSnapshot = currentUserSnapshot.child("status");

                            if (statusSnapshot.exists() && statusSnapshot.getValue(String.class).equals("Pending")) {
                                // Set the bell icon to bellactive if there are pending requests
                                hasPendingRequests = true;
                                break; // No need to continue checking if a pending request is found
                            }
                        }
                    }
                }

                if (bellMenuItem != null) {
                    if (hasPendingRequests) {
                        bellMenuItem.setIcon(R.drawable.bellactive);
                    } else {
                        bellMenuItem.setIcon(R.drawable.belldisactive);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });
    }


    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}