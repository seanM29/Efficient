package com.kiminonawa.mydiary.Sign;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.kiminonawa.mydiary.R;

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }
    public void sendMessageSignUp(View view)
    {
        EditText editText_username = (EditText)findViewById(R.id.username);
        String username = editText_username.getText().toString();
        EditText editText_password = (EditText)findViewById(R.id.password);
        String password = editText_password.getText().toString();
        EditText editText_telephone = (EditText)findViewById(R.id.telephone);
        String telephone = editText_telephone.getText().toString();
        Message m = new Message();
        m.setName(username);
        m.setPassword(password);
        m.setPhone(telephone);
        m.setType(1);
        Log.d("HQX",m.getPhone());

        CallService call=  new CallService(m);
        call.start();
        Message result = call.getResult();
    }
}
