import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Erstellt eine Übersicht von einem gegebenem Team
 */
public class TeamPanel extends JPanel {
    /**
     * Karten im Team
     */
    private ArrayList<Card> team;
    /**
     * Karten im Team
     */
    private static ArrayList<Card> staticTeam;
    /**
     * Anzeige Panel
     */
    private JPanel teamPanel;

    static public ArrayList<Card> GetStaticTeam(){
        return staticTeam;
    }

    /**
     * Erstellt eine Team-Übersicht
     * @param cards Karten im Team
     */
    TeamPanel(ArrayList<Card> cards){
        team = cards;
        staticTeam = cards;
        setLayout(new GridLayout(1, 1));
        teamPanel = new JPanel(new GridLayout(1, 1));
        CreateTeam();
        add(teamPanel);
    }

    /**
     * Fügt die Bilder und Stats hinzu
     */
    void CreateTeam(){
        teamPanel = new JPanel(new GridLayout());
        setBackground(MainUI.vibe);
        JPanel cardsOfTeam = new JPanel(new GridLayout(2, 4));
        for (Card card : team){
            if (card == null ) continue;
            cardsOfTeam.add(Collection.CreateCardInfoPanel(card, Collection.GetShownCategories()));
        }
        teamPanel.add(cardsOfTeam);
    }

    /**
     * Erstellt ein zufälliges Team (ziemlich Fehl am Platz, habe die Klasse vorher für etwas anderes benutzt)
     * @param potCards Kartensammlung
     * @return Ein zufälliges Team
     */
    static ArrayList<Card> CreateRandomTeam(ArrayList<Card> potCards){
        while (potCards.size() <= 8){
            potCards.addAll(potCards);
        }
        ArrayList<Card> randomTeam = new ArrayList<>();
        int teamSize = 8;
        int[] cardNumbers = new int[teamSize];
         outerFor:
        for (int i = 0; i < teamSize; i++) {
            int randomNumber = (int) (Math.floor(Math.random() * potCards.size()));
            for (int j = 0; j < teamSize; j++) {
                if (cardNumbers[i] == randomNumber) continue outerFor;
            }
            cardNumbers[i] = randomNumber;
            randomTeam.add(potCards.get(randomNumber));
        }
        return randomTeam;
    }

    /**
     * Sortiert nicht spielbare Karten aus
     * @param cards Kartensammlung
     * @return Kartensammlung ausschließlich mit spielbaren Karten
     */
    static ArrayList<Card> SortPlayableCards(ArrayList<Card> cards){
        ArrayList<Card> sorted = new ArrayList<>();
        for (Card card : cards){
            if (Objects.equals(card.GetCardType(), "troop") || Objects.equals(card.GetCardType(), "tower")) sorted.add(card);
        }
        return sorted;
    }
}
