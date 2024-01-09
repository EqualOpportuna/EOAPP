package com.example.equalopportuna;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;

public class UserManager {

    private static final String USER_PREFS_NAME = "UserPrefs";
    private static final String KEY_USER_ID = "userId";
    private static final String KEY_FULL_NAME = "fullName";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_DATE_OF_BIRTH = "dateOfBirth";

    private static final String KEY_CAREER_DESC = "careerDesc";
    private static final String KEY_SHORT_INTRO = "shortIntro";
    private static final String KEY_EXPERIENCE_EDUCATION = "experienceEducation";

    private static final String KEY_AVATAR_FILENAME = "avatarFileName";
    private static final String KEY_ZODIAC_FILENAME = "zodiacFileName";


    private static UserManager instance;
    private final SharedPreferences sharedPreferences;

    private UserManager(Context context) {
        sharedPreferences = context.getSharedPreferences(USER_PREFS_NAME, Context.MODE_PRIVATE);
    }
    public void onclickNewEx(View view){

    }

    public void setShortIntro(String shortIntro) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_SHORT_INTRO, shortIntro);
        editor.apply();
    }

    public void setExperienceEducation(String experienceEducation) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_EXPERIENCE_EDUCATION, experienceEducation);
        editor.apply();
    }

    public static synchronized UserManager getInstance(Context context) {
        if (instance == null) {
            instance = new UserManager(context);
        }
        return instance;
    }

    public void saveUserInfo(int userId, String fullName, String email, String dateOfBirth, String career_desc,String avatarFileName, String shortIntro, String experienceEducation, String zodiac) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_USER_ID, userId);
        editor.putString(KEY_FULL_NAME, fullName);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_DATE_OF_BIRTH, dateOfBirth);
        editor.putString(KEY_CAREER_DESC, career_desc);
        editor.putString(KEY_AVATAR_FILENAME, avatarFileName);
        editor.putString(KEY_SHORT_INTRO, shortIntro);
        editor.putString(KEY_EXPERIENCE_EDUCATION, experienceEducation);
        editor.putString(KEY_ZODIAC_FILENAME, zodiac);
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

    public String getShortIntro() { return sharedPreferences.getString(KEY_SHORT_INTRO, "");}

    public String getExperienceEducation() { return sharedPreferences.getString(KEY_EXPERIENCE_EDUCATION, "");}

    public String getAvatarFileName() {
        return sharedPreferences.getString(KEY_AVATAR_FILENAME, null);
    }
    public String getZodiacFilename(){ return sharedPreferences.getString(KEY_ZODIAC_FILENAME, null);}

    public void saveAvatarFileName(String avatarFileName) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_AVATAR_FILENAME, avatarFileName);
        editor.apply();
    }
    public void saveZodiacFileName(String zodiacFileName) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_ZODIAC_FILENAME, zodiacFileName);
        editor.apply();
    }
}
