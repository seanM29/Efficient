package com.kiminonawa.mydiary.Sign;

import android.util.Log;

import com.kiminonawa.mydiary.db.DBManager;
import com.kiminonawa.mydiary.init.InitTask;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by sean on 2016/12/28.
 */
public class UpdateMonitor extends Thread{
    Message result = null;
    Socket socket;
    ObjectInputStream is =null;
    Message m;
    boolean finish =false;
    String name;
    int type;

    public UpdateMonitor(String username) {

        name=username;

    }



    public void setMessage(Message in){
        m =in;
    }

    public void setType(int t){
        type =t;
    }

    public Message getMessage(){
        return result;
    }



    public void run(){
        try {
            int i =0;
            socket = new Socket("192.168.43.204",12349);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //传输注册信息
        Message login = new Message();
        login.setType(6);
        login.setName(name);
        ObjectOutputStream os = null;
        try {
            os = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            os.writeObject(login);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("send socket login message Successfully");

        //接收信息
        Object obj = null;

        try {
            is =new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            obj = is.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (obj != null) {
            result = (Message) obj;
            System.out.println(" login socket successfully");
        }



        while(true){
            int kind=0;
            //接收信息
            Object obj1 = null;

            try {
                 obj1 = is.readObject();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            if (obj1 != null) {
                result = (Message) obj1;
                Log.d("HUANG",result.getContent());
                System.out.println("Get the update message successfully");
            }
            Log.d("HQXINT",Integer.toString(result.type));
            System.out.println(result.type);
            //add event
            if(result.type==-1) {
                //TODO 创建团队日程 时间 地点 内容 协作者
                System.out.println(result.getContent());
                Log.d("HUANG",result.getContent());
                DBManager dbManager = new DBManager(MyApplication.getGlobalContext());
                dbManager.opeDB();
                dbManager.insertMemo(result.getName()+""+result.getTime()+""+result.getContent()+""+result.getOthers(),false, InitTask.globalTest);
            }

        }

    }


    public Socket getSocket() {
        return socket;
    }
}
