import javax.swing.*;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.ArrayList;

public class CardsInGame extends JPanel {
    ArrayList<String> cards;
    JPanel collection;

    CardsInGame(){
        setLayout(new GridLayout(1, 1));
        cards = Filed.ReadFile();
        CreateTable();
        Add();
    }

    void Add(){
        add(collection);
    }

    void CreateTable(){
        collection = new JPanel(new GridLayout(1, 1));
        String[] tableHeader;
        String[][] data={ {"101","Amit","670000"},
                {"102","Jai","780000"},
                {"101","Sachin","700000"}};
        String[] column={"ID","NAME","SALARY"};
        JTable table = new JTable(data, column);
        JScrollPane sp=new JScrollPane(table);
        collection.add(sp);
    }
}
