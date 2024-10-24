import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Team extends JPanel {
    ArrayList<Card> team = new ArrayList<>();
    JPanel teamPanel;

    Team(){
        teamPanel = new JPanel(new GridLayout(1, 1));
        CreateTeam();
        add(teamPanel);
    }

    void CreateTeam(){
        teamPanel = new JPanel(new GridLayout());
        JPanel cardsOfTeam = new JPanel(new GridLayout(2, 4));
        for (Card card : team){
            if (card == null ) continue;
            cardsOfTeam.add(Collection.CreateCardInfoPanel(card, new String[]{""}));
        }
        teamPanel.add(cardsOfTeam);
    }

    ArrayList<Card> CreateRandomTeam(ArrayList<Card> potCards){
        while (potCards.size() < 8){
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
}
