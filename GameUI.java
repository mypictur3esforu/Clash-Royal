import javax.swing.*;
import java.awt.*;

public class GameUI {
    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    static int screenWidth =  screenSize.width;
    static int screenHeight = screenSize.height;
//    static JFrame frame;
    static Map playarea;
    static JButton overlayButton;
    static JButton[] cardButtons = new JButton[4];
    static JPanel troopSelectionPanel, gamePanel;
    static int gameWidth = 700, gameHeight = 1080;

    static {
        gamePanel = new JPanel();
        gamePanel.setLayout(null);
        gamePanel.setSize(screenSize);
        troopSelectionPanel = new JPanel();
        overlayButton = new JButton();
    }

    static void CreateUI(){
        OverlayButton();

//        playarea = new Map();
        playarea.setLocation(screenWidth/2 - 350, 0);

        gamePanel.add(overlayButton);
        gamePanel.add(playarea);
        gamePanel.add(troopSelectionPanel);

//        MainUI.frame.add(gamePanel);
//        MainUI.frame.setVisible(true);
    }

    /**
     * Unsichtbarer Button, der Klicks auf dem Feld wahrnimmt und Koordinaten durchgibt
     */
    static void OverlayButton(){
        overlayButton.setSize(gameWidth, gameHeight);
        overlayButton.setLayout(null);
        overlayButton.setLocation(screenWidth/2 - 350, 0);
        overlayButton.setBorderPainted(false);
        overlayButton.setFocusPainted(false);
        overlayButton.setContentAreaFilled(false);
    }

    /**
     * Die 4 Button in denen man Truppen auswählen kann
     * @param troopSelection Die Truppen die in diesem Spiel vorkommen bzw. ausgewählt werden können
     */
    static void TroopSelectionUI(TroopSelection troopSelection){
        troopSelectionPanel.setLayout(new GridLayout(2, 2));
        troopSelectionPanel.setBounds(0, 0, 600, 1080);
        troopSelectionPanel.setBackground(Color.blue);

        for (int i = 0; i < 4; i++) {
            JButton button = new JButton();
            int finalI = i;
            button.addActionListener(ev->{
                troopSelection.TroopSelected(finalI);
            });
            GameUI.cardButtons[i] = button;
//            button.setIcon(troopsSet[finalI].icon);
            button.setText(troopSelection.troopsSet[finalI].name);
            troopSelectionPanel.add(button);
        }
//        panel.add(troopSelectionPanel);
//        UI.frame.setComponentZOrder(UI.troopSelectionPanel, 4);
        GameUI.gamePanel.setVisible(true);
    }

    void NewButtonValue(int buttonNumber){

    }
}
