package application.starterProgressDialog;

import application.xmlParser.XMLParser;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.HashMap;

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
        XMLParser xmlParser = new XMLParser(new HashMap<>());
        ProgressBarUpdater progressBarUpdater = new ProgressBarUpdater(progressBar, progressText, xmlParser);
        progressBarUpdater.start();
        xmlParser.init();
        synchronized (stage) {
            stage.notify();
        }
    }
}
