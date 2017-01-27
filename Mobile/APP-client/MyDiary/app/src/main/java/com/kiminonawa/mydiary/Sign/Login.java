package com.kiminonawa.mydiary.Sign;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.kiminonawa.mydiary.R;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
    public void SignUp(View view) {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }
    public void LoginIn(View view)
    {
        Intent intent = new Intent(this, LoginIn.class);
        startActivity(intent);
    }
    public void CheckStudent(View view)
    {
        Intent intent = new Intent(this, CheckStudent.class);
        startActivity(intent);
    }
}
