import java.util.ArrayList;

public class Spieler {
    String name;
    ArrayList<Card> cardSelection = new ArrayList<>();

    Spieler(String name, ArrayList<Card> cardSelection){
        this.name = name;
//        this.cardSelection = cardSelection;
        if (cardSelection == null) return;
        for (int i = 0; i < 8; i++) {
            this.cardSelection.add(cardSelection.get(i % cardSelection.size()));
        }
    }

    void ActualizeSelection(int place){
        Card temp = cardSelection.get(place);
        cardSelection.set(place, cardSelection.get(4));
        cardSelection.remove(4);
        cardSelection.add(temp);

    }
}
