import javax.swing.*;

public class Card {
    String name;
    ImageIcon icon;
    int speed, range;
    double health, damage;

    Card(String name, ImageIcon icon, int speed, int range, double health, double damage){
        this.name = name;
        this.icon = icon;
        this.speed = speed;
        this.range = range;
        this.health = health;
        this.damage = damage * 60 / 1000;
    }

    void CreateTroop(int x, int y){

    }
}
