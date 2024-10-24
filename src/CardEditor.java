import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class CardEditor extends JPanel {
    ArrayList<String> cards;
    JPanel overview;
    ArrayList<ArrayList<JTextField>> inputs = new ArrayList<>();
    JScrollPane overviewScroll;

    CardEditor(){
        setLayout(new GridLayout(1, 1));
        cards = FileHandler.ReadFile();
        CreateOverview();
        OverviewScrollPane();
        Add();
    }

    void Add(){
//        add(overviewScroll);
        add(overview);
    }

    void OverviewScrollPane(){
        JScrollBar sB = new JScrollBar(Adjustable.HORIZONTAL);
        sB.setBackground(Color.lightGray);
        overviewScroll = new JScrollPane(overview);
        overviewScroll.setHorizontalScrollBar(sB);
        overviewScroll.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 50));
    }

    /**
     * Erstellt eine Übersicht über alle Karten, die es im Spiel gibt und gibt die Möglichkeit stats zu verändern.
     */
    void CreateOverview(){
        //Ich mag JTable nicht :)
        String[] stats = FileHandler.stats;
        ArrayList<String> categories = new ArrayList<>();
        Collections.addAll(categories, stats);
        categories.add("Save:");
        ArrayList<Card> cardsInGame = ClashRoyal.staticCardCollection;
        overview = new JPanel(new GridLayout(cardsInGame.size() + 1, 1, 0, 5));
        HeadlineRow(categories);
        CardOverview(categories, cardsInGame);
        overview.setBackground(MainUI.vibe);
    }

    /**
     * Fügt die erste Reihe mit den Überschriften zur Overview hinzu
     * @param categories Stats / Kategorien
     */
    void HeadlineRow(ArrayList<String> categories){
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
    void CardOverview(ArrayList<String> categories, ArrayList<Card> cardsInGame){
        categories.removeLast();
//        Ich mag das enhanced for mehr als for i :)
        int i = 1;
        for (Card card : cardsInGame) {
            ArrayList<JTextField> textFieldsOfRow = new ArrayList<>();
            JPanel row = new JPanel(new GridLayout(1, categories.size() + 1, 2,0));
            for (String category : categories){
                JLabel value = new JLabel("Initial Value: " + card.GetStat(category), SwingConstants.CENTER);
                value.setOpaque(true);
                value.setBackground(new Color(0xFFADD0D6, true));

                JTextField input = new JTextField(card.GetStat(category));
                textFieldsOfRow.add(input);

                JPanel stat = new JPanel(new GridLayout(2, 1));
                stat.setBorder(BorderFactory.createLineBorder(Color.black, 1, true));

                stat.add(value);
                stat.add(input);
                row.add(stat);
            }
            inputs.add(textFieldsOfRow);

            JButton save = new JButton("Save Changes");
            int finalI = i;
            save.addActionListener(ev->{SaveChanges(finalI);});

            row.add(save);
            row.setBorder(BorderFactory.createLineBorder(new Color(0x9C448E), 3));
            overview.add(row);
            i++;
        }
    }

//    es werden die ganze Zeit die gleichen TextFields geaddet
    void SaveChanges(int row){
        ArrayList<JTextField> rowInput = inputs.get(row - 1);
        System.out.println(rowInput.getFirst().getText());
        String line = FileHandler.CreateMainStringOfJTextField(row, rowInput);
        FileHandler.EditFile(row, line);
    }
}
