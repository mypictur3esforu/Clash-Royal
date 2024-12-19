import java.util.ArrayList;
import java.util.Objects;

/**
 * Die Main Klasse. Sie erstellt das Menu und ermöglicht es ein Spiel zu starten. Zudem erstellt sie eine Kollektion mit allen Karten
 */
public class ClashRoyal {
    private static Spiel spiel;
    public static ArrayList<Card> staticCardCollection;
//    Card[] cardCollection;
    //private ArrayList<Card> cardCollection;
    public static ClashRoyal clashRoyal;

    public static void main(String[] args) {
       clashRoyal = new ClashRoyal();
    }

    /**
     * Liest alle gespeicherten Karten aus erstellt das Menu
     */
    ClashRoyal(){
        staticCardCollection = FileHandler.GetCards();
        //ClashRoyal.staticCardCollection = cardCollection;
//        new Collection(staticCardCollection);
        MainUI.CreateMainUI();
    }

    /**
     * Startet ein neues Spiel
     */
    public void NewGame(){
        spiel = new Spiel("Casual", staticCardCollection);
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