import javax.swing.*;
import java.awt.*;

public class Map extends JPanel{
    ImageIcon mapPicture;
    JLabel map;
    JPanel panel = new JPanel();

    Map(int width, int height, ImageIcon mapPicture){
        this.mapPicture = Card.ImageResizer(mapPicture, width, height);
        map = new JLabel(this.mapPicture);

        setSize(width,height);

        map.setAlignmentX(0);
        map.setAlignmentY(0);

//        sollte eigentlich nicht sichtbar sein, aber iwi gibts ein Abstand
        panel.setBackground(Color.gray);

        add(panel);
        panel.add(map);

        setVisible(true);
    }
}