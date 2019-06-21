package application.starterProgressDialog;

import application.Main;
import application.xmlParser.XMLaltTest;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GraphCreater extends Thread{

    private ProgressBar progressBar;
    private Text progressText;
    private Stage stage;

    public GraphCreater (ProgressBar pb, Text pt, Stage s) {
        this.progressBar = pb;
        this.progressText = pt;
        this.stage = s;
    }

    @Override
    public void run () {
        XMLaltTest xmLaltTest = new XMLaltTest();
        ProgressBarUpdater progressBarUpdater = new ProgressBarUpdater(progressBar, progressText, xmLaltTest);
        progressBarUpdater.start();
        xmLaltTest.init();
        synchronized (stage) {
            stage.notify();
        }
    }
}
