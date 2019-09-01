package application.algorithmProgess;

import application.DijkstraviController;
import application.graphNavigation.algorithms.AbstractAlgorithm;

public class ProgressBarAlgorithmUpdater extends Thread {

    private AbstractAlgorithm abstractAlgorithm;
    private DijkstraviController dijkstraviController;

    public ProgressBarAlgorithmUpdater(DijkstraviController controller, AbstractAlgorithm abstractAlgorithm) {
        this.abstractAlgorithm = abstractAlgorithm;
        this.dijkstraviController = controller;
    }

    @Override
    public void run() {
        double progress;
        while ((progress = abstractAlgorithm.getProgress()*100) <= 1.0) {
            if (progress >= 0.9) {
                dijkstraviController.getPbAlgorithms().setProgress(1.0);
                return;
            }

            dijkstraviController.getPbAlgorithms().setProgress(progress);
        }
    }
}
