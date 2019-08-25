package application.algorithmProgess;

import application.DijkstraviController;
import application.graphNavigation.algorithms.NavigationService;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class ProgressAleBarUpdater extends Thread {

    private NavigationService navigationService;
    private DijkstraviController dijkstraviController;

    public ProgressAleBarUpdater(DijkstraviController controller, NavigationService navigationService) {
        this.navigationService = navigationService;
        this.dijkstraviController = controller;
    }

    @Override
    public void run() {
        double progress;
        while ((progress = navigationService.getProgress()*100) <= 1.0) {
            if (progress >= 0.9) {
                dijkstraviController.getPbAlgorithms().setProgress(1.0);
                return;
            }

            dijkstraviController.getPbAlgorithms().setProgress(progress);
        }
    }
}
