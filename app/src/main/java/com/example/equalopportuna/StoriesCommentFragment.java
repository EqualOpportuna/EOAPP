package com.example.equalopportuna;

import android.os.Bundle;

import androidx.appcompat.view.menu.MenuView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class StoriesCommentFragment extends Fragment {

    private String username;
    private String message;

    public StoriesCommentFragment() {
        // Required empty public constructor
    }

    public static StoriesCommentFragment newInstance(String param1, String param2) {
        StoriesCommentFragment fragment = new StoriesCommentFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve the username from the Bundle
        if (getArguments() != null) {
            username = getArguments().getString("username");
            message = getArguments().getString("message");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stories_comment, container, false);

        // Set the retrieved username to the TextView
        setUsername(view, username);
        setMessage(view, message);

        return view;
    }

    public void setUsername(View view, String username) {
        TextView tvUsername = view.findViewById(R.id.TVUsername);
        if (tvUsername != null) {
            tvUsername.setText(username);
        }
    }

    public void setMessage(View view, String message){
        TextView tvStory = view.findViewById(R.id.TVStory);
        if(tvStory != null){
            tvStory.setText(message);
        }
    }
}
