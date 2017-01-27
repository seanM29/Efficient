import java.util.ArrayList;

/**
 * Created by sean on 2016/12/25.
 */
public class IniMessage extends Message{

    private ArrayList<Message> Initialize = new ArrayList<Message>();

    public ArrayList<Message> getInitialize(){
        return  Initialize;
    }
    public void setInitialize(ArrayList<Message> in){
        Initialize=in;
    }


}