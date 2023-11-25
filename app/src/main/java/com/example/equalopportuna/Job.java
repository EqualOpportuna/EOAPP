package com.example.equalopportuna;

import android.database.Cursor;

public class Job {
    private int jobID;
    private String jobTitle;
    private String companyName;
    private String jobLocation;
    private String tier;

    public Job(int jobID, String jobTitle, String companyName, String jobLocation, String tier) {
        this.jobID = jobID;
        this.jobTitle = jobTitle;
        this.companyName = companyName;
        this.jobLocation = jobLocation;
        this.tier = tier;
    }


    public int getJobID() {
        return jobID;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getJobLocation() {
        return jobLocation;
    }

    public String getTier() {
        return tier;
    }


}
