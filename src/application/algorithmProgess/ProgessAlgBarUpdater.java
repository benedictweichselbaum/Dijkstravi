package application.algorithmProgess;

import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class ProgessAlgBarUpdater extends Thread {

    ProgressBar progressBar;
    Label lblToUpdate;
    //still to implement

    public ProgessAlgBarUpdater (ProgressBar pb, Label lbl) {
        this.progressBar = pb;
        this.lblToUpdate = lbl;
    }

    @Override
    public void run() {
        //still to implement
    }
}
