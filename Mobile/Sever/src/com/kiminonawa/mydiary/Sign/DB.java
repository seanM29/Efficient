package com.kiminonawa.mydiary.Sign;

/**
 * Created by sean on 2016/12/22.
 */

/**
 *      For login:  Table USER (
 *                  Name char(50) primary,
 *                  Password char(50)
 *                  phone char(20))
 *
 *      For user's list: Table Event(
 *                      ID char(50) primary,  (name+call service time)
 *                      Name char(20),
 *                      Content char(50),
 *                      Time char(50),
 *                      Location char(50),
 *                      Others char(60))
 */

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DB {

    //create table
    private void Create(String sql){
        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:Efficient.db");
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    //delete or insert or update
    private void excute(String sql){
        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:Efficient.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();

            stmt.executeUpdate(sql);

            stmt.close();
            c.commit();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    //select
    private ArrayList<String> select(String sql,ArrayList<String> want){
        Connection c = null;
        Statement stmt = null;
        ArrayList<String> result = new ArrayList<String>();
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:Efficient.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( sql );
            for(int i=0;i<want.size();i++) {
                result.add(rs.getString(want.get(i)));
            }
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Operation done successfully");

        return result;
    }

    private ArrayList<Message> select(String sql){
        Connection c = null;
        Statement stmt = null;
        ArrayList<Message> result = new ArrayList<Message>();
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:Efficient.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( sql );

            while ( rs.next() ) {
                String id = rs.getString("ID");
                String  name = rs.getString("name");
                String content  = rs.getString("content");
                String  location = rs.getString("location");
                String  others = rs.getString("others");
                Message tmp =new Message(id,name,content,location,others);
                result.add(tmp);

            }


            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Operation done successfully");

        return result;
    }



    public void test( String args[] ) {
        DB test = new DB();

       /* String sql ="CREATE TABLE USER (NAME CHAR(50) PRIMARY KEY   NOT NULL,KEYWORD CHAR(50)   NOT NULL)";
        test.Create(sql);

        sql = "INSERT INTO USER(NAME,KEYWORD) VALUES ('sean','shendong')";
        test.excute(sql);
*/
        ArrayList<String> want = new ArrayList<String>();
        want.add("keyword");
       String sql  ="SELECT * FROM USER";
        ArrayList<String> result = test.select(sql,want);

        System.out.println(result.get(0));


    }


    //test whether the account and password is right
    public Message login(Message m) {
        String sql = "select * from USER where name = '"+m.getName()+"';";
        ArrayList<String> want = new ArrayList<String>();
        want.add("password");
        ArrayList<String> result = new ArrayList<String>();


        result = select(sql,want);


        //password is correct
        if(result.get(0).equals(m.getPassword())){
            m.type=0;
        }

        return m;
    }


    //register a new account
    public Message register(Message m) {
        String sql = "insert into USER(name, password,phone) values( '"+m.getName()+"','"+m.getPassword()+"','"+m.getPhone()+"');";

        excute(sql);
        m.type=0;

        //password is correct
        return m;
    }


    //add a todo list to the table event
    public Message insert(Message m) {
        String othername = new String();
        for(int i=0;i<m.getOthers().size();i++){
            othername+=m.getOthers().get(i);
            othername+="#";
        }
        String sql  ="insert into event(ID,Name,Content,Time,Location,Others) values('"+m.getID()+"','"+m.getName()+"','"+m.getContent()+"','"+m.getTime()+"','"+m.getLocation()+"','"+othername+"');";
        excute(sql);
        m.type=0;

        return m;

    }

    public Message initialize(Message m) {
        IniMessage tmp =(IniMessage)m;
        String sql = "select * from Event where name =  '"+tmp.getName()+"';";
        ArrayList<Message> res = select(sql);
        tmp.setInitialize(res);

        Message result = (Message)tmp;
        result.setType(0);
        return result;


    }

    public Message delete(Message m) {
        String sql ="delete from Event where name ='"+m.getName()+"' and content = '"+m.getContent()+"';";
        excute(sql);

        m.type=0;
        return m;
    }

    public ArrayList<String> getPhone(Message m) {

        Connection c = null;
        Statement stmt = null;
        ArrayList<String> result = new ArrayList<String>();
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:Efficient.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();


            ResultSet rs = null;
            for(int i =0;i<m.getOthers().size();i++){
                String sql = "select * from user where name =  '"+m.getOthers().get(i)+"';";
                rs = stmt.executeQuery( sql );
                result.add(rs.getString("phone"));
            }



            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Operation done successfully");

        return result;

    }

    public ArrayList<String> getCallContent(Message m) {

        Connection c = null;
        Statement stmt = null;
        ArrayList<String> result = new ArrayList<String>();
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:Efficient.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();


            ResultSet rs = null;
            String sql = "select * from event where name =  '"+m.getContent()+"';";
            rs = stmt.executeQuery( sql );
            result.add(rs.getString("time"));
            result.add(rs.getString("location"));
            String [] othersPerson=rs.getString("others").split("#");


            for(int i =0;i<othersPerson.length;i++){
                sql = "select * from user where name =  '"+othersPerson[i]+"';";
                rs = stmt.executeQuery( sql );
                result.add(rs.getString("phone"));
            }



            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Operation done successfully");

        return result;
    }

    public ArrayList<String> getOtherName(Message m) {
        Connection c = null;
        Statement stmt = null;
        ArrayList<String> result = new ArrayList<String>();
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:Efficient.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();


            ResultSet rs = null;
            int postion = m.getContent().indexOf("#");

            String scontent = m.getContent().substring(0,postion);
            String sql = "select * from event where content =  '"+scontent+"'and name = '"+m.getName()+"';";
            rs = stmt.executeQuery( sql );
            String [] othersPerson=rs.getString("others").split("#");


            for(int i =0;i<othersPerson.length;i++){
                result.add(othersPerson[i]);
            }



            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Operation done successfully");

        return result;
    }


    //insert a new list for specially use name






   /*public static void main(String []args){
        //String sql  = "Create TABLE Event(ID char(50) PRIMARY KEY, Name char(20), Content TEXT, Time TEXT,  Location TEXT, Others TEXT);";
       //String sql = "Drop table Event;";
       DB db =new DB();
       db.excute(sql);
      // SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
      // String s = df.format(new Date()).toString();
       //System.out.println(s);// new Date()为获取当前系统时间

    }*/


}
