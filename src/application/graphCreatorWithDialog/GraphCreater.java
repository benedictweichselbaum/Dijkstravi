package application.graphCreatorWithDialog;

import application.graphNavigation.graph.Graph;
import application.xmlParser.XMLParser;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

/**
 * This class creates the graph needed for the navigation.
 * Therefor it starts the XML parser and shows the user a dialog
 * that the graph gets calculated before the main window opens.
 * The graph creater is an extra thread.
 */
public class GraphCreater extends Thread{

    private HashMap<Integer, String> listOfExitsById;
    private JFrame openingDialog;

    private Graph graph;

    public GraphCreater (HashMap<Integer, String> listOfExitsById) {
        this.graph = null;
        this.listOfExitsById = listOfExitsById;
    }

    @Override
    public void run () {
        synchronized (this) {
            XMLParser xmlParser = new XMLParser(listOfExitsById);
            showCreateDialog();
            graph = xmlParser.init();
            this.openingDialog.dispose();
            notify();
        }
    }

    private void showCreateDialog () {
        openingDialog = new JFrame("Graph berechnen");
        Container pane = openingDialog.getContentPane();
        JLabel jLabel = new JLabel("        Der Graph wird berechnet...");
        openingDialog.setLayout(new GridLayout(1, 1));
        openingDialog.setLocation(800, 400);

        pane.add(jLabel);
        //pane.add(jProgressBar);

        openingDialog.setSize(350, 100);
        openingDialog.setResizable(false);
        openingDialog.setVisible(true);
    }

    public Graph getGraph() {
        return graph;
    }

}
