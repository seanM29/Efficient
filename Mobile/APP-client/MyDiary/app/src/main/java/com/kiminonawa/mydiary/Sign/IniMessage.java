package com.kiminonawa.mydiary.Sign;

import java.util.ArrayList;

public class IniMessage extends Message{

    private ArrayList<Message> Initialize = new ArrayList<Message>();

    public ArrayList<Message> getInitialize(){
        return  Initialize;
    }
    public void setInitialize(ArrayList<Message> in){
        Initialize=in;
    }


}
