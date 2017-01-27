package com.kiminonawa.mydiary.Sign;

import java.util.ArrayList;

public class StringProcess
{
    public static ArrayList<String> timeProcess(String str) {
        int i, j;
        String tmpstr;
        ArrayList<String> al = new ArrayList<>();
        str = str.replace("{单周}", "").replace("{双周}", "");
        i = str.indexOf("周");
        while ((j = str.indexOf("周", i + 1)) != -1) {
            tmpstr = str.substring(i, j);
            al.add(tmpstr);
            i = j;
        }
        al.add(str.substring(i));
        return al;
    }
    public static ArrayList<String> placeProcess(String str)
    {
        int i, j;
        String tmpstr;
        ArrayList<String> al = new ArrayList<>();
        i = str.indexOf("玉泉");
        if (i!=-1)
        {
            while ((j = str.indexOf("玉泉", i + 1)) != -1) {
                tmpstr = str.substring(i, j);
                al.add(tmpstr);
                i = j;
            }
            al.add(str.substring(i));
        }
        else
        {
            i = str.indexOf("紫金港");
            if (i!=-1)
            {
                while ((j = str.indexOf("紫金港", i + 1)) != -1) {
                    tmpstr = str.substring(i, j);
                    al.add(tmpstr);
                    i = j;
                }
                al.add(str.substring(i));
            }
            else
            {
                i = str.indexOf("西溪");
                while ((j = str.indexOf("紫金港", i + 1)) != -1) {
                    tmpstr = str.substring(i, j);
                    al.add(tmpstr);
                    i = j;
                }
                al.add(str.substring(i));
            }
        }
        return al;
    }
}

