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
    private JFrame jFrame;

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
            this.jFrame.dispose();
            notify();
        }
    }

    private void showCreateDialog () {
        jFrame = new JFrame("Graph berechnen");
        Container pane = jFrame.getContentPane();
        JLabel jLabel = new JLabel("        Der Graph wird berechnet...");
        jFrame.setLayout(new GridLayout(1, 1));
        jFrame.setLocation(800, 400);

        pane.add(jLabel);
        //pane.add(jProgressBar);

        jFrame.setSize(350, 100);
        jFrame.setResizable(false);
        jFrame.setVisible(true);
    }

    public Graph getGraph() {
        return graph;
    }

}
