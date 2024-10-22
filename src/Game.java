import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Game extends JPanel {
    JPanel selectButtons, game, map, restrictHalf;
    JButton overlayButton;
    CardSelector[] buttons = new CardSelector[4];
    ImageIcon icon = new ImageIcon("images/crtestmap.png");

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    int width, height, screenWidth = screenSize.width, screenHeight = screenSize.height; ;

    Game(int width, int height, ArrayList<Card> cards){
        this.width = width;
        this.height = height;
        game = new JPanel();
        game.setLayout(null);
        game.setBounds(0, 0, screenWidth, screenHeight);

        setLayout(null);
        setBackground(Color.black);

        CreateMap();
        CreateOverlayButton();
        CreateSelectButtons(cards);
        CreateRestrict();
        Add();
    }

    void Add(){
        game.add(restrictHalf);
        game.add(overlayButton);
        game.add(selectButtons);
        game.add(map);
        game.setVisible(true);
        add(game);
    }

    void CreateMap(){
        map = new JPanel();
        map.setBackground(Color.white);
        JLabel label = new JLabel(Card.ImageResizer(icon, width, height));
        map.add(label);
        map.setBounds(screenWidth / 2 - width / 2, 0, width, height);
        map.setVisible(true);
    }

    void CreateOverlayButton(){
        overlayButton = new JButton();
//        overlayButton.setBounds(screenWidth / 2 - width / 2, 0, width, height);
        overlayButton.setSize(width, height);
        overlayButton.setLayout(null);
        overlayButton.setLocation(screenWidth / 2 - width / 2, 0);
        overlayButton.setBorderPainted(false);
        overlayButton.setFocusPainted(false);
        overlayButton.setContentAreaFilled(false);
    }

    void CreateSelectButtons(ArrayList<Card> cards){
        selectButtons = new JPanel(new GridLayout(4, 1));
        for (int i = 0; i < 4; i++) {
            CardSelector button = new CardSelector(cards.get(i));
            buttons[i] = button;
            selectButtons.add(button);
        }
        selectButtons.setBounds(0, 0, screenWidth / 2 - width / 2, screenHeight);
    }

    void CreateRestrict(){
        restrictHalf = new JPanel(new GridLayout(1, 1));
        JLabel label = new JLabel();
        label.setOpaque(true);
        label.setBackground(new Color(0x62DD033B, true));
        restrictHalf.add(label);
        restrictHalf.setBounds(screenWidth / 2 - width / 2, 0, width, height / 2 + height / 10);
        restrictHalf.setVisible(false);
    }

    void ActualizeButton(int buttonNumber, Card newCard){
        buttons[buttonNumber].NewCard(newCard);
    }
}
