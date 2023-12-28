package com.example.equalopportuna;
// UserManager.java
import android.content.Context;
import android.content.SharedPreferences;

public class UserManager {

    private static final String USER_PREFS_NAME = "UserPrefs";
    private static final String KEY_USER_ID = "userId";
    private static final String KEY_FULL_NAME = "fullName";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_DATE_OF_BIRTH = "dateOfBirth";

    private static UserManager instance;
    private final SharedPreferences sharedPreferences;

    private UserManager(Context context) {
        sharedPreferences = context.getSharedPreferences(USER_PREFS_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized UserManager getInstance(Context context) {
        if (instance == null) {
            instance = new UserManager(context);
        }
        return instance;
    }

    public void saveUserInfo(int userId, String fullName, String email, String dateOfBirth) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_USER_ID, userId);
        editor.putString(KEY_FULL_NAME, fullName);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_DATE_OF_BIRTH, dateOfBirth);
        editor.apply();
    }

    public int getUserId() {
        return sharedPreferences.getInt(KEY_USER_ID, -1); // Default value if not found
    }

    public String getFullName() {
        return sharedPreferences.getString(KEY_FULL_NAME, "");
    }

    public String getEmail() {
        return sharedPreferences.getString(KEY_EMAIL, "");
    }

    public String getDateOfBirth() {
        return sharedPreferences.getString(KEY_DATE_OF_BIRTH, "");
    }
}
