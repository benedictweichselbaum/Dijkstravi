package application.starterProgressDialog;

import application.xmlParser.XMLParser;
import javax.swing.*;

public class ProgressBarUpdater extends Thread{

    private JProgressBar progressBar;
    private JLabel progressText;
    private XMLParser xmlParser;

    ProgressBarUpdater (JProgressBar pb, JLabel pt, XMLParser xmlAL){
        this.progressBar = pb;
        this.progressText = pt;
        this.xmlParser = xmlAL;
    }

    @Override
    public void run() {
        progressBar.setValue(50);
        while (progressBar.getValue() < 100) {
            progressBar.setValue((int) Math.round(xmlParser.getProgress() * 100));
            int progress = progressBar.getValue();
            if (progress < 45 && progress > 25) {
                progressText.setText("Erzeuge Liste von benötigten Knotenpunkten");
            } else if (progress < 75 && progress > 45) {
                progressText.setText("Füge Knoten in den Graphen ein");
            } else if (progress > 75){
                progressText.setText("Füge Kanten in den Graphen ein");
            }
        }
        progressText.setText("Graph berechnet. Schließen sie das Fenster");
    }
}
