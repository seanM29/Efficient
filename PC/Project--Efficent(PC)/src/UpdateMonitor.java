import javax.swing.*;
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
    Socket socket = null;
    ObjectInputStream is =null;
    Message m;
    boolean finish =false;
    MainMenu main;
    List list;
    String name;
    JTextArea jta =null;

    int type;

    public UpdateMonitor(MainMenu in,String username) {
        try {
            socket = new Socket("localhost",12349);
        } catch (IOException e) {
            e.printStackTrace();
        }
        main = in;
        list = main.getList();
        name=username;
        jta = main.getJta();
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
        while(true){
            int kind=0;
            //接收信息
            Object obj = null;
            try {
                is =new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
                obj = is.readObject();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            if (obj != null) {
                result = (Message) obj;
                System.out.println("Get the update message successfully");
            }

            //add event
            if(result.type==-1) {
                list.additem(result.getContent());
            }
            //add chat message
            else{
                String chatInformation = result.getName()+" :"+result.getContent()+";\n";
                jta.append(chatInformation);

            }



        }

    }


    public Socket getSocket() {
        return socket;
    }
}
