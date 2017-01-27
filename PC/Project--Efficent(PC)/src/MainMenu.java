import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by sean on 2016/12/24.
 */
public class MainMenu extends JPanel implements ActionListener{

    // panel component for the add event function
    JFrame input;
    JButton invite ;
    JButton finish ;
    TextField person ;
    TextField Time ;
    TextField Location ;
    TextField Content ;
    //others invited
    ArrayList<String> others = new ArrayList<String>();
    private Message send;             //add the list to send the people
    private ArrayList<Message> localMessage = new ArrayList<Message>();
    private String name;            //user name
    private  JFrame jframe =new JFrame("Efficient");
    //call service from the sever
    private CallService call =new CallService();
    //to do list we want dispaly
    private List list = new List();

    private JPanel chatpanel =new JPanel();
    private JPanel chatDisplay = new JPanel();
    private JTextField chatfield = new JTextField();
    private  JTextArea jta = new JTextArea();

    private JScrollPane chatMessage =new JScrollPane(jta);


    public MainMenu(String n){


        name = n;


        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         list = new List();
        jframe.add(list,BorderLayout.WEST);

        //add the menu bar
        JMenuBar menuBar = new JMenuBar();

        //build the menu
        JMenu menu = new JMenu("Operation");
        menuBar.add(menu);

        //a group of menuItem
        //add event
        JMenuItem menuItem = new JMenuItem("Add");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddEvent();
            }
        });
        menu.add(menuItem);

        //delete event
        menuItem = new JMenuItem("Delete");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeEvent();
            }
        });
        menu.add(menuItem);

        //call others in the project
        menuItem = new JMenuItem("Call");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                callothers();
            }
        });
        menu.add(menuItem);
        jframe.add(menuBar,BorderLayout.NORTH);

        //chat room gui
        chatDisplay.setLayout(new BorderLayout());

        chatpanel.setPreferredSize(new Dimension(100,80));
        chatpanel.setLayout(new BoxLayout(chatpanel,BoxLayout.LINE_AXIS));
        chatpanel.add(chatfield);
        chatpanel.add(Box.createHorizontalStrut(5));
        chatpanel.add(new JSeparator(SwingConstants.VERTICAL));
        chatpanel.add(Box.createHorizontalStrut(5));
        JButton ChatSend = new JButton("Send");
        ChatSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Message m = new Message();
                int index = list.list.getSelectedIndex();
                m.setContent(list.listModel.get(index).toString()+"#"+chatfield.getText());
                m.setType(8);
                m.setName(name);
                String chatInformation = name+" : "+chatfield.getText()+";\n";
                jta.append(chatInformation);
                chatfield.setText("");
                CallService call =new CallService(m);
                call.start();
            }
        });
        chatpanel.add(ChatSend);
        chatpanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        chatMessage.setBackground(Color.white);


        chatDisplay.add(chatpanel,BorderLayout.SOUTH);
        chatDisplay.add(chatMessage,BorderLayout.CENTER);



        jframe.add(chatDisplay,BorderLayout.CENTER);



        //dispaly the value
        jframe.setVisible(true);
        jframe.setSize(1000,800);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        jframe.setLocation((d.width - jframe.getSize().width) / 2,
                (d.height - jframe.getSize().height) / 2);


        //get the item before from the db
        Message iniMessage = new IniMessage();
        iniMessage.setType(4);
        iniMessage.setName(name);


        CallService call =new CallService();
        call.setMessage(iniMessage);
        call.start();
        try {
            call.join();
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        iniMessage = call.getResult();


        ArrayList<Message> res = ((IniMessage)iniMessage).getInitialize();

        for(int i=0;i<res.size();i++){
            list.additem(res.get(i).getContent());
        }

        //login udate socket
        UpdateMonitor um = new UpdateMonitor(this,name);

        //monitor the update
        um.start();







    }

    public void callothers() {
        int index = list.list.getSelectedIndex();
        String content =list.listModel.get(index).toString();

        Message message = new Message();

        /*for(int i=0;i<localMessage.size();i++) {
            if(content==localMessage.get(i).getContent()){
                message=localMessage.get(i);
            }
        }*/

        message.setContent(content);
        message.setType(7);

        CallService call =new CallService();
        call.setMessage(message);
        call.start();
        try {
            call.join();
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
    }

    //remove item from db and gui
    public void removeEvent() {
        int index = list.list.getSelectedIndex();
        String content =list.listModel.get(index).toString();

        Message message = new Message();
        message.setContent(content);
        message.setName(name);
        message.setType(5);

        list.removeitem();

      //  call.SendGet(message);

        CallService call =new CallService();
        call.setMessage(message);
        call.start();
        try {
            call.join();
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
      //  iniMessage = call.getResult();
    }

    public void AddEvent() {


        // initialize
        input  = new JFrame();
        input.setLayout(new BorderLayout());
        invite = new JButton("invite");
        finish = new JButton("finish");
        person = new TextField(10);
        Time = new TextField();
        Location = new TextField();
        Content = new TextField();







        //time location content panel
        JPanel p2 = new JPanel();
        p2.setLayout(new GridLayout(3, 2));
        p2.add(new Label("Time"));
        p2.add(Time);
        p2.add(new Label("Location"));
        p2.add(Location);
        p2.add(new Label("Content") );
        p2.add(Content);
        input.add(p2,BorderLayout.CENTER);



        //finish and invite button
        JPanel buttonPane = new JPanel();

        buttonPane.setLayout(new BoxLayout(buttonPane,
                BoxLayout.LINE_AXIS));

        //register button
        finish.addActionListener(this);
        invite.addActionListener(this);

        buttonPane.add(finish);
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(person);
        buttonPane.add(invite);
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        input.add(buttonPane, BorderLayout.SOUTH);
        input.setVisible(true);
        input.setSize(300,300);
    }

    //add event and finish button listener
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton jbt = (JButton)e.getSource();
        String text = jbt.getText();


        //finish button
        if(text.equals("finish")){
            String time =null;
            String location = null;
            String content = null;
            String ID = null;


            time =Time.getText();
            location = Location.getText();
            content = Content.getText();

            //name + time to be ID
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String s = df.format(new Date()).toString();
            ID=name+s;

            // send information to the sever
            send = new Message(ID,name, time,location,content,others,3);
            //存储message 信息到本地
            localMessage.add(send);

            CallService call =new CallService();
            call.setMessage(send);
            call.start();
            try {
                call.join();
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            //send = call.getResult();


            list.additem(content);

            //reset
            others = new ArrayList<String>();

            input.setVisible(false);

        }
        else if(text.equals("invite")){
            others.add(person.getText());
            person.setText("");
        }

    }

    public List getList(){
        return list;
    }

    public JTextArea getJta() {
        return jta;
    }
}
