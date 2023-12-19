package com.example.equalopportuna;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class forgot_pw_email  extends AppCompatActivity {
    int code;
    private EditText etemail,etotp;
    private Button btnchange;
    private Button btnback;


    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_pw);

        etemail = findViewById(R.id.et_email);
        etotp = findViewById(R.id.et_otp);
        Button btnproceed = findViewById(R.id.btn_proceed);
        btnchange = findViewById(R.id.btn_change);

        TextView btnback= findViewById(R.id.or_login);
        btnback.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(forgot_pw_email.this,login_page.class));
            }
        });

        btnproceed.setOnClickListener(new View.OnClickListener(){
            public void onClick(View V){
                sendVerifyEmail();
                checkCode();
            }
        });

    }

    public void sendVerifyEmail(){
        Random random = new Random();
        code = random.nextInt(8999)+1000;
        EditText emailTXT = findViewById(R.id.et_email);
        String url = "https://equalopportuna1234.000webhostapp.com/otp/sendEmail.php";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url, new Response.Listener<String>(){

            public void onResponse(String response){
                Toast.makeText(forgot_pw_email.this,response,Toast.LENGTH_SHORT).show();
                findViewById(R.id.box1).setVisibility(View.GONE);
                findViewById(R.id.box2).setVisibility(View.VISIBLE);
            }
        }, new Response.ErrorListener(){
            public void onErrorResponse(VolleyError error){
                Toast.makeText(forgot_pw_email.this,error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("email",emailTXT.getText().toString());
                params.put("code",String.valueOf(code));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void checkCode(){
        String inputCode = String.valueOf(findViewById(R.id.et_otp));
        if(inputCode.equals(String.valueOf(code))){
            Toast.makeText(this,"Success",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show();
        }
    }
}
