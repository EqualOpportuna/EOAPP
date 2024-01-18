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
    private String avatarName;

    public Users(String username, String careerDescription, String avatarName) {
        this.username = username;
        this.careerDescription = careerDescription;
        this.avatarName = avatarName;
    }

    public String getUsername() {
        return username;
    }

    public String getCareerDescription() {
        return careerDescription;
    }

    public String getAvatarName() {
        return avatarName;
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
                String query = "SELECT full_name, career_description, avatar FROM users";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    String username = resultSet.getString("full_name");
                    String career_desc = resultSet.getString("career_description");
                    String avatar = resultSet.getString("avatar");

                    // Exclude the currently logged-in user
                    if (!username.equals(loggedInFullName)) {
                        // Add other properties as needed
                            Users user = new Users(username, career_desc, avatar);
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
public static void clearUsers(){
        userList.clear();
}
}
