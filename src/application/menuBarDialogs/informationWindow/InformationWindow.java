package application.menuBarDialogs.informationWindow;

import javax.swing.*;
import java.awt.*;

public class InformationWindow extends JFrame{

    Container pane;
    JLabel informationText;
    JButton btnClose;

    public InformationWindow () {
        super("Informationen");
        this.pane = this.getContentPane();
        this.setLayout(new FlowLayout());
        this.setSize(700, 200);
        this.initJObjectsForInformationWindow();
        this.setResizable(false);
        this.setLocation(675, 300);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pane.add(informationText);
        pane.add(btnClose);
    }

    private void initJObjectsForInformationWindow () {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<html><p>");
        stringBuilder.append("<h2>Programm \"Dijkstravi\":</h2> <br/>" +
                "Geschrieben von: Daniel Kraus, Dominic Fischer, Sebastian Dürr und Benedict Weichselbaum <br/>" +
                "Entstanden im Rahmen der Vorlesung \"Programmieren I\" an der DHBW Stuttgart Campus Horb");
        stringBuilder.append("</br></p></html>");
        this.informationText = new JLabel(stringBuilder.toString());
        this.btnClose = new JButton("Schließen");
        this.btnClose.addActionListener(actionEvent -> this.dispose());
    }
}
