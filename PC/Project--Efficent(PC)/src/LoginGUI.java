import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by sean on 2016/12/20.
 */
public class LoginGUI extends JPanel{

    private JFrame jflogin;
    private JPanel jplogin;

    private JLabel dname = new JLabel("User ame");
    private JLabel dpassword = new JLabel("Password");

    private JButton login = new JButton("Login");
    private JButton register = new JButton("Register");

    private JTextField Tname = new JTextField();
    private JTextField Tpassword= new JTextField();
    private JLabel image=new JLabel(new ImageIcon("test.jpg"));
    private JFrame frame = new JFrame();



    public LoginGUI(){

        setLayout(new BorderLayout());
        add(image, BorderLayout.NORTH);

        JPanel p2 = new JPanel();
        p2.setLayout(new GridLayout(3, 2));
        p2.add(dname);
        p2.add(Tname);
        p2.add(dpassword);
        p2.add(Tpassword );
        p2.add(login);
        p2.add(register);
        add(p2,BorderLayout.CENTER);


        //login in the system
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Message m = new Message();
                m.setName(Tname.getText());
                m.setPassword(Tpassword.getText());
                m.type = 2;

                Message result = new Message();
                CallService call =new CallService();
                call.setMessage(m);
                call.start();
                try {
                    call.join();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                result = call.getResult();



                //插入成功
                if(result.type==0){
                    //JOptionPane.showMessageDialog(null,"login successfully");
                    frame.setVisible(false);
                    new MainMenu(m.getName());
                }
                else{
                    JOptionPane.showMessageDialog(null,"No such accout or password failed");
                }
            }


        });



        //register new account
        register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               new Register();
            }
        });



        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("Efficient");
        frame.add(this, BorderLayout.CENTER);
        frame.setSize(400,600);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation((d.width - frame.getSize().width) / 2,
                (d.height - frame.getSize().height) / 2);
        frame.setVisible(true);



    }

    class Register extends JPanel{
        JLabel rname = new JLabel("User ame");
        JLabel rpassword = new JLabel("Password");
        JLabel rphone = new JLabel("Phone");

        JButton register = new JButton("Register");

        JTextField tname = new JTextField();
        JTextField tpassword= new JTextField();
        JTextField tphone= new JTextField();

        JFrame rframe = new JFrame();

        public Register(){

            setLayout(new GridLayout(3,2));
            add(rname);
            add(tname);
            add(rpassword);
            add(tpassword);
            add(rphone);
            add(tphone);

            register.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Message m = new Message();
                    m.setName(tname.getText());
                    m.setPassword(tpassword.getText());
                    m.setPhone(tphone.getText());
                    m.type = 1;

                    Message result = new Message();
                    CallService call =new CallService();
                    call.setMessage(m);
                    call.run();
                    try {
                        call.join();
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    result = call.getResult();


                    //插入成功
                    if(result.type==0){
                        JOptionPane.showMessageDialog(null,"register successfully");
                        rframe.setVisible(false);
                    }
                    else{
                        JOptionPane.showMessageDialog(null,"register failed");
                    }
                }
            });


            rframe.add(this,BorderLayout.CENTER);
            rframe.add(register,BorderLayout.SOUTH);
            rframe.setSize(300,300);
            rframe.setVisible(true);
        }

    }





    public static void main(String []args){
        LoginGUI lgui = new LoginGUI();
    }



}
