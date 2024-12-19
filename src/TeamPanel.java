import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class TeamPanel extends JPanel {
    private ArrayList<Card> team;
    private static ArrayList<Card> staticTeam;
    private JPanel teamPanel;

    static public ArrayList<Card> GetStaticTeam(){
        return staticTeam;
    }

    TeamPanel(ArrayList<Card> cards){
        team = cards;
        staticTeam = cards;
        setLayout(new GridLayout(1, 1));
        teamPanel = new JPanel(new GridLayout(1, 1));
        CreateTeam();
        add(teamPanel);
    }

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

    static ArrayList<Card> SortPlayableCards(ArrayList<Card> cards){
        ArrayList<Card> sorted = new ArrayList<>();
        for (Card card : cards){
            if (Objects.equals(card.GetCardType(), "troop") || Objects.equals(card.GetCardType(), "tower")) sorted.add(card);
        }
        return sorted;
    }
}
