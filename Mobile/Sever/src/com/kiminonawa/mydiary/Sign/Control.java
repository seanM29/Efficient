package com.kiminonawa.mydiary.Sign;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

/**
 * Created by sean on 2016/12/23.
 */
public class Control{

    private int SERVER_PORT ;
    private ServerSocket ss ;
    HashMap<String,Socket> map = new HashMap<String, Socket>();         //维护注册的socket编号


    public void mapadd(String s,Socket S){
            map.put(s,S);
    }



    public Control(int sp,ServerSocket sst){
        SERVER_PORT=sp;
        ss =sst;
    }



    /*开启服务*/
    public  void service() {
        while (true) {
            Socket socket = null;
            try {
                socket = ss.accept(); //

            } catch (IOException e) {
                e.printStackTrace();
            }
            //开启线程
            SeverThread sthread = null;
            try {
                sthread = new SeverThread(socket);
                sthread.setCtr(this);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            sthread.start();
        }
    }


    public Socket getSocket(String name) {
       return map.get(name);
    }
}
