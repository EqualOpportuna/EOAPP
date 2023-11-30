package com.example.equalopportuna;

class Model {
    String name;
    String date;
    String feedback;
    int image;


    public Model(String name, String date, String feedback, int image) {
        this.name = name;
        this.date = date;
        this.feedback = feedback;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getFeedback() {
        return feedback;
    }

    public int getImage() {
        return image;
    }
}
