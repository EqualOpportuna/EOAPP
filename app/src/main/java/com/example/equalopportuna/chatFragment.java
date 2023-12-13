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

        // Fetch com.example.equalopportuna.chat data from the static list
        List<chat> chatList = chat.getChatList();

        // RecyclerView adapter
        chat_adapter chatAdapter = new chat_adapter(chatList, requireContext());
        RecyclerChat.setAdapter(chatAdapter);
        RecyclerChat.setLayoutManager(new LinearLayoutManager(requireContext()));

        return view;
    }

    private List<chatFragment> getChatList() {

        return null;
    }

    public String getUsername() {
        return "Username";
    }

    public String getChatPreview() {
        return "You: hahaha glhf!";
    }

    public String getChatDate() {
        return "Dec 4";
    }
}
