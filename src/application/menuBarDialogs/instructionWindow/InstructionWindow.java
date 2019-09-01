package application.menuBarDialogs.instructionWindow;

import javax.swing.*;
import java.awt.*;

/**
 * Simple Java Swing JFrame class for use of the programm.
 * Information text formatted with HTML.
 */
public class InstructionWindow extends JFrame{

    private JLabel informationText;
    private JButton btnClose;

    public InstructionWindow() {
        super("Kurzbedienungsanleitung des Programms");
        Container pane = this.getContentPane();
        this.setLayout(new FlowLayout());
        this.setSize(700, 850);
        this.initJObjectsForInformationWindow();
        this.setResizable(false);
        this.setLocation(200, 0);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pane.add(informationText);
        pane.add(btnClose);
    }

    private void initJObjectsForInformationWindow () {

        String informationText = "<html><body width=\"500px\"><p>" +
                "<h2>Bedienungsanleitung</h2> <br/>" +
                "<h3>Start und Ziel auswählen</h3>" +
                "<ul>" +
                "<li>In das Feld \"Von\" die ersten Buchstaben der Startautobahnauffahrt eintippen</li>" +
                "<li>In der erscheinenden Drop-Down Liste die gewünschte Startauffahrt wählen</li>" +
                "<li>In das Feld \"Nach\" die ersten Buchstaben der Zielausfahrt eintippen</li>" +
                "<li>In der erscheinenden Drop-Down Liste die gewünschte Zielausfahrt wählen</li>" +
                "</ul>" +
                "<h3>Algorithmus und Routenoption auswählen</h3>" +
                "<p>Unter dem Punkt \"Algorithmus\" kann zwischen vier Algorithmen ausgewählt werden:</p>" +
                "<ul>" +
                "<li>Dijstra Algorithmus: Der Standard Algorithmus</li>" +
                "<li>A* Algorithmus: Dieser Algorithmus ist eine Optimierung des Dijkstra-Algorithmus.</li>" +
                "<li>Bellman-Ford: Dieser Algorithmus berechnet vom ausgewählten Start zu jedem Knoten den kürzesten Weg</li>" +
                "<li>Shortest Path Faster Algorithmus: Dieser Algorithmus ist eine Optimierung des Bellman-Ford Algorithmus</li>" +
                "</ul>" +
                "<p>Mit den Knöpfen darunter kann ausgewählt werden, ob die kürzeste Strecke oder die schnellste Route berechnet werden soll.</p>" +
                "<h3>Berechnung starten</h3>" +
                "<p>Um die Berechnung mit den ausgewählten Eingaben zu starten, auf Berechnen klicken. Während der Berechnung ist im " +
                "Forschrittsbalken am unteren Rand des Fensters der aktuelle Berechnungsstatus zu sehen.</p>" +
                "<p>Nach der erfolgreichen Berechnung ist im Ausgabefeld die Routenbeschreibung und in der Deutschlandkarte ist die Route eingezeichnet.</p>" +
                "<p>Um Start und Ziel zu vertauschen, auf die Tauschpfeile neben dem Berechnen-Knopf drücken.</p>" +
                "<h3>In der Karte navigieren</h3>" +
                "<p>Es ist möglich das Bild zu zoomen, dafür muss zuerst auf die Karte geklickt werden. Anschließend können folgende Befehle ausgeführt werden:</p>" +
                "<li>Hereinzoomen: \"+\" oder \"P\"</li>" +
                "<li>Herauszoomen: \"-\" oder \"M\"</li>" +
                "<li>Im Bild navigieren: Pfeiltasten oder Mausrad</li>" +
                "<li>Bild zurückzoomen: N oder Z</li>" +
                "<li></li>"+
                "<h3>Einstellen einer Höchstgeschwindigkeit</h3>" +
                "<p>Über \"Einstellungen\" --> \"Optionen\" können Sie eine persönliche Höchstgeschwindigkeit einstellen, die bei der Zeitberechnung auf nicht beschränkten Fahrbahnabschnitten berücksichtigt wird.</p>" +
                "</ul>" +
                "</br></p></body></html>";
        this.informationText = new JLabel(informationText);
        this.btnClose = new JButton("Schließen");
        this.btnClose.addActionListener(actionEvent -> this.dispose());
    }
}
