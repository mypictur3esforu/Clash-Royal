import javax.swing.*;

public class ClashRoyal {
    static Spiel spiel;
    Card[] cardCollection;

    public static void main(String[] args) {
        ImageIcon silvarro = Stuff.ImageResizer(new ImageIcon("images/SilvarroPixilart.png"), 100, 100);
        new ClashRoyal(new Card[]{new Card("Card Nummero Uno", silvarro, 5, 30, 1000, 1000, 10), new Card("Card Nummero Dos", new ImageIcon("images/tower.png"), 10, 100, 75, 15, 100)});
    }

    ClashRoyal(Card[] cardCollection){
        UI.CreateUI();
        spiel = new Spiel("Casual", cardCollection);
        System.out.println("spiel defined");
        System.out.println("Spiel: " + ClashRoyal.spiel);
        this.cardCollection = cardCollection;
    }
}