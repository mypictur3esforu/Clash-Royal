import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Collection extends JPanel {
    private ArrayList<Card> cards;
    private JPanel cardView;
    private static String[] shownCategories = new String[]{"Name:", "Speed:", "Range:", "Health:", "Damage:", "Attack Speed:", "Type:", "Elixir:"};

    static public String[] GetShownCategories(){return shownCategories;}

    Collection(ArrayList<Card> cards) {
        this.cards = cards;
        setLayout(new GridLayout(2, 1));
        PaintCards();

        BackgroundColors();
        Add();
    }

    void Add(){
        add(new Team(Team.CreateRandomTeam(Team.SortPlayableCards(ClashRoyal.staticCardCollection))));
        add(cardView);
    }

    void BackgroundColors(){
        cardView.setBackground(MainUI.vibe);
//        team.setBackground(MainUI.vibe);
    }

    void PaintCards() {
        cardView = new JPanel(new GridLayout(3, 7));
        for (Card card : cards) {
            if (!card.GetCardType().equals("troop") && !card.GetCardType().equals("tower")) continue;
            cardView.add(CreateCardInfoPanel(card, shownCategories));
        }
    }

    static JPanel CreateCardInfoPanel(Card card, String[] categories) {
        JPanel infoPanel = new JPanel(new GridLayout(1, 2));
        infoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        JPanel stats = new JPanel(new GridLayout(categories.length, 1));
        for (int i = 0; i < categories.length; i++) {
            JLabel stat = new JLabel(categories[i] + " " + card.GetMultipleStats(categories).get(i), SwingConstants.CENTER);
            stats.add(stat);
        }
        JLabel icon = new JLabel(card.GetIcon());
        infoPanel.add(icon);
        infoPanel.add(stats);
        return infoPanel;
    }

}
