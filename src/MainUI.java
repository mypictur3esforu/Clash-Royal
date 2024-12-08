import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class MainUI {
    private static JFrame frame = new JFrame();
    private static JPanel cardPanel = new JPanel(new CardLayout());
    private static JPanel menu = new JPanel(), shop = new JPanel();
    static GameUI gameUI;
    private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private static boolean devToolsEnabled = true;
    static Color vibe = new Color(0x878787);

    static int screenWidth = screenSize.width, screenHeight = screenSize.height, gameWidth = 700, gameHeight = screenHeight;

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

    static void FrameDefinition() {
        frame = new JFrame("Clash Royal");
//        frame.setLayout(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setMinimumSize(new Dimension(screenWidth, 300));
    }

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