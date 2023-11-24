package com.example.equalopportuna;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
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

    private UserManager userManager;

    public MainPageFragment() {
        // Required empty public constructor
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

        // Initialize UserViewModel

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_page, container, false);

        profileImageView = view.findViewById(R.id.profileImageView);
        uploadButton = view.findViewById(R.id.uploadButton);
        welcomeTextView = view.findViewById(R.id.welcomeTextView);

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        // Get user information from UserViewModel
        int userId = userManager.getUserId();
        String fullName = userManager.getFullName();
        welcomeTextView.setText("Welcome, " + fullName + "!");

        return view;
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
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
}
