package com.kiminonawa.mydiary.Sign;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class CallService extends Thread {

    Message result = null;
    Socket socket = null;
    ObjectInputStream is =null;
    Message m;



    public CallService(Message in ){
        m = in;
    }

    public CallService() {

    }

    public void setMessage(Message in){
        m =in;
    }

    //sever and client send and get the message
    public void run(){

        try {
            socket = new Socket("192.168.43.204",12349);
            is =new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //传输信息
        ObjectOutputStream os = null;
        try {
            os = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            os.writeObject(m);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("send Successfully");



        //接收信息
        Object obj = null;
        try {
            obj = is.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (obj != null) {
            result = (Message) obj;
            System.out.println("Get the message successfully");
        }

        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loginsocket(Socket s){

    }

    public Message getResult() {
        return result;
    }
}

