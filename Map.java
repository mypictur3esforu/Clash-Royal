import javax.swing.*;
import java.awt.*;

public class Map extends JPanel{
    ImageIcon mapPicture = Card.ImageResizer(new ImageIcon("images/crtestmap.png"), 700, 1060);
    JLabel map = new JLabel(mapPicture);
    JPanel panel = new JPanel();

    Map(){
        setSize(700,1080);

        map.setAlignmentX(0);
        map.setAlignmentY(0);

//        sollte eigentlich nicht sichtbar sein, aber iwi gibts ein Abstand
        panel.setBackground(Color.gray);

        add(panel);
        panel.add(map);

        setVisible(true);
    }
}