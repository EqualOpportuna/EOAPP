package com.example.equalopportuna;

public class Model {
    private String courseName;
    private String courseDate;
    private String courseFeedback;
    private String link;
    private String description;

    private String recommended;
    int image;


    public Model(String courseName, String courseDate, String courseFeedback, String link, String description, String recommendedCourse) {
        this.courseName = courseName;
        this.courseDate = courseDate;
        this.courseFeedback = courseFeedback;
        this.link = link;
        this.description = description;
        this.recommended = recommendedCourse;

    }



    public String getCourseName(){return courseName;}
    public String getCourseDate(){return courseDate;}
    public String getCourseFeedback(){return courseFeedback;}
    public String getLink(){return link;}
    public String getDescription(){return description;}
    public String getRecommendedCourse() { return recommended;}



}

