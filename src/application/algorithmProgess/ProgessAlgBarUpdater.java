package application.algorithmProgess;

import application.DijkstraviController;
import application.graphNavigation.algorithms.NavigationService;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class ProgessAlgBarUpdater extends Thread {

    private NavigationService navigationService;
    private DijkstraviController dijkstraviController;

    public ProgessAlgBarUpdater (DijkstraviController controller, NavigationService navigationService) {
        this.navigationService = navigationService;
        this.dijkstraviController = controller;
    }

    @Override
    public void run() {
        double progress;
        while ((progress = navigationService.getProgress()) <= 1.0) {
            dijkstraviController.getPbAlgorithms().setProgress(progress*100);
            dijkstraviController.getLblProgress().setText("Berechnung läuft");
        }
        dijkstraviController.getLblProgress().setText("Fertig");
    }
}
