import java.util.ArrayList;
import java.util.Objects;

public class ClashRoyal {
    static Spiel spiel;
    static ArrayList<Card> staticCardCollection;
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