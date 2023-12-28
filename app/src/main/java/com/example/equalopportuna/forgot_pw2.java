package com.example.equalopportuna;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;


public class forgot_pw2 extends AppCompatActivity {

    int code;
    public EditText mEmail, mSubject, mMessage;

    private Button btnchange, btnproceed, btnback;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_pw);
        mEmail = (EditText)findViewById(R.id.et_email);

        btnproceed = findViewById(R.id.btn_proceed);
        btnchange = findViewById(R.id.btn_change);


        btnproceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMail();
            }
        });

        btnchange.setOnClickListener(new View.OnClickListener(){
            public void onCLick(View view){
                checkCode();
            }
        })
    }

    private void sendMail() {

        String mail = mEmail.getText().toString().trim();
        String subject = "EqualOpportuna OTP Code";

        //Generate random code
        Random random = new Random();
        code = random.nextInt(899999)+100000;
        String temp = String.valueOf(code);

        //Send Mail
        JavaMailAPI javaMailAPI = new JavaMailAPI(this,mail,subject,temp);

        javaMailAPI.execute();

        public void onResponse(String response){
                Toast.makeText(forgot_pw_email.this,response,Toast.LENGTH_SHORT).show();
                findViewById(R.id.box1).setVisibility(View.GONE);
                findViewById(R.id.box2).setVisibility(View.VISIBLE);
            }

    }

    public void checkCode(){
        String inputCode = String.valueOf(findViewById(R.id.et_otp));
        if(inputCode.equals(String.valueOf(code))){
            Toast.makeText(this,"Success",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(forgot_pw2.this,change_pw.class))
        }else{
            Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show();
        }
    }

}
