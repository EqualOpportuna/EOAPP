package com.example.equalopportuna;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class chatFragment extends Fragment {
    RecyclerView RecyclerChat; // Rename RecyclerFriends to RecyclerChat

    public chatFragment() {
        // Required empty public constructor
    }

    public static chatFragment newInstance() {
        return new chatFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false); // Use fragment_chat.xml

        RecyclerChat = view.findViewById(R.id.RecyclerChat); // Use RecyclerChat

        // Fetch chat data from the static list (Replace this with your actual data fetching logic)
        List<chatFragment> chatList = getChatList();

        // RecyclerView adapter
        chat_adapter chatAdapter = new chat_adapter(chatList, requireContext());
        RecyclerChat.setAdapter(chatAdapter);
        RecyclerChat.setLayoutManager(new LinearLayoutManager(requireContext()));

        return view;
    }

    // Add getter methods or replace this with your actual data fetching logic
    private List<chatFragment> getChatList() {
        // Replace this with your actual data fetching logic
        // For example, you might fetch chat data from a database or API
        // and return a list of chatFragment objects.
        return null;
    }

    // Add getter methods or replace this with your actual data
    public String getUsername() {
        // Replace this with your actual username logic
        return "Username";
    }

    public String getChatPreview() {
        // Replace this with your actual chat preview logic
        return "You: hahaha glhf!";
    }

    public String getChatDate() {
        // Replace this with your actual chat date logic
        return "Dec 4";
    }
}
