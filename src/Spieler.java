import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;

public class Spieler {
    String name;
    ArrayList<Card> cardSelection = new ArrayList<>();
    Color color;

    Spieler(String name, ArrayList<Card> cardSelection, Color color){
        this.name = name;
//        this.cardSelection = cardSelection;
        if (cardSelection == null) return;
        for (int i = 0; i < 8; i++) {
            this.cardSelection.add(cardSelection.get(i % cardSelection.size()));
        }
        this.color = color;
    }

    void ActualizeSelection(int place){
        Card temp = cardSelection.get(place);
        cardSelection.set(place, cardSelection.get(4));
        cardSelection.remove(4);
        cardSelection.add(temp);

    }
}
