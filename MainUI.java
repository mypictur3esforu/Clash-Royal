import javax.swing.*;
import javax.swing.tree.DefaultTreeCellEditor;
import java.awt.*;
import java.util.Objects;

public class MainUI {
    static JFrame frame = new JFrame();
    static JPanel cardPanel = new JPanel(new CardLayout());
    static JPanel menu = new JPanel(), collection = new JPanel(), shop = new JPanel();
    static Game game;
    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    static boolean devToolsEnabled = true;

    static int screenWidth = screenSize.width, screenHeight = screenSize.height, gameWidth = 700, gameHeight = screenHeight;

    static void CreateMainUI() {
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
        cardPanel.add(collection, "Collection");
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
            cardPanel.add(game,  "GameUI");
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