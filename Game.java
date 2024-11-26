import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Game extends JPanel {
    private JPanel selectButtons, game, map, restrictHalf, elixirBar;
    JButton overlayButton;
     CardSelector[] buttons = new CardSelector[4];
    private ImageIcon icon = new ImageIcon("images/crtestmap.png");
    private JProgressBar elixirPBar, troopCost;

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    int width, height, screenWidth = screenSize.width, screenHeight = screenSize.height;

    public void SetRestrictHalfVisible(boolean visible){restrictHalf.setVisible(visible);}
    Game(int width, int height, ArrayList<Card> cards){
        this.width = width;
        this.height = height;
//        this.height = 800;
        game = new JPanel();
        game.setLayout(null);
//        game.setBounds(0, 0, screenWidth, screenHeight);
        game.setBounds(0, 0, screenWidth, screenHeight);
        game.setBackground(MainUI.vibe);

        setLayout(null);
        setBackground(Color.black);

        CreateMap();
        CreateOverlayButton();
        CreateSelectButtons(cards);
        CreateRestrict();
        CreateElixirBar();
        Add();
    }

    void Add(){
        //game.add(restrictHalf);
        game.add(overlayButton);
        game.add(selectButtons);
        game.add(map);
        game.add(elixirBar);
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

    void CreateElixirBar(){
        elixirBar = new JPanel(null);
        elixirPBar = new JProgressBar(JProgressBar.VERTICAL);
        elixirPBar.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
        elixirPBar.setForeground(new Color(0x7511FA));

        troopCost = new JProgressBar(JProgressBar.VERTICAL);
        troopCost.setForeground(new Color(0x80FF0000, true));
        troopCost.setOpaque(false);
        troopCost.setBorderPainted(false);

        elixirPBar.add(troopCost);
        elixirBar.add(elixirPBar);
        elixirBar.setBounds(screenWidth / 2 + width / 2, 0, 200, height);
        troopCost.setBounds(2, 0, elixirBar.getWidth() - 4, elixirBar.getHeight());
        elixirPBar.setBounds(0, 0, elixirBar.getWidth(), elixirBar.getHeight());
//        elixirBar.setBounds(1000, 0, 1000, 1000);
    }

    void UpdateElixirBar(double amountOfElixir, double selectedTroopCost){
        elixirPBar.setValue((int) (100 / 10 * amountOfElixir));
        troopCost.setValue((int) (100 / 10 * selectedTroopCost));
    }

    void ActualizeButton(int buttonNumber, Card newCard){
        buttons[buttonNumber].NewCard(newCard);
    }
}
