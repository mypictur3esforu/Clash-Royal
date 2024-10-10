import javax.swing.*;
import java.awt.*;

public class TroopSelection {
    Card[] troopsSet = new Card[4];

    TroopSelection(Card[] troopSet){
//        this.troopsSet = troopSet;
        for (int i = 0; i < troopsSet.length; i++) {
            troopsSet[i] = troopSet[i % troopSet.length];
        }
        TroopSelectionUI();
    }

    void TroopSelectionUI(){
        UI.troopSelectionPanel = new JPanel();
        UI.troopSelectionPanel.setLayout(new GridLayout(2, 2));
        UI.troopSelectionPanel.setBounds(0, 0, 600, 1080);
        for (int i = 0; i < 4; i++) {
            JButton button = new JButton();
            int finalI = i;
            button.addActionListener(ev->{
                TroopSelected(finalI);
            });
            UI.troopSelection[i] = button;
//            button.setIcon(troopsSet[finalI].icon);
            UI.troopSelectionPanel.add(button);
        }
        UI.frame.add(UI.troopSelectionPanel);
//        UI.frame.setComponentZOrder(UI.troopSelectionPanel, 4);
        UI.frame.setVisible(true);
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
