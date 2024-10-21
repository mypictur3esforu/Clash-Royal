import javax.swing.*;
import java.util.ArrayList;

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
        cardCollection = Filed.GetCards();
        new Collection(cardCollection);
        System.out.println("Spiel: " + ClashRoyal.spiel);
        MainUI.CreateMainUI();
    }

    void NewGame(){
        spiel = new Spiel("Casual", cardCollection);
    }
}