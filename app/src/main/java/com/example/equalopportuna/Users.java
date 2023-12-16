package com.example.equalopportuna;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Users {
    private static List<Users> userList = new ArrayList<>();

    private String username;
    private String careerDescription;
    private int imageUrl;

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

    public static List<Users> getAllUsers() {
        return userList;
    }

    public static void setAllUsers(List<Users> usersList) {
        userList = usersList;
    }

    // Method to fetch all users from the database
    public static List<Users> getAllUsersFromDatabase(Connection connection, String loggedInFullName) {
        List<Users> usersList = new ArrayList<>();

        if (connection != null) {
            try {
                String query = "SELECT full_name FROM users";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    String username = resultSet.getString("full_name");

                    // Exclude the currently logged-in user
                    if (!username.equals(loggedInFullName)) {
                        // Add other properties as needed
                        Users user = new Users(username, "Worker", R.drawable.profile_image1);
                        usersList.add(user);
                    }
                }

                resultSet.close();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return usersList;
}
}
