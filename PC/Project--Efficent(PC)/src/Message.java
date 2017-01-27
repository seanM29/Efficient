import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by sean on 2016/12/24.
 */
/*
* type
*   0 success
*   1 register
*   2 login
*   3 add a todo list
*   4 get all the event from the db which belongs to this user
*
*
* */



public class Message implements java.io.Serializable{

    private static final long serialVersionUID=-1;
    //which kind of message
    public int type;

    protected String Name;
    private String Password;

    private String time;
    private String location;
    private String Content;
    private ArrayList<String> others = new ArrayList<String>();
    private String ID;
    private String phone;


    public Message( String id,String n,String t, String l, String c,ArrayList<String> o,int k){
        ID=id;
        time = t;
        location = l;
        Content = c;
        others = o;
        Name =n;
        type=k;
    }



    public Message(String name, String password) {
        Name=name;
        Password=password;
    }

    public Message() {

    }

    public String getName(){
        return Name;
    }

    public String getPassword() {
        return Password;
    }

    public void setName(String name) {
        Name = name;
    }
    public void setPassword(String password){
        Password = password;
    }


    public String getID(){return ID;}

    public String getTime(){return time;}

    public String getLocation(){return location;}

    public String getContent(){return Content;}

    public ArrayList<String> getOthers(){return others;}

    public void setType(int i) {
        type = i;
    }

    public void setContent(String content) {
        Content=content;
    }


    public void setOthers(ArrayList<String> in) {
        others=in;
    }
    public Message(String id, String name, String content, String Location, String Others) {
        ID =id;
        Name=name;
        Content=content;
        location=Location;

        String[] tmp = Others.split("#");
        for(int i=0;i<tmp.length;i++){
            others.add(tmp[i]);
        }

    }
    public void setID(String id) {
        ID=id;
    }

    public void setPhone(String p){phone=p;}

    public String getPhone(){return phone;}

}
