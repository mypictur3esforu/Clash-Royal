import java.util.ArrayList;
import java.util.Objects;

public class ClashRoyal {
    private static Spiel spiel;
    public static ArrayList<Card> staticCardCollection;
//    Card[] cardCollection;
    private ArrayList<Card> cardCollection;
    public static ClashRoyal clashRoyal;

    public static void main(String[] args) {
       clashRoyal = new ClashRoyal();
    }

    ClashRoyal(){
        cardCollection = FileHandler.GetCards();
        ClashRoyal.staticCardCollection = cardCollection;
        new Collection(cardCollection);
        MainUI.CreateMainUI();
    }

    public void NewGame(){
        spiel = new Spiel("Casual", cardCollection);
    }

    /**
     * Findet die Karte mit dem gegebenem Namen
     * @param name Name der gesuchten Karte
     * @return Die gesuchte Karte
     */
    public static Card GetCardByName(String name){
        for (Card card: staticCardCollection){
            if (Objects.equals(name, card.GetName())) return card;
        }
        return null;
    }
}