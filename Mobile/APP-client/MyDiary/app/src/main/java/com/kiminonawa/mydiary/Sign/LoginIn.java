package com.kiminonawa.mydiary.Sign;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.kiminonawa.mydiary.R;

import static com.kiminonawa.mydiary.R.id.username;

public class LoginIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_in);
    }
    public void sendMessageLoginIn(View view)
    {
        EditText editText_username = (EditText)findViewById(username);
        String username = editText_username.getText().toString();
        EditText editText_password = (EditText)findViewById(R.id.password);
        String password = editText_password.getText().toString();
        Message m = new Message();
        m.setName(username);
        m.setPassword(password);
        m.setType(2);
        CallService call=  new CallService(m);
        call.start();
        try {
            call.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Message result = call.getResult();

        //登录成功
        if(result.type==0)
        {
            //Log.d("HQXTEST",result.getName());
            test(username);
        }
    }

    private void test(String username){
        UpdateMonitor updateMonitor = new UpdateMonitor(username);
        updateMonitor.start();
    }
}
