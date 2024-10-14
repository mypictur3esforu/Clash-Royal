import javax.swing.*;

public class ClashRoyal {
    static Spiel spiel;
    Card[] cardCollection;

    public static void main(String[] args) {
        ImageIcon silvarro = Stuff.ImageResizer(new ImageIcon("images/SilvarroPixilart.png"), 100, 100);
        new ClashRoyal(new Card[]{new Card("Silvarro", silvarro, 5, 200, 800, 500, 50, 300), new Card("Turm", new ImageIcon("images/tower.png"), 10, 100, 75, 15, 100, 300)});
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