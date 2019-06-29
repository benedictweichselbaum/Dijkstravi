package application.starterProgressDialog;

import application.graphNavigation.Graph;
import application.xmlParser.XMLParser;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.*;
import java.util.HashMap;

public class GraphCreater extends Thread{

    private JProgressBar progressBar;
    private JLabel progressText;
    private HashMap<Integer, String> listOfExitsById;

    private Graph graph;

    public GraphCreater (JProgressBar pb, JLabel pt, HashMap listOfExitsById) {
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
