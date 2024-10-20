import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.util.ArrayList;

public class CardCreator extends JPanel {
    JPanel image, statInput, save;
    JLabel headline;
    ArrayList<JTextField> values = new ArrayList<>();
    String[] stats = new String[]{"Name:", "Speed:", "Range:", "Health:", "Damage:", "Attack Speed:", "Sight Distance:", "Width:", "Height:"};
    JTextField imageRef;

    CardCreator(){
        setBackground(new Color(0xFF777777, true));
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
    }

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

    void CreateSave(){
        save = new JPanel(new GridLayout(1, 1));
        JButton saveButton = new JButton("SAVE");
        saveButton.addActionListener(e -> {Save();});
        saveButton.setFont(new Font("Arial", Font.BOLD, 30));
        save.add(saveButton);
    }

    void Save(){
        String string = CreateMainString();
    }

    String CreateMainString(){
        StringBuilder string;
        int number = 1;
        string = new StringBuilder(number + ": ");
        for (int i = 0; i < stats.length; i++) {
//            string += stats[i] + " " + values.get(i) + "; ";
            string.append(stats[i]).append(" ").append(values.get(i).getText()).append("; ");
        }
        string.append("Icon: ").append(imageRef.getText()).append("; ");
        System.out.println(string);
        return string + "";
    }

}
