package com.example.equalopportuna;

public class Model {
    private String courseName;
    private String courseDate;
    private String courseFeedback;
    int image;


    public Model(String courseName, String courseDate, String courseFeedback, int image) {
        this.courseName = courseName;
        this.courseDate = courseDate;
        this.courseFeedback = courseFeedback;
        this.image = image;
    }

    public Model(String courseName, String courseDate, String courseFeedback) {
        this.courseName = courseName;
        this.courseDate = courseDate;
        this.courseFeedback= courseFeedback;
    }

    public String getCourseName(){return courseName;}
    public String getCourseDate(){return courseDate;}
    public String getCourseFeedback(){return courseFeedback;}

    public int getImage() {
        return image;
    }

}

