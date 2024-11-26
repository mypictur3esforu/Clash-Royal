import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CardCreator extends JPanel {
    private JPanel image, statInput, save;
    private JLabel headline;
    private ArrayList<JTextField> values = new ArrayList<>();
    private String[] stats;
    private JTextField imageRef;
    private int numberOfTroops;

    CardCreator(){
        stats = FileHandler.GetStats();
        numberOfTroops = FileHandler.ReadFile().size() - 1;
//        setBackground(new Color(0xFF777777, true));
//        GridBagLayout vermutlich besser
    setLayout(new GridLayout(4, 1));
    Headline();
    CreateStats();
    CreateImage();
    CreateSave();
    Add();
    }

    void Add(){
        add(headline);
        add(statInput);
        add(image);
        add(save);
    }

    void Headline(){
        headline = new JLabel("Troop Creator",SwingConstants.CENTER);
        headline.setFont(new Font("Arial", Font.BOLD, 50));
        headline.setOpaque(true);
        headline.setBackground(MainUI.vibe);
    }

    /**
     * Erzeugt Eingabe Felder für die Stats
     */
    void CreateStats(){
        statInput = new JPanel(new GridLayout(3, 4, 2, 2));
        for (String stat : stats){
            JPanel panel = new JPanel(new GridLayout(1, 2));
            panel.setBorder(BorderFactory.createLineBorder(Color.black));
            JLabel label = new JLabel(stat, SwingConstants.CENTER);
            label.setOpaque(true);
            label.setBackground(new Color(0x4587B1));
            panel.add(label);
            JTextField textField = new JTextField();
            values.add(textField);
            panel.add(textField);
            statInput.add(panel);
        }
    }

    /**
     * Erzeugt Eingabe Feld fürs Bild
     */
    void CreateImage(){
        image = new JPanel(new GridLayout(3, 1));
        JLabel label = new JLabel("Add Image", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 20));
        imageRef = new JTextField("Image Path Reference");
        JButton showButton = new JButton("Show");
        showButton.addActionListener(e -> {
            showButton.setIcon(new ImageIcon(imageRef.getText()));
        });
        image.add(label);
        image.add(imageRef);
        image.add(showButton);
    }

    /**
     * Erzeugt den SaveButton
     */
    void CreateSave(){
        save = new JPanel(new GridLayout(1, 1));
        JButton saveButton = new JButton("SAVE");
        saveButton.addActionListener(e -> {
            SaveNewCard();});
        saveButton.setFont(new Font("Arial", Font.BOLD, 30));
        save.add(saveButton);
        saveButton.setBackground(MainUI.vibe);
    }

    /**
     * Erzeugt und speichert die neue Karte
     */
    void SaveNewCard(){
        String string = CreateMainString();
        FileHandler.WriteToFile(string);
        FileHandler.ReadFile();
    }

    //Wäre besser, wenn man die Methode aus FileHandler nimmt damit keine Dopplung entsteht
    String CreateMainString(){
        StringBuilder string;
        numberOfTroops++;
        string = new StringBuilder(numberOfTroops + ". ");
        for (int i = 0; i < stats.length; i++) {
//            string += stats[i] + " " + values.get(i) + "; ";
            string.append(stats[i]).append(" ").append(values.get(i).getText()).append("; ");
        }
        string.append("Icon: ").append(imageRef.getText()).append("; ");
        System.out.println(string);
        return string + "";
    }



}
