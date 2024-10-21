import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Collection {
    ArrayList<Card> cards;

    Collection(ArrayList<Card> cards){
        this.cards = cards;
//        MainUI.collection.setLayout(new GridLayout((int) (cards.length / 7), 7));
        MainUI.collection.setLayout(new GridLayout(10, 7));
        DrawCards();
    }
    void DrawCards(){
        for (Card card : cards) {
            JPanel panel = new JPanel(new GridLayout(1, 2));
            JPanel stats = new JPanel(new GridLayout(6, 1));
            JLabel icon = new JLabel(card.icon);

            JLabel name = new JLabel("Name: " + card.name);
            JLabel speed = new JLabel("Speed: " + card.speed);
            JLabel range = new JLabel("Range: " + card.range);
            JLabel health = new JLabel("Health Points: " + card.health);
            JLabel damage = new JLabel("Damage: " + card.damage);
            stats.add(name);
            stats.add(speed);
            stats.add(range);
            stats.add(health);
            stats.add(damage);

            panel.add(icon);
            panel.add(stats);

            MainUI.collection.add(panel);
        }
    }
}
