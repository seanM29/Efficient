package com.kiminonawa.mydiary.Sign;
import java.io.IOException;
import java.net.ServerSocket;

/**
 * Created by sean on 2016/12/28.
 */
public class Beginning {
    private static final int SERVER_PORT = 12349;
    private static ServerSocket ss = null;

    public static void main(String[] args) {
        try {
            //开启进程
            ss = new ServerSocket(SERVER_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Control ctr = new Control(SERVER_PORT,ss);
        ctr.service();

    }
}
