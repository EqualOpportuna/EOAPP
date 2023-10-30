package com.example.equalopportuna;

import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {
    Connection con;

    public Connection SQLConnection(){
        StrictMode.ThreadPolicy tp = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(tp);
        String ConURL = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            ConURL = "jdbc:mysql://sql12.freesqldatabase.com:3306/sql12657308";
            con = DriverManager.getConnection(ConURL, "sql12657308", "sdm55pTuPS");
        } catch (Exception e){
            Log.e("Error", e.getMessage());
        }
        return con;
    }
}
