package application.menuBarDialogs.informationWindow;

import javax.swing.*;
import java.awt.*;

/**
 * Simple Java Swing JFrame class for information about the programm.
 * Information text formatted with HTML.
 */
public class InformationWindow extends JFrame{

    private JLabel informationText;
    private JButton btnClose;

    public InformationWindow () {
        super("Informationen");
        Container pane = this.getContentPane();
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

        String informationText = "<html><p>" +
                "<h2>Programm \"Dijkstravi\":</h2> <br/>" +
                "Geschrieben von: Daniel Kraus, Dominic Fischer, Sebastian Dürr und Benedict Weichselbaum <br/>" +
                "Entstanden im Rahmen der Vorlesung \"Programmieren I\" an der DHBW Stuttgart Campus Horb" +
                "</br></p></html>";
        this.informationText = new JLabel(informationText);
        this.btnClose = new JButton("Schließen");
        this.btnClose.addActionListener(actionEvent -> this.dispose());
    }
}
