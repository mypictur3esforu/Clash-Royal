import javax.swing.*;

public class ClashRoyal {
    static Spiel spiel;
    Card[] cardCollection;

    public static void main(String[] args) {
        new ClashRoyal(new Card[]{new Card("Silvarro", new ImageIcon("images/SilvarroPixilart.png"), 5, 200, 800, 500, 50, 300, 100, 100),
                new Card("Turm", new ImageIcon("images/tower.png"), 10, 100, 75, 15, 100, 300, 50, 50)});
    }

    ClashRoyal(Card[] cardCollection){
//        GameUI.CreateUI();
        new Collection(cardCollection);
        spiel = new Spiel("Casual", cardCollection);
        System.out.println("Spiel: " + ClashRoyal.spiel);
        this.cardCollection = cardCollection;
        MainUI.CreateMainUI();
    }
}