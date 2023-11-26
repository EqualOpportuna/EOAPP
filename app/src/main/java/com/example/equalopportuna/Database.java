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


}
