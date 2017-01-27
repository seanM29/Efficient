import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.Serializable;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sean on 2016/12/24.
 */
public class List extends JPanel {

    public DefaultListModel listModel;
    public JList list;




    public List() {
       // setLayout(BorderLayout);
        //setPreferredSize(new java.awt.Dimension(500, 500));
        //super(new BorderLayout());
        setLayout(new BorderLayout());                                      //神奇，没有设置就会有间隙，但是不是默认是borderlayout
        setBorder(new LineBorder(Color.RED));
        listModel = new DefaultListModel();

        //put it in the list
        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.setCellRenderer(new MarioListRenderer(1));


        //   list.addListSelectionListener(this);
        list.setVisibleRowCount(16);
//        list.setPreferredSize(new java.awt.Dimension(400, 400));
        list.setFixedCellHeight(50);
        list.setFixedCellWidth(300);
        JScrollPane listScrollPane = new JScrollPane(list);
       // listScrollPane.setPreferredSize(new java.awt.Dimension(500, 700));
        //listScrollPane.setBorder( new EmptyBorder(1,1,1,1));
        add(listScrollPane, BorderLayout.CENTER);

       // add(list);
    }

    public void removeitem() {
        int index = list.getSelectedIndex();
        listModel.remove(index);

        int size = listModel.getSize();

        if (index == listModel.getSize()) {
            //removed item in last position
            index--;
        }

        list.setSelectedIndex(index);
        list.ensureIndexIsVisible(index);
    }

    public void additem(String content){

        int index = listModel.getSize();
        listModel.insertElementAt(content,index);

    }

    /*public class DefaultListCellRenderer extends JLabel
           implements ListCellRenderer, Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public Component getListCellRendererComponent(JList list,
                Object value, int index, boolean isSelected,
        boolean cellHasFocus) {
            ImageIcon ico = new ImageIcon("test.jpg");

            ico.setImage(ico.getImage().getScaledInstance(10,50,Image.SCALE_DEFAULT));
            setIcon(ico);
            setText(value.toString());

            if (isSelected) {
                setBackground(Color.blue);
                setForeground(Color.yellow);
            } else {
                // 设置选取与取消选取的前景与背景颜色.
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }
            return this;
        }
    }*/


}
