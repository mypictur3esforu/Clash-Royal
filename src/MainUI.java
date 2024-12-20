import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * Die UI des Menus, also alles was nicht das Spielfeld ist
 */
public class MainUI {
    /**
     * Frame in dem das gesamte Spiel angezeigt wird
     */
    private static JFrame frame = new JFrame();
    /**
     * Das Panel, dass das Seitenwechseln ermöglicht
     */
    private static JPanel cardPanel = new JPanel(new CardLayout());
    /**
     * JPanel in dem der StartScreen angezeigt wird
     */
    private static JPanel menu = new JPanel();
    /**
     * JPanel in dem der Shop angezeigt wird
     */
    private static JPanel shop = new JPanel();
    /**
     * Die UI des Spielfelds
     */
    static GameUI gameUI;
    /**
     * Größe des Screens
     */
    private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    /**
     * Zeigt an ob Developer Tools zur Verfügung stehen
     */
    private static boolean devToolsEnabled = true;
    /**
     * Die Farbe in der einige große Flächen eingefärbt werden
     */
    static Color vibe = new Color(0x878787);

    /**
     * Breite des Screens
     */
    static int screenWidth = screenSize.width;
    /**
     * Höhe des Screens
     */
    static int screenHeight = screenSize.height;
    /**
     * Breite des Spielfelds
     */
    static int gameWidth = 700;
    /**
     * Höhe des Spielfelds bzw. des Screens
     */
    static int gameHeight = screenHeight;

    /**
     * Erstellt die Menu UI
     */
    static void CreateMainUI() {
        shop.setBackground(vibe);
        menu.setBackground(MainUI.vibe);
        FrameDefinition();
        Menu();
        AddPanels();
        SwapPanel("Menu");
        frame.add(cardPanel);
        SwapButtons();
        frame.setVisible(true);
    }

    /**
     * Fügt alle Komponenten ein
     */
    static void AddPanels(){
        cardPanel.add(shop, "Shop");
        cardPanel.add(new Collection(ClashRoyal.staticCardCollection), "Collection");
        cardPanel.add(menu, "Menu");
//        cardPanel.add(new DevTools(), "DevCollection");
        if (devToolsEnabled){
        cardPanel.add(new CardCreator());
        cardPanel.add(new CardEditor());
        }
    }

    /**
     * Verändert den Frame
     */
    static void FrameDefinition() {
        frame = new JFrame("Clash Royal");
//        frame.setLayout(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setMinimumSize(new Dimension(screenWidth, 300));
    }

    /**
     * Erstellt den StartScreen
     */
    static void Menu() {
        menu.setLayout(null);
        JButton startGame = new JButton("Start Game");
        startGame.addActionListener(ev -> {
            ClashRoyal.clashRoyal.NewGame();
            cardPanel.add(gameUI,  "GameUI");
            SwapPanel("GameUI");
        });
        startGame.setBounds(screenWidth / 2 - 350, 0, 700, screenHeight);
        menu.add(startGame);
    }

    /**
     * Die Knöpfe, die zum Seitenwechsel dienen
     */
    static void SwapButtons() {
        String[] directions = new String[]{"<", ">"};

        for (int i = 0; i < 2; i++) {
            JButton button = new JButton(directions[i]);
            button.setBackground(Color.white);
            if (i == 0) {
                frame.add(button, BorderLayout.WEST);
                button.addActionListener(e -> {
                SwapPanel("prev");
                });
            } else {
                frame.add(button, BorderLayout.EAST);
                button.addActionListener(e -> {
                    SwapPanel("next");
                });
            }
        }
    }

    /**
     * Wechselt die Seiten entsprechend dem input
     * @param name Name der Richtung oder des JPanels
     */
    static void SwapPanel(String name) {
        CardLayout cLayout = (CardLayout) (cardPanel.getLayout());
        if (Objects.equals(name, "next")) {
            cLayout.next(cardPanel);
        } else if (Objects.equals(name, "prev")) {
            cLayout.previous(cardPanel);
        } else {
            cLayout.show(cardPanel, name);
        }
    }

    
}