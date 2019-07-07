package application.starterProgressDialog;

import application.graphNavigation.graph.Graph;
import application.xmlParser.XMLParser;

import javax.swing.*;
import java.util.HashMap;

public class GraphCreater extends Thread{

    private JProgressBar progressBar;
    private JLabel progressText;
    private HashMap<Integer, String> listOfExitsById;

    private Graph graph;

    public GraphCreater (JProgressBar pb, JLabel pt, HashMap<Integer, String> listOfExitsById) {
        this.progressBar = pb;
        this.progressText = pt;
        this.graph = null;
        this.listOfExitsById = listOfExitsById;
    }

    @Override
    public void run () {
        synchronized (this) {
            XMLParser xmlParser = new XMLParser(listOfExitsById);
            ProgressBarUpdater progressBarUpdater = new ProgressBarUpdater(progressBar, progressText, xmlParser);
            progressBarUpdater.start();
            graph = xmlParser.init();
            notify();
        }
    }

    public Graph getGraph() {
        return graph;
    }

}
