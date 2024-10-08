import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Timer;

public class UI {
    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    static int screenWidth =  screenSize.width;
    static int screenHeight = screenSize.height;
    static JFrame frame;
    static Window playarea;
    static JButton canvasButton;
    //static ArrayList<JButton>[] troopSelection;
    static JButton[] troopSelection = new JButton[4];
    static JPanel troopSelectionPanel;

    static void CreateUI(){
        frame = new JFrame();
        frame.setLayout(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setMinimumSize(new Dimension(1900, 300));

        canvasButton = new JButton();
//        canvasButton.setVisible(false);
        canvasButton.setSize(700, 1080);

        canvasButton.setLayout(null);
        canvasButton.setLocation(screenWidth/2 - 350, 0);
        canvasButton.setBorderPainted(false);
        canvasButton.setFocusPainted(false);
        canvasButton.setContentAreaFilled(false);

        playarea = new Window();
        playarea.setLocation(screenWidth/2 - 350, 0);
//        Towers();

        frame.add(canvasButton);
        frame.add(playarea);
        frame.setVisible(true);
    }


//    static void Towers(){
//        int[][] towerCords = new int[][]{{75, 130}, {325, 80}, {575, 130}};
//        for (int[] towerCord : towerCords) {
//
//            JLabel tower = new JLabel(new ImageIcon("images/tower.png"));
//            tower.setBounds(towerCord[0], towerCord[1], 50, 50);
//            canvasButton.add(tower);
//        }
//    }
}
