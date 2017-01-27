package com.kiminonawa.mydiary.Sign;

import android.graphics.Color;

import com.kiminonawa.mydiary.db.DBManager;
import com.kiminonawa.mydiary.main.topic.ITopic;

import java.util.ArrayList;

public class AddCourse {
    DBManager dbManager = new DBManager(MyApplication.getGlobalContext());

    public void add(String day, ArrayList<ArrayList<String>> arr)
    {
        dbManager.opeDB();
        long mitsuhaMemoId = dbManager.insertTopic(day, ITopic.TYPE_MEMO, Color.BLACK);
        //Log.d("hqxhqx",Integer.toString((int)mitsuhaMemoId));
        String checkDay12 = day + "第1,2节";
        String checkDay34 = day + "第3,4节";
        String checkDay345 = day + "第3,4,5节";
        String checkDay67 = day + "第6,7节";
        String checkDay678 = day + "第6,7,8节";
        String checkDay910 = day + "第9,10节";
        String checkDay1112 = day + "第11,12节";
        String checkDay111213 = day + "第11,12,13节";
        check(checkDay111213,arr,mitsuhaMemoId);
        check(checkDay1112,arr,mitsuhaMemoId);
        check(checkDay910,arr,mitsuhaMemoId);
        check(checkDay678,arr,mitsuhaMemoId);
        check(checkDay67,arr,mitsuhaMemoId);
        check(checkDay345,arr,mitsuhaMemoId);
        check(checkDay34,arr,mitsuhaMemoId);
        check(checkDay12,arr,mitsuhaMemoId);
    }
    public void check(String checkDay, ArrayList<ArrayList<String>> arr,long mitsuhaMemoId)
    {
        for (int i = 0; i<arr.size();i++)
        {
            if (arr.get(i).get(2).contains(checkDay)==true) {
                if (mitsuhaMemoId != -1) {
                    dbManager.insertMemo(arr.get(i).toString(), false, mitsuhaMemoId);
                }
                //System.out.println(arr.get(i).get(2));
                //Log.d("hqx",arr.get(i).toString());
                return;
            }
        }
    }
}