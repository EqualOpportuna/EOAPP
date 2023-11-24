package com.example.equalopportuna;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateJobFormFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateJobFormFragment extends Fragment {

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

        // Set OnClickListener for the ImageButton
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate to JobOpeningsFragment
                Navigation.findNavController(view).navigate(R.id.jobOpeningsFragment);
            }
        });

        // Add the rest of your code...

        return view;
    }
}
