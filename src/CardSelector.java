import javax.swing.*;

public class CardSelector extends JButton {
    private Card inheritedCard;

    public Card GetInheritedCard(){return inheritedCard;}
    public void SetInheritedCard(Card card){inheritedCard = card;}

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
