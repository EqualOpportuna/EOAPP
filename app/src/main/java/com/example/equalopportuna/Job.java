package com.example.equalopportuna;

import android.util.Log;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Job {
    private int jobID;
    private String jobTitle;
    private String companyName;
    private String jobLocation;
    private String tier;
    private String name;

    private static List<Job> jobList = new ArrayList<>();


    public Job(int jobID, String jobTitle, String companyName, String jobLocation, String tier, String name) {
        this.jobID = jobID;
        this.jobTitle = jobTitle;
        this.companyName = companyName;
        this.jobLocation = jobLocation;
        this.tier = tier;
        this.name = name;
    }
    public Job(){}

    public void fetchAndStoreJobData(Connection connection) {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM jobs");

            jobList.clear(); // Clear the existing list before fetching new data

            while (rs.next()) {
                int jobID = rs.getInt("jobId");
                String jobTitle = rs.getString("jobTitle");
                String companyName = rs.getString("companyName");
                String jobLocation = rs.getString("jobLocation");
                String tier = rs.getString("tier");
                String name = rs.getString("job_poster");

                Job job = new Job(jobID, jobTitle, companyName, jobLocation, tier, name);
                jobList.add(job);
            }

            stmt.close();

        } catch (SQLException e) {
            Log.e("Error", "Error fetching and storing job data: " + e.getMessage());
        }
    }

    // New method to fetch job data
    public static List<Job> getJobList() {
        return jobList;
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
    public String getName() { return name; }

}
