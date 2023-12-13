package com.example.equalopportuna;

import java.util.ArrayList;
import java.util.List;

public class chat {

    private String username;
    private String chatPreview;
    private String chatDate;
    private int imageUrl;

    public chat(String username, String chatPreview, String chatDate, int imageUrl) {
        this.username = username;
        this.chatPreview = chatPreview;
        this.chatDate = chatDate;
        this.imageUrl = imageUrl;
    }

    public String getUsername(){ return username;}
    public String getChatPreview(){return chatPreview;}
    public String getChatDate(){return chatDate;}
    public int getImageUrl(){return imageUrl;}

    public static List<chat> getChatList() {
        List<chat> chatList = new ArrayList<>();

        // testing with sample data
        chatList.add(new chat("Pak Gai Hong", "You: alright thanks for the tip!", "4 Dec", R.drawable.profile_image3));
        chatList.add(new chat("Rendang Ismail", "Rendang: best wishes and good luck!", "19 Nov", R.drawable.profile_image1));
        chatList.add(new chat("Muthu", "Muthu: spill the tea sis", "31 Feb", R.drawable.profile_image2));

        return chatList;
    }
}
