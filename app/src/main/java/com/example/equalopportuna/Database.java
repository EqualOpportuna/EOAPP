package com.example.equalopportuna;

import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Database {
    Connection con;

    public Connection SQLConnection() {
        StrictMode.ThreadPolicy tp = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(tp);

        String ConURL = "jdbc:mysql://114.132.171.90:3306/equalopportuna";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(ConURL, "root", "um123456");
        } catch (ClassNotFoundException e) {
            Log.e("Error", "MySQL JDBC Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            Log.e("Error", "Error connecting to the database: " + e.getMessage());
        }

        return con;
    }

    public List<Job> getJobData() {
        List<Job> jobList = new ArrayList<>();

        // Use the existing connection or create a new one if needed
        Connection connection = con != null ? con : SQLConnection();

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM jobs");

            while (rs.next()) {
                int jobID = rs.getInt("jobId");
                String jobTitle = rs.getString("jobTitle");
                String companyName = rs.getString("companyName");
                String jobLocation = rs.getString("jobLocation");

                String tier = rs.getString("tier");

                Job job = new Job(jobID, jobTitle, companyName, jobLocation, tier);
                jobList.add(job);
            }

            if (con == null) {
                connection.close();
            }

        } catch (SQLException e) {
            Log.e("Error", "Error fetching job data: " + e.getMessage());
        }

        return jobList;
    }

}
