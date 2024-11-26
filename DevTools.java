import javax.swing.*;
import java.awt.*;

public class DevTools extends JPanel {
    private CardLayout cardLayout = new CardLayout();
    private CardEditor cardEditor;
    private CardCreator cardCreator;
    private JPanel viewPanel = new JPanel(cardLayout);
    private JButton up, down;

//    Ganz komisches Layout, deswegen geht das irgendwie nicht
    DevTools(){
        DevUI();
        viewPanel.setBackground(MainUI.vibe);;
        cardEditor = new CardEditor();
        cardCreator = new CardCreator();
        Add();
    }

    void Add(){
        viewPanel.add(cardCreator);
        viewPanel.add(cardEditor);
        add(viewPanel);
    }

    void DevUI(){
        String[] arrows = new String[]{"↑", "↓"};
        for (int i = 0; i < arrows.length; i++){
            JButton button = new JButton(arrows[i]);
            int finalI = i;
            button.addActionListener(ev->{SwitchPane(arrows[finalI]);});
            button.setSize(MainUI.screenWidth, 30);
            if (i == 0) add(button, BorderLayout.NORTH);
            if (i == 1) add(button, BorderLayout.SOUTH);
        }
    }

    void SwitchPane(String direction){
        if (direction.equals("↑")) cardLayout.next(viewPanel);
        if (direction.equals("↓")) cardLayout.previous(viewPanel);
    }
}
