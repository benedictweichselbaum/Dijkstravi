package application.graphNavigation.algorithms;

import application.DijkstraviController;
import application.algorithmProgess.ProgressBarAlgorithmUpdater;
import application.graphNavigation.algorithms.exceptions.NoWayFoundException;
import application.graphNavigation.graph.Graph;
import application.graphNavigation.graph.Node;
import application.graphNavigation.runningTime.RuntimeCalculation;
import application.routeDrawing.MapRouteDrawer;
import javafx.scene.image.Image;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class AlgorithmThread extends Thread {

    private AbstractAlgorithm abstractAlgorithm;
    private Stack<Integer> way;
    private Graph graph;
    private int startNode;
    private int targetNode;
    private ProgressBarAlgorithmUpdater progressBarAlgorithmUpdater;
    private DijkstraviController controller;
    private String fromStr;
    private String toStr;
    private String algorithm;
    private int maxSpeed;
    private boolean fastestPath;
    private RuntimeCalculation rc;

    public AlgorithmThread(AbstractAlgorithm abstractAlgorithm, Graph graph, int startNode, int targetNode,
                           ProgressBarAlgorithmUpdater progressBarAlgorithmUpdater, DijkstraviController controller,
                           String fromStr, String toStr, String algorithm, int maxSpeed, boolean fastestPath) {
        this.abstractAlgorithm = abstractAlgorithm;
        this.way = null;
        this.graph = graph;
        this.startNode = startNode;
        this.targetNode = targetNode;
        this.progressBarAlgorithmUpdater = progressBarAlgorithmUpdater;
        this.controller = controller;
        this.fromStr = fromStr;
        this.toStr = toStr;
        this.algorithm = algorithm;
        this.maxSpeed = maxSpeed;
        this.fastestPath = fastestPath;
    }

    public AlgorithmThread(){}

    @Override
    public void run() {
        progressBarAlgorithmUpdater.start();
        try {
            rc = new RuntimeCalculation();
            way = abstractAlgorithm.initCalculateShortestWay(graph, startNode, targetNode, maxSpeed, fastestPath);
            rc.stopCalculation();

            connectionFound(way);

            Stack<Integer> wayForPicture = (Stack<Integer>) way.clone();

            File imageFile = new File("src/application/autobahnnetz_DE.png");
            Image autobahnNetworkImage = new Image(imageFile.toURI().toString());
            List<Node> listOfNodesForPicture = new ArrayList<>();

            setImage(wayForPicture, autobahnNetworkImage, listOfNodesForPicture);

            String orders = abstractAlgorithm.directions(graph, way, controller);

            controller.enableFields();

            initialOrder(orders);
            this.abstractAlgorithm.progress = 1.0;

        } catch (Exception e) {
            orderWayNotFound();
        }
        progressBarAlgorithmUpdater.interrupt();
        this.interrupt();
    }

    public boolean connectionFound(Stack<Integer> way) throws NoWayFoundException {
        if (way == null) {
            throw new NoWayFoundException();
        } else return true;
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
                .setImage(MapRouteDrawer.drawWayWithListOfNodes(autobahnNetworkImage, listOfNodesForPicture));
    }

    private void initialOrder(String orders) {
        String mode;
        if (fastestPath) {
            mode = "schnellsten";
        } else {
            mode = "kürzesten";
        }

        String output = String.format("Routenbeschreibung des %s Weges von %s nach %s mit dem %s-Algorithmus (Laufzeit des Algorithmus: %s)"
                + orders, mode, fromStr, toStr, algorithm, rc.getResult());
        controller.getTxtAreaRoute().setText(output);
    }

    private void orderWayNotFound() {
        controller.getTxtAreaRoute().setText("Es konnte keine Route von " + fromStr + " nach " + toStr + " gefunden werden.");
        this.abstractAlgorithm.progress = 1.0;
        controller.enableFields();
        controller.deleteDistanceAndDuration();
        File imageFile = new File("src/application/autobahnnetz_DE.png");
        Image autobahnNetworkImage = new Image(imageFile.toURI().toString());
        controller.getImgViewAutobahn()
                .setImage(autobahnNetworkImage);
    }

    public Stack<Integer> getWay() {
        return way;
    }
}