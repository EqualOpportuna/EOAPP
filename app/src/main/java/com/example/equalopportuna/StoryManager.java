// StoryManager.java
package com.example.equalopportuna;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StoryManager {

    private static final String STORY_PREFS_NAME = "StoryPrefs";
    private static final String KEY_STORY_ID = "storyId";
    private static final String KEY_USER_ID = "userId";
    private static final String KEY_MESSAGE = "message";

    private static StoryManager instance;
    private final SharedPreferences sharedPreferences;

    private static List<ForumPostNew> storyList = new ArrayList<>();

    private StoryManager(Context context) {
        sharedPreferences = context.getSharedPreferences(STORY_PREFS_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized StoryManager getInstance(Context context) {
        if (instance == null) {
            instance = new StoryManager(context);
        }
        return instance;
    }

    public void saveStoryInfo(int storyId, int userId, String message) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_STORY_ID, storyId);
        editor.putInt(KEY_USER_ID, userId);
        editor.putString(KEY_MESSAGE, message);
        editor.apply();
    }

    public int getStoryId() {
        return sharedPreferences.getInt(KEY_STORY_ID, -1); // Default value if not found
    }

    public int getUserId() {
        return sharedPreferences.getInt(KEY_USER_ID, -1); // Default value if not found
    }

    public String getMessage() {
        return sharedPreferences.getString(KEY_MESSAGE, "");
    }

    public static void fetchAndStoreStoryData(Context context, Connection connection) {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM stories");

            storyList.clear(); // Clear the existing list before fetching new data

            while (rs.next()) {
                int storyId = rs.getInt("id");
                int userId = rs.getInt("user_id");
                String message = rs.getString("message");

                ForumPostNew story = new ForumPostNew(getUsernameById(context, userId), message, userId);
                storyList.add(story);
            }

            stmt.close();

        } catch (SQLException e) {
            Log.e("Error", "Error fetching and storing story data: " + e.getMessage());
        }
    }

    // New method to fetch story data
    public static List<ForumPostNew> getStoryList() {
        return storyList;
    }

    // New method to fetch the username by user ID
    private static String getUsernameById(Context context, int userId) {
        Database database = new Database();
        Connection connection = database.SQLConnection();

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT full_name FROM users WHERE id = " + userId);

            if (rs.next()) {
                String username = rs.getString("full_name");
                rs.close();
                stmt.close();
                return username;
            } else {
                rs.close();
                stmt.close();
                return "Unknown"; // Placeholder if the user is not found
            }

        } catch (SQLException e) {
            Log.e("Error", "Error fetching username by ID: " + e.getMessage());
            return "Error"; // Placeholder for error handling
        } finally {
            // Close the connection when done
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                Log.e("Error", "Error closing connection: " + e.getMessage());
            }
        }
    }




}
