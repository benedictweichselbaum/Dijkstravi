package application.starterProgressDialog;

import application.xmlParser.XMLaltTest;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;

public class ProgressBarUpdater extends Thread{

    ProgressBar progressBar;
    Text progressText;
    XMLaltTest xmLaltTest;

    ProgressBarUpdater (ProgressBar pb, Text pt, XMLaltTest xmlAL){
        this.progressBar = pb;
        this.progressText = pt;
        this. xmLaltTest = xmlAL;
    }

    @Override
    public void run() {
        while (progressBar.getProgress() < 1.00) {
            progressBar.setProgress(xmLaltTest.getProgress());
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
