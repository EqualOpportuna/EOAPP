package com.example.equalopportuna;

import java.util.ArrayList;
import java.util.List;

public class Users {
    private String username;
    private String careerDescription;
    private int imageUrl;   //atm using int because i just wanna test things out; change back to strings later


    public Users(String username, String careerDescription, int imageUrl) {
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

    public int getImageUrl() {
        return imageUrl;
    }
    public static List<Users> getUserList() {
        List<Users> userList = new ArrayList<>();

        // testing with sample data
        userList.add(new Users("Ali Abu Bakar", "Engineer", R.drawable.profile_image1));
        userList.add(new Users("Ching Ching Mai", "Designer", R.drawable.profile_image2));
        userList.add(new Users("Muthu", "Developer", R.drawable.profile_image3));

        return userList;
    }
}
