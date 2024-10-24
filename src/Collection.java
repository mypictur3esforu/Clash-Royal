import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Collection extends JPanel {
    ArrayList<Card> cards;
    JPanel cardView;

    Collection(ArrayList<Card> cards) {
        this.cards = cards;
        setLayout(new GridLayout(1, 1));
        PaintCards();
        Add();
    }

    void Add(){
        add(cardView);
    }

    void DrawCards() {
        for (Card card : cards) {
            if (card.cardType.equals("projectile")) continue;
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
            panel.setBackground(MainUI.vibe);

//            MainUI.collection.add(panel);
        }
    }

    void PaintCards() {
        cardView = new JPanel(new GridLayout(10, 7));
        for (Card card : cards) {
            if (!card.cardType.equals("troop") && !card.cardType.equals("tower")) continue;
            String[] shownCategories = new String[]{"Name:", "Speed:", "Range:", "Health:", "Damage:", "Attack Speed:", "Type:", "Elixir:"};
            cardView.add(CreateCardInfoPanel(card, shownCategories));
        }
    }
    JPanel CreateCardInfoPanel(Card card, String[] categories) {
        JPanel infoPanel = new JPanel(new GridLayout(1, 2));
        infoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        JPanel stats = new JPanel(new GridLayout(categories.length, 1));
        for (int i = 0; i < categories.length; i++) {
            JLabel stat = new JLabel(categories[i] + " " + card.GetMultipleStats(categories).get(i));
            stats.add(stat);
        }
        JLabel icon = new JLabel(card.icon);
        infoPanel.add(icon);
        infoPanel.add(stats);
        return infoPanel;
    }
}
