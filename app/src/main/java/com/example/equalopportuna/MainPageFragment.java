package com.example.equalopportuna;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainPageFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int PICK_IMAGE_REQUEST = 1;

    private String mParam1;
    private String mParam2;

    private CircleImageView profileImageView;
    private TextView uploadButton;
    private TextView welcomeTextView;

    private TextView shortIntro;
    private TextView edu_ex;

    private UserManager userManager;

    public MainPageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();

        // Load the avatar when the fragment is resumed
        loadAvatar();
    }

    private void loadAvatar() {
        // Get user information from UserViewModel
        String avatarFileName = userManager.getAvatarFileName(); // Modify this based on your actual implementation

        if (avatarFileName != null) {
            // Update the profile image view
            int resId = getResources().getIdentifier(avatarFileName, "drawable", requireActivity().getPackageName());
            profileImageView.setImageResource(resId);
        }
    }

    public static MainPageFragment newInstance(String param1, String param2) {
        MainPageFragment fragment = new MainPageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userManager = UserManager.getInstance(requireContext());

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_page, container, false);

        profileImageView = view.findViewById(R.id.profileImageView);
        uploadButton = view.findViewById(R.id.uploadButton);
        welcomeTextView = view.findViewById(R.id.welcomeTextView);
        shortIntro = view.findViewById(R.id.shortIntro);
        edu_ex = view.findViewById(R.id.edu_desc);


        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAvatarPickerDialog();
            }
        });

        // Get user information from UserViewModel
        int userId = userManager.getUserId();
        String fullName = userManager.getFullName();
        welcomeTextView.setText("Hey " + fullName + "!");
        shortIntro.setText(userManager.getShortIntro());
        edu_ex.setText(userManager.getExperienceEducation());

        shortIntro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show toast when shortIntro is clicked
                showEditableDialog("Short Introduction", shortIntro, "short_intro");
            }
        });

        edu_ex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show toast when edu_ex is clicked
                showEditableDialog("Education and Experience", edu_ex, "experience_education");
            }
        });

        return view;
    }

    private void showAvatarPickerDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Choose Avatar");

        final String[] avatars = {"profile_image1", "profile_image2", "profile_image3", "profile_image4", "profile_image5", "profile_image6", "profile_image7", "profile_image8", "profile_image9", "profile_image10"};

        builder.setItems(avatars, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String selectedAvatar = avatars[which];

                saveAvatarToDatabase(selectedAvatar);
                userManager.saveAvatarFileName(selectedAvatar);

                loadAvatar();
            }
        });

        builder.show();
    }

    private void saveAvatarToDatabase(String selectedAvatar) {
        // Assuming you have a Database instance, replace "yourPackageName" with your actual package name
        Database database = new Database();

        try {
            Connection connection = database.SQLConnection();

            if (connection != null) {
                // Get user ID from UserManager (modify this based on your actual implementation)
                int userId = userManager.getUserId();

                // Update the avatar information in the database
                String updateQuery = "UPDATE users SET avatar = ? WHERE id = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                    preparedStatement.setString(1, selectedAvatar);
                    preparedStatement.setInt(2, userId);
                    preparedStatement.executeUpdate();
                }

                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            // Handle the selected image URI, you can set it to the profileImageView or perform other actions.
            profileImageView.setImageURI(imageUri);
        }
    }

    private void showEditableDialog(String title, TextView textView, String columnName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle(title);

        // Set up the input
        final EditText input = new EditText(requireContext());
        input.setText(textView.getText());
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newValue = input.getText().toString();
                // Update the corresponding column in the database
                updateColumnInDatabase(columnName, newValue);
                // Update the TextView with the new value
                textView.setText(newValue);

                if (columnName.equals("short_intro")) {
                    userManager.setShortIntro(newValue);
                } else if (columnName.equals("experience_education")) {
                    userManager.setExperienceEducation(newValue);
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void updateColumnInDatabase(String columnName, String newValue) {
        // Assuming you have a Database instance, replace "yourPackageName" with your actual package name
        Database database = new Database();

        try {
            Connection connection = database.SQLConnection();

            if (connection != null) {
                // Get user ID from UserManager (modify this based on your actual implementation)
                int userId = userManager.getUserId();

                // Update the specified column in the database
                String updateQuery = "UPDATE users SET " + columnName + " = ? WHERE id = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                    preparedStatement.setString(1, newValue);
                    preparedStatement.setInt(2, userId);
                    preparedStatement.executeUpdate();
                }

                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
