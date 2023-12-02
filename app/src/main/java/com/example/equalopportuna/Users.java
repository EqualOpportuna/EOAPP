package com.example.equalopportuna;

import java.util.ArrayList;
import java.util.List;

public class Users {
    private String username;
    private String careerDescription;
    private String imageUrl;

    public Users(String username, String careerDescription, String imageUrl) {
        this.username = username;
        this.careerDescription = careerDescription;
        this.imageUrl = imageUrl;
    }

    public String getUsername() {
        return username;
    }

    public String getCareerDescription() {
        return careerDescription;
    }

    public String getImageUrl() {
        return imageUrl;
    }
    public static List<Users> getUserList() {
        List<Users> userList = new ArrayList<>();

        return userList;
    }
    Users user1 = new Users("JohnDoe", "Software Engineer", "https://www.gstatic.com/android/keyboard/emojikitchen/20221101/u1f98a/u1f98a_u1f920.png?fbx");
    Users user2 = new Users("AliceSmith", "Graphic Designer", "https://www.gstatic.com/android/keyboard/emojikitchen/20201001/u1f60e/u1f60e_u1f435.png?fbx");

}
