package com.example.equalopportuna;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateJobFormFragment extends Fragment {

    private EditText etJobTitle, etCompany, etLocation;

    private FirebaseDatabase db;
    private DatabaseReference jobsRef;
    private DatabaseReference userRef;

    private UserManager userManager;

    private String username;

    private int userID;
    private Spinner tierSpinner;

    public CreateJobFormFragment() {
        // Required empty public constructor
    }

    public static CreateJobFormFragment newInstance() {
        return new CreateJobFormFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_job_form, container, false);

        ImageButton imageButton = view.findViewById(R.id.imageButton);
        Button jobPostBtn = view.findViewById(R.id.watchBtn);
        tierSpinner = view.findViewById(R.id.tierSpinner); // Add this line to initialize the Spinner

        userManager = UserManager.getInstance(requireContext());

        username = userManager.getFullName();
        userID = userManager.getUserId();

        db = FirebaseDatabase.getInstance("https://equalopportunaapp-default-rtdb.asia-southeast1.firebasedatabase.app");
        jobsRef = db.getReference("jobs");
        userRef = db.getReference("users");


        // Initialize EditText fields
        etJobTitle = view.findViewById(R.id.ET_jobTitle);
        etCompany = view.findViewById(R.id.ET_company);
        etLocation = view.findViewById(R.id.ET_location);

        // Set OnClickListener for the ImageButton
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate to JobOpeningsFragment
                Navigation.findNavController(view).navigate(R.id.jobOpeningsFragment);
            }
        });

        // Set OnClickListener for the jobPostBtn
        jobPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertJobToDatabase();
                updateNotificationStatusForAllUsers();
            }
        });

        // Populate Spinner with tier options
        String[] tierOptions = {"A", "B", "C"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, tierOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tierSpinner.setAdapter(adapter);

        return view;
    }

    private void insertJobToDatabase() {
        String jobTitle = etJobTitle.getText().toString().trim();
        String company = etCompany.getText().toString().trim();
        String location = etLocation.getText().toString().trim();
        String selectedTier = tierSpinner.getSelectedItem().toString(); // Get the selected tier from the Spinner

        if (jobTitle.isEmpty() || company.isEmpty() || location.isEmpty()) {
            Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Use a Database instance to get a connection
        Database database = new Database();
        Connection connection = database.SQLConnection();

        if (connection != null) {
            try {
                // Insert the job into the database with the selected tier
                String query = "INSERT INTO jobs (jobTitle, companyName, jobLocation, tier, job_poster) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, jobTitle);
                preparedStatement.setString(2, company);
                preparedStatement.setString(3, location);
                preparedStatement.setString(4, selectedTier);
                preparedStatement.setString(5, username);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                    int jobId = -1;
                    if (generatedKeys.next()) {
                        jobId = generatedKeys.getInt(1);
                    }
                    insertJobToFirebase(jobId);


                    String fetchQuery = "SELECT * FROM jobs WHERE jobId = ?";
                    PreparedStatement fetchStatement = connection.prepareStatement(fetchQuery);
                    fetchStatement.setInt(1, jobId);
                    ResultSet rs = fetchStatement.executeQuery();

                    if (rs.next()) {
                        int fetchedJobID = rs.getInt("jobId");
                        String fetchedJobTitle = rs.getString("jobTitle");
                        String fetchedCompanyName = rs.getString("companyName");
                        String fetchedJobLocation = rs.getString("jobLocation");
                        String fetchedTier = rs.getString("tier");

                        Job newJob = new Job(fetchedJobID, fetchedJobTitle, fetchedCompanyName, fetchedJobLocation, fetchedTier, username);
                        Job.getJobList().add(newJob);


                        Toast.makeText(requireContext(), "Job posted successfully", Toast.LENGTH_SHORT).show();
                    }

                    generatedKeys.close();
                    fetchStatement.close();
                } else {
                    Toast.makeText(requireContext(), "Failed to post job", Toast.LENGTH_SHORT).show();
                }

                preparedStatement.close();
                connection.close();

            } catch (SQLException e) {
                e.printStackTrace();  // Print the stack trace for detailed error information

                Toast.makeText(requireContext(), "Error posting job: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(requireContext(), "Error connecting to the database", Toast.LENGTH_SHORT).show();
        }
    }

    private void insertJobToFirebase(int jobId) {
        // Get the values from EditText fields
        String jobTitle = etJobTitle.getText().toString().trim();
        String company = etCompany.getText().toString().trim();
        String location = etLocation.getText().toString().trim();
        String selectedTier = tierSpinner.getSelectedItem().toString(); // Get the selected tier from the Spinner

        // Check if all fields are filled
        if (jobTitle.isEmpty() || company.isEmpty() || location.isEmpty()) {
            Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Add the job details under the jobId key under the current user's username
        jobsRef.child(username).child(String.valueOf(jobId)).setValue(new Job(jobId, jobTitle, company, location, selectedTier, username))
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(requireContext(), "Job posted to Firebase successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(requireContext(), "Failed to post job to Firebase", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void updateNotificationStatusForAllUsers() {
        // Set "have_pending_job_notification" to true for all users except the current user
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String userKey = userSnapshot.getKey();
                    if (userKey != null && !userKey.equals(username)) {
                        // Add a key "new_job_posted" with the job title as the value
                        userRef.child(userKey).child("new_job_posted").child(etJobTitle.getText().toString().trim()).setValue(etJobTitle.getText().toString().trim());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(requireContext(), "Failed to update notification status", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
