import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * In der Collection sind alle Karten und das Team einsehbar
 */
public class Collection extends JPanel {
    /**
     * Karten im Spiel
     */
    private ArrayList<Card> cards;
    /**
     * JPanel in dem alle gesammelten Karten angezeigt werden
     */
    private JPanel cardView;
    /**
     * Kategorien die angezeigt werden
     */
    private static String[] shownCategories = new String[]{"Name:", "Speed:", "Range:", "Health:", "Damage:", "Attack Speed:", "Type:", "Elixir:"};

    static public String[] GetShownCategories(){return shownCategories;}

    /**
     * Erstellt die Karten Anzeige
     * @param cards Karten im Spiel
     */
    Collection(ArrayList<Card> cards) {
        this.cards = cards;
        setLayout(new GridLayout(2, 1));
        PaintCards();

        BackgroundColors();
        Add();
    }

    /**
     * Fügt alle Komponenten hinzu
     */
    void Add(){
        add(new TeamPanel(TeamPanel.CreateRandomTeam(TeamPanel.SortPlayableCards(ClashRoyal.staticCardCollection))));
        add(cardView);
    }

    /**
     * Färbt den Hintergrund
     */
    void BackgroundColors(){
        cardView.setBackground(MainUI.vibe);
//        team.setBackground(MainUI.vibe);
    }

    /**
     * Erstellt die Kartenübersicht
     */
    void PaintCards() {
        cardView = new JPanel(new GridLayout(3, 7));
        for (Card card : cards) {
            if (!card.GetCardType().equals("troop") && !card.GetCardType().equals("tower")) continue;
            cardView.add(CreateCardInfoPanel(card, shownCategories));
        }
    }

    /**
     * Erstellt eine einzelne Informationskarte für eine Karte
     * @param card Karte die beschrieben werden soll
     * @param categories Kategorien, die sichtbar sein müssen
     * @return JPanel mit allen Informationen
     */
    static JPanel CreateCardInfoPanel(Card card, String[] categories) {
        JPanel infoPanel = new JPanel(new GridLayout(1, 4));
        infoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        JPanel stats = new JPanel(new GridLayout(categories.length, 1));
        for (int i = 0; i < categories.length; i++) {
            JLabel stat = new JLabel(categories[i] + " " + card.GetMultipleStats(categories).get(i), SwingConstants.CENTER);
            stats.add(stat);
        }
//        Color color = new Color(0xF8EC0D);
//        infoPanel.setBackground(color);
//        stats.setBackground(color);
        JLabel icon = new JLabel(card.GetIcon());
        infoPanel.add(icon);
        infoPanel.add(stats);
        return infoPanel;
    }

}
