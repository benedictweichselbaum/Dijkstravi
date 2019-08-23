package application.algorithmProgess;

import application.graphNavigation.algorithms.NavigationService;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class ProgessAlgBarUpdater extends Thread {

    private ProgressBar progressBar;
    private Label lblToUpdate;
    private NavigationService navigationService;
    public ProgessAlgBarUpdater (ProgressBar pb, Label lbl, NavigationService navigationService) {
        this.progressBar = pb;
        this.lblToUpdate = lbl;
        this.navigationService = navigationService;
    }

    @Override
    public void run() {
        double progress;
        while ((progress = navigationService.getProgress()) <= 1.0) {
            progressBar.setProgress(progress);
        }
    }
}
