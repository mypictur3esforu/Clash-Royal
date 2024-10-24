import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Collection extends JPanel {
    ArrayList<Card> cards;
    JPanel cardView, team;

    Collection(ArrayList<Card> cards) {
        this.cards = cards;
        setLayout(new GridLayout(2, 1));
        CreateTeam(new Card[0]);
        PaintCards();

        BackgroundColors();
        Add();
    }

    void Add(){
        add(team);
        add(cardView);
    }

    void BackgroundColors(){
        cardView.setBackground(MainUI.vibe);
//        team.setBackground(MainUI.vibe);
    }

    void PaintCards() {
        cardView = new JPanel(new GridLayout(3, 7));
        for (Card card : cards) {
            if (!card.cardType.equals("troop") && !card.cardType.equals("tower")) continue;
            String[] shownCategories = new String[]{"Name:", "Speed:", "Range:", "Health:", "Damage:", "Attack Speed:", "Type:", "Elixir:"};
            cardView.add(CreateCardInfoPanel(card, shownCategories));
        }
    }
    JPanel CreateCardInfoPanel(Card card, String[] categories) {
        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        infoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        JPanel stats = new JPanel(new GridLayout(categories.length, 1));
        for (int i = 0; i < categories.length; i++) {
            JLabel stat = new JLabel(categories[i] + " " + card.GetMultipleStats(categories).get(i), SwingConstants.CENTER);
            stats.add(stat);
        }
        JLabel icon = new JLabel(card.icon);
        infoPanel.add(icon);
        infoPanel.add(stats);
        return infoPanel;
    }

    void CreateTeam(Card[] teamCards){
        team = new JPanel(new GridLayout());
        JPanel cardsOfTeam = new JPanel(new GridLayout(2, 4));
        for (Card card : teamCards){
            if (card == null ) continue;
            cardsOfTeam.add(CreateCardInfoPanel(card, new String[]{""}));
        }
        team.add(cardsOfTeam);
    }
}
