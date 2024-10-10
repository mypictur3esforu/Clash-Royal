import javax.swing.*;

public class Window extends JPanel{
    //the pictures
    ImageIcon mapPicture = Stuff.ImageResizer(new ImageIcon("images/crtestmap.png"), 700, 1080);
    JLabel map = new JLabel(mapPicture);
    JPanel panel = new JPanel();

    Window(){
        //panel.setLayout(null);
        setSize(700,1080);
        //setMinimumSize(new Dimension(700, 600));
        //setBackground(Color.gray);

        map.setAlignmentX(0);
        map.setAlignmentY(0);

        add(panel);
        panel.add(map);

        setVisible(true);
    }
}