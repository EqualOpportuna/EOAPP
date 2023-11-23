package com.example.equalopportuna;

public class ForumPostNew {
    private String username;
    private String message;

    public ForumPostNew(String username, String message) {
        this.username = username;
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public String getMessage() {
        return message;
    }
}
