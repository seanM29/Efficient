import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sean on 2016/12/25.
 */
public class MarioListRenderer extends DefaultListCellRenderer {
    private final Map<Integer, ImageIcon> imageMap = null;
    private int type=0;

    Font font = new Font("helvitica", Font.BOLD, 24);

    public MarioListRenderer(int t){
        type =t;
    }


    @Override
    public Component getListCellRendererComponent(
            JList list, Object value, int index,
            boolean isSelected, boolean cellHasFocus) {

        JLabel label = (JLabel) super.getListCellRendererComponent(
                list, value, index, isSelected, cellHasFocus);

        //ImageIcon ico = imageMap.get(type);
        ImageIcon ico;
        if(type==1){
            ico =new ImageIcon("test.jpg");
        }
        else{
            ico = new ImageIcon("2.jpg");
        }

        ico.setImage(ico.getImage().getScaledInstance(10,50,Image.SCALE_DEFAULT));
        setIcon(ico);
        label.setIcon(ico);
        label.setHorizontalTextPosition(JLabel.RIGHT);
        label.setFont(font);
        return label;
    }


    public Map<Integer, ImageIcon> createImageMap(int[] list) {
        Map<Integer, ImageIcon> map = new HashMap<>();
        try {
            map.put(1, new ImageIcon("test.jpg"));
            map.put(2, new ImageIcon("2.jpg"));

            //map.put("Luigi", new ImageIcon(new URL("http://i.stack.imgur.com/UvHN4.png")));
            // map.put("Bowser", new ImageIcon(new URL("http://i.stack.imgur.com/s89ON.png")));
            // map.put("Koopa", new ImageIcon(new URL("http://i.stack.imgur.com/QEK2o.png")));
            // map.put("Princess", new ImageIcon(new URL("http://i.stack.imgur.com/f4T4l.png")));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return map;
    }
}