import javax.swing.*;
import java.awt.*;

/**
 * Verändert die Größe des gegebenen Bildes
 */
 public class Stuff {
    static ImageIcon ImageResizer(ImageIcon paraImage, int width, int height){
        paraImage.setImage(paraImage.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
        return paraImage;
    }
}
