import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

public class CardEditor extends JPanel {
    ArrayList<String> cards;
    JPanel overview;
    ArrayList<ArrayList<JTextField>> inputs = new ArrayList<>();

    CardEditor(){
        setLayout(new GridLayout(1, 1));
        cards = FileHandler.ReadFile();
        CreateOverview();
        Add();
    }

    void Add(){
        add(overview);
    }

    /**
     * Erstellt eine Übersicht über alle Karten, die es im Spiel gibt und gibt die Möglichkeit stats zu verändern.
     */
    void CreateOverview(){
        //Ich mag JTable nicht :)
        String[] categories = FileHandler.stats;
        ArrayList<Card> cardsInGame = ClashRoyal.staticCardCollection;
        overview = new JPanel(new GridLayout(cardsInGame.size() + 5, 1, 0, 5));
        HeadlineRow(categories);
        CardOverview(categories, cardsInGame);
    }

    /**
     * Fügt die erste Reihe mit den Überschriften zur Overview hinzu
     * @param stats Stats / Kategorien
     */
    void HeadlineRow(String[] stats){
        ArrayList<String> categories = new ArrayList<>();
        Collections.addAll(categories, stats);
        categories.add("Save");
        JPanel headlineRow = new JPanel(new GridLayout(1, categories.size()));
        for (String category : categories){
            JLabel headline = new JLabel(category, SwingConstants.CENTER);
            headline.setOpaque(true);
            headline.setBackground(new Color(0xFF10DFB9, true));
            headline.setBorder(BorderFactory.createLineBorder(Color.black, 3));
            headline.setFont(new Font("Arial", Font.BOLD, 20));
            headlineRow.add(headline);
        }
        overview.add(headlineRow);
    }

    /**
     * Fügt der Overview alle Karten mit Informationen und Inputs hinzu
     * @param categories Stats
     * @param cardsInGame Karten
     */
    void CardOverview(String[] categories, ArrayList<Card> cardsInGame){
        int i = 0;
//        Ich mag das enhanced for mehr als for i
        for (Card card : cardsInGame) {
            ArrayList<JTextField> textFieldsOfRow = new ArrayList<>();
            JPanel row = new JPanel(new GridLayout(1, categories.length + 1, 2,0));
            for (String category : categories){
                JLabel value = new JLabel(card.GetStat(category), SwingConstants.CENTER);
                value.setOpaque(true);
                value.setBackground(new Color(0xFFADD0D6, true));
                JTextField input = new JTextField();
                textFieldsOfRow.add(input);
                inputs.add(textFieldsOfRow);
                JPanel stat = new JPanel(new GridLayout(2, 1));
                stat.setBorder(BorderFactory.createLineBorder(Color.black, 2, true));
                stat.add(value);
                stat.add(input);
                row.add(stat);
            }
            JButton save = new JButton("Save Changes");
            int finalI = i;
            save.addActionListener(ev->{SaveChanges(finalI);});
            row.add(save);
            overview.add(row);
            i++;
        }
    }

    void SaveChanges(int row){

    }
}
