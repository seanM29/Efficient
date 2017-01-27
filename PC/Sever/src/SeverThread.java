import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by sean on 2016/12/24.
 */


public class SeverThread extends Thread {
    int flag = 0;
    private Socket socket = null;
    Account test = new Account("sean","shendong");
    private DB db =new DB();
    private Control ctr;
    private boolean WriteBack =true;

    public SeverThread(Socket s) throws IOException {
        socket = s;
    }

    public void setCtr(Control c){
        ctr = c;
    }

    //运行代码
    public void run() {
        ObjectInputStream is = null;
        ObjectOutputStream os = null;
        Message result = new Message();
        result.type=-100;
        try {
            os = new ObjectOutputStream(socket.getOutputStream());
            is =new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }


        //接收信息
        Message m = null;
        Object obj = null;
        try {
            obj = is.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (obj != null) {
            m = (Message) obj;
            System.out.println("Get the message successfully");
        }


        //according the request type, do different operation
        switch (m.type){
            case 1:
                result=db.register(m);
                break;
            case 2:
                //login
                result=db.login(m);
                break;
            case 3:
                //add a new list to the event
                result  =db.insert(m);
                if(m.getOthers().size()>0){
                    InsertOther(m);
                }

              //  result  =db.insert(tmp);
                break;
            case 4:
                //get all list from the db belong to this user in the beginning
                result=db.initialize(m);
                break;
            case 5:
                //delete a event from the gui and db
                result= db.delete(m);
                break;
            case 6:
                //注册这个端口号
                String username = m.getName();
                ctr.mapadd(username,socket);
                result=m;
                result.type=0;
                break;
            case 7:
                //call 同一个项目中的其他人
                ArrayList<String> callContent = db.getCallContent(m);

                SingleCall call = new SingleCall(m.getName(),callContent);
                call.execute();
                result=m;
                result.setType(0);
                break;
            case 8:
                //chat message update
                ArrayList<String> othername = db.getOtherName(m);
                updateChat(othername,m);
                break;

        }


        //output the result to the client after the service
        try {
            os.writeObject(result);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Sever Succeed");


    }

    private void updateChat(ArrayList<String> name,Message m) {

        Socket s=null;

        m.setType(-2);          //chat message update


        for(int i=0;i<name.size();i++){
            s = ctr.getSocket(name.get(i));
            if(s!=null){
                ObjectOutputStream os = null;
                try {
                    os = new ObjectOutputStream(s.getOutputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                int position = m.getContent().indexOf("#");
                String DContent = m.getContent().substring(position+1);
                m.setContent(DContent);


                //将数据输给其他被邀请的用户
                try {
                    //创建日程

                    os.writeObject(m);
                    os.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }



        }

    }

    private void InsertOther(Message m) {
            String name;
            Message message = m;
            ArrayList<String> others = m.getOthers();
            Socket s=null;

            for(int i=0;i<m.getOthers().size();i++){
                String tmp =m.getName();
                m.setName(m.getOthers().get(i));

                others.set(i,tmp);
                m.setOthers(others);

                //update ID
                String ID = m.getID();
                ID=ID.replaceFirst(tmp ,m.getName());
                m.setID(ID);

                Message result =db.insert(m);
                s = ctr.getSocket(result.getName());
                if(s!=null){
                    ObjectOutputStream os = null;
                    try {
                        os = new ObjectOutputStream(s.getOutputStream());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    m.setType(-1);              //增加其他项

                    //将数据输给其他被邀请的用户
                    try {
                        //创建日程

                        os.writeObject(m);
                        os.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }



            }

    }
}
