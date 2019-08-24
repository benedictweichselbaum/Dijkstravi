package application.globalLogic;

import application.DijkstraviController;
import application.algorithmProgess.ProgessAlgBarUpdater;
import application.graphNavigation.algorithms.*;
import application.graphNavigation.graph.Connection;
import application.graphNavigation.graph.Graph;
import application.graphNavigation.graph.Node;
import application.imageManipulation.MapManipulator;
import application.menuBarDialogs.optionDialog.OptionWindow;
import application.graphCreatorWithDialog.GraphCreater;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.util.*;
import java.util.List;

/**
 * This class manages all parts of the internal logic of the program.
 * The controller class uses the global logic to trigger the calculations.
 */
public class GlobalLogic {

    private Graph graph;
    private HashMap<String, ArrayList<Integer>> listOfExits;
    private HashMap<Integer, String> listOfExitsById;
    private ComboBox from;
    private ComboBox to;
    private ArrayList<Integer> nodesWithHelperConnection;
    private int idFrom, idTo;
    private OptionWindow optionWindow;
    private ImageView imageView;
    private DijkstraviController dijkstraviController;

    public GlobalLogic(ComboBox from, ComboBox to, ImageView iv, DijkstraviController dijkstraviController) {
        this.from = from;
        this.to = to;
        this.optionWindow = new OptionWindow();
        this.imageView = iv;
        this.dijkstraviController = dijkstraviController;
        idFrom = idTo = -1;
        listOfExitsById = new HashMap<>();
        this.createGraph();
        fillListOfExits();
        initComboBoxes();
    }

    @SuppressWarnings("unchecked")
    private void initComboBoxes() {
        ObservableList<String> observableList = FXCollections.observableArrayList();

        listOfExits.forEach((n, a) -> observableList.add(n));
        this.from.setItems(observableList.sorted());
        this.to.setItems(observableList.sorted());
    }

    @SuppressWarnings("all")
    private void fillListOfExits() {
        listOfExits = new HashMap<>();

        listOfExitsById.forEach((l, b) -> addExitToListOfExits(l, b));
    }

    private void addExitToListOfExits(int id, String name) {
        ArrayList<Integer> list = listOfExits.get(name);
        if (list == null)
            list = new ArrayList<>();
        list.add(id);
        listOfExits.put(name, list);
    }

    @SuppressWarnings("all")
    private void createGraph() {
        GraphCreater graphCreater = new GraphCreater(listOfExitsById);

        graphCreater.start();

        synchronized (graphCreater) {
            try {
                graphCreater.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.graph = graphCreater.getGraph();
    }

    public void calculateWay(int alg) {
        dijkstraviController.getPbAlgorithms().setProgress(0);
        if (idTo > 1) deleteHelpStructure();

        String fromStr = from.getEditor().getText();
        String toStr = to.getEditor().getText();

        ArrayList<Integer> fromId = listOfExits.get(fromStr);
        ArrayList<Integer> toId = listOfExits.get(toStr);

        createHelpStructure(fromId, toId);

        String fromIdString = "";
        String toIdString = "";
        for (Integer fi : fromId)
            fromIdString += fi.toString() + ",";
        for (Integer ti : toId)
            toIdString += ti.toString() + ",";
        fromIdString = fromIdString.substring(0, fromIdString.length() - 1);
        toIdString = toIdString.substring(0, toIdString.length() - 1);

        if (graph.getNodeById(idFrom).getName().equals("HelperNode")) {
            fromIdString += " Hilfsknoten " + idFrom;
        }
        if (graph.getNodeById(idTo).getName().equals("HelperNode")) {
            toIdString += " Hilfsknoten " + idTo;
        }

        String algorithmus = "";
        NavigationService navigationService = null;
        switch (alg) {
            case 0:
                algorithmus = "Dijkstra";
                navigationService = new Dijkstra();
                break;
            case 1:
                algorithmus = "A*";
                navigationService = new AStar();
                break;
            case 2:
                algorithmus = "Bellman-Ford";
                navigationService = new bff();
                //navigationService = new BellmanFord();
                break;
            case 3:
                algorithmus = "Min-Plus-Matrixmultiplikations";
                //navigationService = new ;
                break;
        }

        ProgessAlgBarUpdater progessAlgBarUpdater = new ProgessAlgBarUpdater(dijkstraviController, navigationService);
        AlgorithmThread algorithmThread = new AlgorithmThread(navigationService, graph, idFrom, idTo,
                progessAlgBarUpdater, dijkstraviController, fromStr, toStr, algorithmus);
        algorithmThread.start();
    }
    public OptionWindow getOptionWindow() {
        return optionWindow;
    }

    private void createHelpStructure(ArrayList<Integer> fromId, ArrayList<Integer> toId) {
        idFrom = createHelperNode(fromId);
        idTo = createHelperNode(toId);
        nodesWithHelperConnection = toId;
        for (int id : fromId) {
            if (id != idFrom)
                addHelperConnection(idFrom, id);
        }
        for (int id : toId) {
            if (id != idTo)
                addHelperConnection(id, idTo);
        }
    }

    private int createHelperNode(ArrayList<Integer> Ids) {
        if (Ids.size() == 1) {
            return Ids.get(0);
        } else {
            Node helperNode = addHelperNode(Ids);
            graph.addNodesSorted(helperNode);
            return helperNode.getId();
        }
    }

    private Node addHelperNode(ArrayList<Integer> listOfNodes) {
        int lat = 0;
        int lon = 0;
        for (int nodeId : listOfNodes) {
            Node nd = graph.getNodeById(nodeId);
            lat += nd.getLatitude();
            lon += nd.getLongitude();
        }
        lat = lat / listOfNodes.size();
        lon = lon / listOfNodes.size();

        return new Node(graph.getAmountOfNodes(), true, lat, lon, "HelperNode");
    }

    private void addHelperConnection(int fromId, int toId) {
        graph.addEdge(fromId, toId, 1, -1, "HelperWay", "Imaginary");
    }

    private void deleteHelpStructure() {
        // NIX OPTIMIEREN, VERURSACHT NUR FEHLER (ab Java 13 kanns gerne ausprobiert werden)
        ArrayList<Integer> ndl = new ArrayList<>();
        ArrayList<Connection> cnl = new ArrayList<>();
        if (graph.getNodeById(idTo).getName().equals("HelperNode")) {
            for (int nd : nodesWithHelperConnection) {
                for (Connection cn : graph.getAllConnectionsOfNode(nd)) {
                    if (cn.getAim() == idTo) {
                        //graph.deleteConnection(nd, cn);
                        ndl.add(nd);
                        cnl.add(cn);
                    }
                }
            }
            for (int i = 0; i < ndl.size(); i++)
                graph.deleteConnection(ndl.get(i), cnl.get(i));

            graph.deleteLastNodeWithOutgoingConnections();
        }
        if (graph.getNodeById(idFrom).getName().equals("HelperNode"))
            graph.deleteLastNodeWithOutgoingConnections();
        idTo = 0;
    }
}
