package com.example.equalopportuna;

public class itemCourseLisiting {
    String nameOfCourse;
    String feedbackOfCourese;

    int icon;

    public itemCourseLisiting(String nameOfCourse, int icon, String feedbackOfCoures ) {
        this.nameOfCourse = nameOfCourse;
        this.icon = icon;
        this.feedbackOfCourese = feedbackOfCourese;
    }

    public String getFeedbackOfCourese() {
        return feedbackOfCourese;
    }

    public void setFeedbackOfCourese(String feedbackOfCourese) {
        this.feedbackOfCourese = feedbackOfCourese;
    }

    public String getNameOfCourse() {
        return nameOfCourse;
    }

    public void setNameOfCourse(String nameOfCourse) {
        this.nameOfCourse = nameOfCourse;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
