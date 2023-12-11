package com.example.equalopportuna;

public class ForumPostNew {
    private int user_id;
    private String username;
    private String message;

    public ForumPostNew(String username, String message, int user_id) {
        this.username = username;
        this.message = message;
        this.user_id = user_id;

    }

    public String getUsername() {
        return username;
    }

    public String getMessage() {
        return message;
    }

    public int getUserId() { return user_id; }
}
