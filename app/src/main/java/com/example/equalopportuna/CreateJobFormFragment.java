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

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreateJobFormFragment extends Fragment {

    private EditText etJobTitle, etCompany, etLocation;
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
        Button jobPostBtn = view.findViewById(R.id.jobPostBtn);
        tierSpinner = view.findViewById(R.id.tierSpinner); // Add this line to initialize the Spinner

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
                // Call a method to insert the job into the database
                insertJobToDatabase();
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

        // Use a Database instance to get a connection
        Database database = new Database();
        Connection connection = database.SQLConnection();

        if (connection != null) {
            try {
                // Insert the job into the database with the selected tier
                String query = "INSERT INTO jobs (jobTitle, companyName, jobLocation, tier) VALUES (?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, jobTitle);
                preparedStatement.setString(2, company);
                preparedStatement.setString(3, location);
                preparedStatement.setString(4, selectedTier);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    Toast.makeText(requireContext(), "Job posted successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(requireContext(), "Failed to post job", Toast.LENGTH_SHORT).show();
                }

                preparedStatement.close();
                connection.close();

            } catch (SQLException e) {
                Toast.makeText(requireContext(), "Error posting job: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(requireContext(), "Error connecting to the database", Toast.LENGTH_SHORT).show();
        }
    }
}
