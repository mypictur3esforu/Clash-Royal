import javax.swing.*;

/**
 * Buttons, die im Spiel zu Karten Auswahl dienen
 */
public class CardSelector extends JButton {
    private Card inheritedCard;

    public Card GetInheritedCard(){return inheritedCard;}

    /**
     * Erstellt einen neuen Button mit einer Karte dessen Bild angezeigt wird
     * @param inheritedCard Die Karte die als Bild angezeigt werden soll
     */
    CardSelector(Card inheritedCard){
        this.inheritedCard = inheritedCard;
        if (inheritedCard != null) ActualizeImage();
    }

    /**
     * Ändert die bisher angezeigte Karte zu einer neuen
     * @param newCard Die neue Karte
     */
    void NewCard(Card newCard){
        inheritedCard = newCard;
        ActualizeImage();
    }

    /**
     * Gibt dem JButton das ImageIcon der gespeicherten (neuen) Karte
     */
    void ActualizeImage(){
        setIcon(inheritedCard.GetIcon());
    }
}
