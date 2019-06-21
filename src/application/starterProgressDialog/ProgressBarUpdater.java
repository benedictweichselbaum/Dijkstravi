package application.starterProgressDialog;

import application.xmlParser.XMLParser;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;

public class ProgressBarUpdater extends Thread{

    ProgressBar progressBar;
    Text progressText;
    XMLParser xmlParser;

    ProgressBarUpdater (ProgressBar pb, Text pt, XMLParser xmlAL){
        this.progressBar = pb;
        this.progressText = pt;
        this.xmlParser = xmlAL;
    }

    @Override
    public void run() {
        while (progressBar.getProgress() < 1.00) {
            progressBar.setProgress(xmlParser.getProgress());
            double progress = progressBar.getProgress();
            if (progress < 0.45 && progress > 0.25) {
                progressText.setText("Erzeuge Liste von benötigten Knotenpunkten");
            } else if (progress < 0.75 && progress > 0.45) {
                progressText.setText("Füge Knoten in den Graphen ein");
            } else if (progress > 0.75){
                progressText.setText("Füge Kanten in den Graphen ein");
            }
        }
        progressText.setText("Graph berechnet. Schließen sie das Fenster");
    }
}
