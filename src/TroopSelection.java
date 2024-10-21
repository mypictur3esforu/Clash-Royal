import javax.swing.*;
import java.awt.*;

public class TroopSelection {
    Card[] troopsSet = new Card[8];

    TroopSelection(Card[] troopSet){
//        this.troopsSet = troopSet;
        for (int i = 0; i < troopsSet.length; i++) {
            troopsSet[i] = troopSet[i % troopSet.length];
        }
        GameUI.TroopSelectionUI(this);
    }

    /**
     * Angeklickte Truppe
     * @param buttonNumber Zahl des Button / welcher Button geklickt wurde
     */
    void TroopSelected(int buttonNumber){
        System.out.println("Troop chosen: " + buttonNumber);
        ClashRoyal.spiel.feld.SelectTroop(troopsSet[buttonNumber]);
    }

}
