import javax.swing.*;

public class CardSelector extends JButton {
    private Card inheritedCard;

    CardSelector(Card inheritedCard){
        this.inheritedCard = inheritedCard;
        if (inheritedCard != null) ActualizeImage();
    }

    void NewCard(Card newCard){
        inheritedCard = newCard;
        ActualizeImage();
    }

    void ActualizeImage(){
        setIcon(inheritedCard.GetIcon());
    }
}
