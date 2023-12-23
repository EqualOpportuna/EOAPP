package com.example.equalopportuna;

public class HelperClass {
    String full_name, email;

    public HelperClass(){}

    public HelperClass(String full_name, String email) {
        this.full_name = full_name;
        this.email = email;

    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
