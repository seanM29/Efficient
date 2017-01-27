package com.kiminonawa.mydiary.Sign;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.kiminonawa.mydiary.R;
import com.kiminonawa.mydiary.main.MainActivity;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CheckStudent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_student);
    }
    public void sendMessageCheckStudent(View view)
    {
        int count;
        int tmpj;
        int stringLength=0;
        String[][] str = new String[15][4];
        if(Build.VERSION.SDK_INT >= 11) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads    ().detectDiskWrites().detectNetwork().penaltyLog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
        }
        EditText editText1 = (EditText)findViewById(R.id.username);
        EditText editText2 = (EditText)findViewById(R.id.password);
        String message1 = editText1.getText().toString();
        String message2 = editText2.getText().toString();
        String url="http://www.wanghaobo.cn/extra/%E6%95%99%E5%8A%A1%E7%BD%91%E7%88%AC%E8%99%AB/daemon.php?id="+message1+"&pwd="+message2;
        Map<String,String> params=new HashMap<String, String>();
        String result=HttpUtils.submitPostData(url,params,"utf-8");
        try {
            JSONArray array = new JSONArray(result);
            for (int i =0 ;i<array.length() ;i++){
                stringLength++;
                count = 0;
                tmpj=0;
                JSONArray item = new JSONArray(array.getString(i));
                for (int j =0 ;j<item.length();j++){
                    if(count>0 && count<6 && count!=3)
                        str[i][tmpj++] = item.getString(j);
                    count++;
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        ArrayList<ArrayList<String>> arr = new ArrayList<>();
        for (int i=0;i<stringLength;i++)
        {
            ArrayList<String> time = StringProcess.timeProcess(str[i][2]);
            ArrayList<String> place = StringProcess.placeProcess(str[i][3]);
            for (int j=0; j<time.size();j++)
            {
                ArrayList<String> tmp = new ArrayList<>();
                tmp.add(str[i][0]);
                tmp.add(str[i][1]);
                tmp.add(time.get(j));
                tmp.add(place.get(j));
                arr.add(tmp);
            }
        }
        AddCourse addcourse = new AddCourse();
        addcourse.add("周日",arr);
        addcourse.add("周六",arr);
        addcourse.add("周五",arr);
        addcourse.add("周四",arr);
        addcourse.add("周三",arr);
        addcourse.add("周二",arr);
        addcourse.add("周一",arr);
        Toast.makeText(getApplicationContext(), "核对完成!", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
