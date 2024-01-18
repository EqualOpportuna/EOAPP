package com.example.equalopportuna;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Friends extends Users {
    private String connectionDate; // Assuming connectionDate is a String representing the date
    private static List<Friends> friendList = new ArrayList<>();

    public Friends(String username, String careerDescription, String avatarName, String connectionDate) {
        super(username, careerDescription, avatarName);
        this.connectionDate = connectionDate;
    }

    public String getConnectionPeriod() {
        // Assuming connectionDate is in the format "yyyy-MM-dd HH:mm:ss"
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date connectionDateTime = sdf.parse(connectionDate);
            Date currentDateTime = new Date();

            long diffInMillis = currentDateTime.getTime() - connectionDateTime.getTime();
            long diffInSeconds = diffInMillis / 1000;
            long diffInMinutes = diffInSeconds / 60;
            long diffInHours = diffInMinutes / 60;
            long diffInDays = diffInHours / 24;

            if (diffInDays > 30) {
                long diffInMonths = diffInDays / 30;
                long remainingDays = diffInDays % 30;
                return String.format("%d months, %d days ago", diffInMonths, remainingDays);
            } else if (diffInDays > 0) {
                return String.format("%d days, %d hours ago", diffInDays, diffInHours % 24);
            } else if (diffInHours > 0) {
                return String.format("%d hours, %d minutes ago", diffInHours, diffInMinutes % 60);
            } else {
                return String.format("%d minutes ago", diffInMinutes % 60);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return ""; // Handle the case where parsing fails
    }

    public static void setAllFriends(List<Friends> friendsList) {
        friendList = friendsList;
    }

    public static List<Friends> getFriendList() {
        return friendList;
    }

    public static List<Friends> getAllFriendsFromDatabase(Connection connection, String loggedInFullName) {
        List<Friends> friendsList = new ArrayList<>();

        if (connection != null) {
            try {
                // Get the user ID based on the full name
                String getUserIdQuery = "SELECT id FROM users WHERE full_name = ?";
                try (PreparedStatement getUserIdStatement = connection.prepareStatement(getUserIdQuery)) {
                    getUserIdStatement.setString(1, loggedInFullName);

                    try (ResultSet userIdResultSet = getUserIdStatement.executeQuery()) {
                        if (userIdResultSet.next()) {
                            int loggedInUserId = userIdResultSet.getInt("id");

                            // Now execute the main query to get friends
                            String query = "SELECT\n" +
                                    "    u.id, \n" + // Include the user ID in the result set
                                    "    u.full_name,\n" +
                                    "    u.career_description,\n" +
                                    "    u.avatar,\n" +
                                    "    f.connection_date,\n" +
                                    "    f.status\n" +
                                    "FROM\n" +
                                    "    friends f\n" +
                                    "    JOIN users u ON (f.user_id1 = u.id OR f.user_id2 = u.id)\n" +
                                    "WHERE\n" +
                                    "    (f.user_id1 = ? OR f.user_id2 = ?)\n" +
                                    "    AND (f.status = 'Connected');";


                            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                                preparedStatement.setInt(1, loggedInUserId);
                                preparedStatement.setInt(2, loggedInUserId);

                                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                                    // Add logs to check if resultSet is empty or not
                                    if (!resultSet.next()) {
                                    } else {
                                        do {
                                            int friendUserId = resultSet.getInt("id");

                                            if (friendUserId != loggedInUserId) {


                                                String username = resultSet.getString("full_name");
                                                String career_desc = resultSet.getString("career_description");
                                                String avatar = resultSet.getString("avatar");
                                                String connectionDate = resultSet.getString("connection_date");


                                                Friends friend = new Friends(username, career_desc, avatar, connectionDate);
                                                friendsList.add(friend);
                                            }
                                        } while (resultSet.next());
                                    }
                                }
                            }
                        } else {
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } finally {

            }
        }

        return friendsList;
    }
    public static void clearFriends(){
        friendList.clear();
    }


}
