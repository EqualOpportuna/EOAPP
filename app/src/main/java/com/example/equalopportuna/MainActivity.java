package com.example.equalopportuna;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FirebaseDatabase db;
    private DatabaseReference friendsRef;
    private DatabaseReference usersRef;

    private UserManager userManager;
    private List<String> jobNotifications;

    private String currentUsername;

    private MenuItem bellMenuItem;

    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Runnable checkPendingStatusRunnable = new Runnable() {
        @Override
        public void run() {
            checkJobNotification();
            checkConnectedFriends();
            checkPendingStatus();
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
        usersRef = db.getReference("users");
        Toolbar toolbar = findViewById(R.id.toolbar);
        bellMenuItem = toolbar.getMenu().findItem(R.id.bell);

        // Check for pending status in Firebase
        handler.post(checkPendingStatusRunnable);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_view);


        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.Community) {
                    Navigation.findNavController(MainActivity.this, R.id.NHFMain)
                            .navigate(R.id.fragment_community);
                    return true;
                }
                if (item.getItemId() == R.id.bell) {
                    if (item.getIcon().getConstantState().equals(getResources().getDrawable(R.drawable.bellinactive).getConstantState())) {
                        showToast("No notifications");
                        return true;
                    } else {
                        if (!jobNotifications.isEmpty()) {

                            showJobNotificationsPopup(item.getActionView() != null ? item.getActionView() : findViewById(R.id.bell)); // Provide a fallback view if item.getActionView() is null

                        } else {
                            Intent intent = new Intent(MainActivity.this, PendingRequestsActivity.class);
                            intent.putExtra("currentUsername", currentUsername);
                            MainActivity.this.startActivity(intent);
                            return true;
                        }
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
                    if (hasPendingRequests || !jobNotifications.isEmpty()) {
                        bellMenuItem.setIcon(R.drawable.bellactive);
                    }
                    else {
                        bellMenuItem.setIcon(R.drawable.bellinactive);
                    }
                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });
    }

    private void checkConnectedFriends() {



        List<Users> allUsers = Users.getAllUsers();
        friendsRef.child(currentUsername).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot friendSnapshot : snapshot.getChildren()) {
                        String friendName = friendSnapshot.getKey();
                        if (friendSnapshot.child("status").exists()) {
                            String status = friendSnapshot.child("status").getValue(String.class);
                            if (status.equals("Connected")) {

                                for (int i = 0; i < allUsers.size(); i++) {

                                    if (allUsers.get(i).getUsername().equals(friendName)) {
                                        allUsers.remove(i);
                                        Users.setAllUsers(allUsers);

                                        break;
                                    }

                                }

                            }
                        }
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

    public void checkJobNotification() {
        jobNotifications = new ArrayList<>();

        usersRef.child(currentUsername).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    DataSnapshot newJobPostedSnapshot = snapshot.child("new_job_posted");

                    if (newJobPostedSnapshot.exists()) {
                        for (DataSnapshot jobSnapshot : newJobPostedSnapshot.getChildren()) {
                            String jobNotification = jobSnapshot.getValue(String.class);
                            System.out.println("JOB TITLE: " + jobNotification);
                            jobNotifications.add("new job available: " + jobNotification);
                            if (bellMenuItem != null) {
                                bellMenuItem.setIcon(R.drawable.bellactive);
                            }
                        }

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });
    }




    private void showJobNotificationsPopup(View anchorView) {
        // Inflate the layout for the popup
        View popupView = getLayoutInflater().inflate(R.layout.popup_job_notifications, null);

        // Set up the PopupWindow with a black background
        PopupWindow popupWindow = new PopupWindow(popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.BLACK)); // Set black background

        // Set focusable to true to receive touch events outside the PopupWindow
        popupWindow.setFocusable(true);

        // Set up the ListView in the popup
        ListView listView = popupView.findViewById(R.id.popupListView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, jobNotifications);
        listView.setAdapter(adapter);

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                bellMenuItem.setIcon(R.drawable.bellinactive);

                jobNotifications.clear();
                deleteNewJobPostedNode();
            }
        });

        // Show the popup below the anchor view
        popupWindow.showAsDropDown(anchorView);
    }

    private void deleteNewJobPostedNode() {
        usersRef.child(currentUsername).child("new_job_posted").removeValue()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        showToast("new_job_posted node deleted");
                    } else {
                        showToast("Failed to delete new_job_posted node");
                    }
                });
    }



}