import javax.swing.*;
import java.awt.*;

public class Card {
    String name;
    ImageIcon icon;
    int speed, range, attackSpeed, sightDistance, width, height;
    double health, damage;

    Card(String name, ImageIcon icon, int speed, int range, double health, double damage, int attackSpeed, int sightDistance, int width, int height){
        this.name = name;
        this.icon = ImageResizer(icon, width, height);
        this.speed = speed;
        this.range = range;
        this.health = health;
        this.damage = damage /* * 60 / 1000*/;
        this.attackSpeed = attackSpeed;
        this.sightDistance = sightDistance;
        this.width = width;
        this.height = height;
    }

    static ImageIcon ImageResizer(ImageIcon paraImage, int width, int height){
        paraImage.setImage(paraImage.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
        return paraImage;
    }
}
