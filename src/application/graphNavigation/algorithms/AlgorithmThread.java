package application.graphNavigation.algorithms;

import application.DijkstraviController;
import application.algorithmProgess.ProgressAleBarUpdater;
import application.graphNavigation.graph.Graph;
import application.graphNavigation.graph.Node;
import application.imageManipulation.MapManipulator;
import javafx.scene.image.Image;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class AlgorithmThread extends Thread {

    private NavigationService navigationService;
    private Stack<Integer> way;
    private Graph graph;
    private int startNode;
    private int targetNode;
    private ProgressAleBarUpdater progressAleBarUpdater;
    private DijkstraviController controller;
    private String fromStr;
    private String toStr;
    private String algorithm;
    private int maxSpeed;

    public AlgorithmThread(NavigationService navigationService, Graph graph, int startNode, int targetNode,
                           ProgressAleBarUpdater progressAleBarUpdater, DijkstraviController controller,
                           String fromStr, String toStr, String algorithm, int maxSpeed) {
        this.navigationService = navigationService;
        this.way = null;
        this.graph = graph;
        this.startNode = startNode;
        this.targetNode = targetNode;
        this.progressAleBarUpdater = progressAleBarUpdater;
        this.controller = controller;
        this.fromStr = fromStr;
        this.toStr = toStr;
        this.algorithm = algorithm;
        this.maxSpeed = maxSpeed;
    }

    @Override
    public void run() {
        progressAleBarUpdater.start();
        try {
            way = navigationService.calculateShortestWay(graph, startNode, targetNode);

            if (way == null) {
                controller.getTxtAreaRoute()
                        .setText("Bitte warten! Ihr gewünschter Zielort ist leider noch " +
                                "nicht von Ihrem Startpunkt aus über Autobahnen zu erreichen.");
            }

            Stack<Integer> wayForPicture = (Stack<Integer>) way.clone();
            String orders = navigationService.directions(graph, way, maxSpeed);

            File imageFile = new File("src/application/autobahnnetz_DE.png");
            Image autobahnNetworkImage = new Image(imageFile.toURI().toString());
            List<Node> listOfNodesForPicture = new ArrayList<>();

            setImage(wayForPicture, autobahnNetworkImage, listOfNodesForPicture);

            controller.enableFields();

            controller.getTxtAreaRoute().setText("Routenbeschreibung von " + fromStr + " nach " + toStr
                    + " mit dem " + algorithm + "-Algorithmus:" + orders);
            this.navigationService.progress = 1.0;
        } catch (Exception e) {
            controller.getTxtAreaRoute().setText("Zum Starten der Wegberechnung bitte erst Start und Ziel auswählen."
                    + e.toString() + "\n" + e.getLocalizedMessage() + "\n" + e.getMessage());
            this.navigationService.progress = 1.0;
            controller.enableFields();
        }
        progressAleBarUpdater.interrupt();
        this.interrupt();
    }

    private void setImage(Stack<Integer> wayForPicture, Image autobahnNetworkImage, List<Node> listOfNodesForPicture) {
        int counterToSkipFirstAndLastNode = 0;
        for (Integer w : wayForPicture) {
            if (counterToSkipFirstAndLastNode == 0 && graph.getNodeById(w).getName().equals("HelperWay")) {
                counterToSkipFirstAndLastNode++;
            } else if (counterToSkipFirstAndLastNode == wayForPicture.size() - 1
                    && graph.getNodeById(w).getName().equals("HelperWay")) {
                break;
            } else {
                counterToSkipFirstAndLastNode++;
                listOfNodesForPicture.add(graph.getNodeById(w));
            }
        }
        controller.getImgViewAutobahn()
                .setImage(MapManipulator.drawWayWithListOfNodes(autobahnNetworkImage, listOfNodesForPicture));
    }

    public Stack<Integer> getWay() {
        return way;
    }
}
