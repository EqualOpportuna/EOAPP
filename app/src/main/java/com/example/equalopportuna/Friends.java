package com.example.equalopportuna;

import java.util.ArrayList;
import java.util.List;

public class Friends {
    private String username;
    private String careerDescription;
    private int imageUrl;
    private int connectionPeriod;

    public Friends(String username, String careerDescription, int imageUrl, int connectionPeriod) {
        this.username = username;
        this.careerDescription = careerDescription;
        this.imageUrl = imageUrl;
        this.connectionPeriod = connectionPeriod;
    }

    public String getUsername() {
        return username;
    }

    public String getCareerDescription() {
        return careerDescription;
    }

    public int getImageUrl() {
        return imageUrl;
    }

    public int getConnectionPeriod() {
        return connectionPeriod;
    }


    // Other getters and methods...

    public static List<Friends> getUserList() {
        List<Friends> userList = new ArrayList<>();

        // testing with sample data
        userList.add(new Friends("Lay How Ye", "Engineer", R.drawable.profile_image1, 3));
        userList.add(new Friends("Xiu Mui Mui", "Designer", R.drawable.profile_image2, 7));
        userList.add(new Friends("Ding Ngo Fai", "Developer", R.drawable.profile_image3, 1));

        return userList;
    }
}
