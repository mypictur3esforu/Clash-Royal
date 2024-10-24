import java.util.ArrayList;
import java.util.Objects;

public class ClashRoyal {
    static Spiel spiel;
    static ArrayList<Card> staticCardCollection;
//            new Card[]{new Card("Silvarro", new ImageIcon("images/SilvarroPixilart.png"), 5, 200, 800, 500, 50, 300, 100, 100),
//            new Card("Turm", new ImageIcon("images/tower.png"), 10, 100, 75, 15, 100, 300, 50, 50)};
//    Card[] cardCollection;
    ArrayList<Card> cardCollection;
    static ClashRoyal clashRoyal;

    public static void main(String[] args) {
       clashRoyal = new ClashRoyal();
    }

    ClashRoyal(){
        cardCollection = FileHandler.GetCards();
        ClashRoyal.staticCardCollection = cardCollection;
        new Collection(cardCollection);
        MainUI.CreateMainUI();
    }

    void NewGame(){
        spiel = new Spiel("Casual", cardCollection);
    }

    /**
     * Findet die Karte mit dem gegebenem Namen
     * @param name Name der gesuchten Karte
     * @return Die gesuchte Karte
     */
    static Card GetCardByName(String name){
        for (Card card: staticCardCollection){
            if (Objects.equals(name, card.name)) return card;
        }
        return null;
    }
}