package com.example.equalopportuna;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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
    EditText passCode1;
    EditText passCode2;
    EditText passCode3;
    EditText passCode4;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_pw);
    }

    public void sendVerifyEmail(View view){
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

    public void checkCode(View view){
        String inputCode = String.valueOf(findViewById(R.id.et_otp));
        if(inputCode.equals(String.valueOf(code))){
            Toast.makeText(this,"Success",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show();
        }
    }
}
