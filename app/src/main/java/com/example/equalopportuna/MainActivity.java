package com.example.equalopportuna;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {
    Connection con;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Database db = new Database();
        Button btn = (Button)findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                con = db.SQLConnection();
                try {
                    if (con != null) {
                        System.out.println("HI");
                        System.out.println("first");
                        String Q = "Select * from users";
                        Statement st = con.createStatement();
                        ResultSet rs = st.executeQuery(Q);
                        while (rs.next()) {
                            String name = rs.getString("full_name");

                            TextView textView = findViewById(R.id.textView);
                            textView.setText(name);

                        }
                    }
                }catch (Exception e){
                    Log.e("Error", e.getMessage());
                }
            }
        });
    }
}