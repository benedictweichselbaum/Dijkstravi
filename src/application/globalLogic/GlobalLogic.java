package application.globalLogic;

import application.graphNavigation.*;
import application.imageManipulation.MapManipulator;
import application.menuBarDialogs.optionDialog.OptionWindow;
import application.starterProgressDialog.GraphCreater;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.*;
import java.util.List;

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

    public GlobalLogic (ComboBox from, ComboBox to, ImageView iv) {
        this.from = from;
        this.to = to;
        this.optionWindow = new OptionWindow();
        this.imageView = iv;
        idFrom = idTo = -1;
        listOfExitsById = new HashMap<>();
        this.showCreatingDialogAndCreateGraph();
        fillListOfExits();
        initComboBoxes();
    }

    @SuppressWarnings("unchecked")
    private void initComboBoxes () {
        ObservableList<String> observableList =  FXCollections.observableArrayList();

        listOfExits.forEach((n, a) -> observableList.add(n));
        this.from.setItems(observableList.sorted());
        this.to.setItems(observableList.sorted());
    }

    @SuppressWarnings("all")
    private void fillListOfExits(){
        listOfExits = new HashMap<>();

        listOfExitsById.forEach((l,b) -> addExitToListOfExits(l,b));
    }

    private void addExitToListOfExits(int id, String name){
        ArrayList<Integer> list = listOfExits.get(name);
        if(list == null)
            list = new ArrayList<>();
        list.add(id);
        listOfExits.put(name, list);
    }

    @SuppressWarnings("all")
    private void showCreatingDialogAndCreateGraph() {
        JFrame jFrame = new JFrame("Graph berechnen");
        Container pane = jFrame.getContentPane();
        JProgressBar jProgressBar = new JProgressBar(0, 100);
        jProgressBar.setValue(0);
        JLabel jLabel = new JLabel("        Der Graph wird berechnet...");
        jFrame.setLayout(new GridLayout(1, 1));
        jFrame.setLocation(800, 400);

        pane.add(jLabel);
        //pane.add(jProgressBar);

        jFrame.setSize(350, 100);
        jFrame.setResizable(false);
        jFrame.setVisible(true);

        GraphCreater graphCreater = new GraphCreater(jProgressBar, jLabel, listOfExitsById);

        graphCreater.start();

        synchronized (graphCreater) {
            try {
                graphCreater.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        this.graph = graphCreater.getGraph();
        jFrame.dispose();
    }

    public String calculateWay(int alg){
          try {
            if(idTo > 1)
            deleteHelpStructure();

            String fromStr = from.getEditor().getCharacters().toString();
            String toStr = to.getEditor().getCharacters().toString();

            ArrayList<Integer> fromId = listOfExits.get(fromStr);
            ArrayList<Integer> toId = listOfExits.get(toStr);

            createHelpStructure(fromId, toId);

            System.out.println(graph.getAllConnectionsOfNode(idFrom) + "<- da");
            System.out.println(graph.getAllConnectionsOfNode(idTo) + "<- daTO");
            System.out.println(nodesWithHelperConnection.toString() + "<- HelperConn");

            String fromIdString = "";
            String toIdString = "";
            for (Integer fi : fromId)
                fromIdString += fi.toString() + ",";
            for (Integer ti : toId)
                toIdString += ti.toString() + ",";
            fromIdString = fromIdString.substring(0, fromIdString.length() - 1);
            toIdString = toIdString.substring(0, toIdString.length() - 1);

            if(graph.getNodeById(idFrom).getName().equals("HelperNode")){
                fromIdString += " Hilfsknoten " + idFrom;
            }
            if(graph.getNodeById(idTo).getName().equals("HelperNode")){
                toIdString += " Hilfsknoten " + idTo;
            }

            String algorithmus = "";
            String orders = "";
            Stack<Integer> way;
            Stack<Integer> wayForPicture = new Stack<>();
            switch (alg){
                case 0: algorithmus = "Dijkstra";
                    System.out.println("Dijkstrvigator:");
                    NavigationService dijkstrvigator = new Dijkstra();
                    way = dijkstrvigator.calculateShortestWay(graph, idFrom, idTo);
                    wayForPicture = (Stack<Integer>) way.clone();
                    orders = dijkstrvigator.directions(graph, way);
                    break;
                case 1: algorithmus = "A*";
                    System.out.println("AStarigator:");
                    NavigationService aStarigator = new AStar();
                    way = aStarigator.calculateShortestWay(graph, idFrom, idTo);
                    wayForPicture = (Stack<Integer>) way.clone();
                    orders = aStarigator.directions(graph,way);
                    break;
                case 2: algorithmus = "Bellman-Ford";
                    break;
                case 3: algorithmus = "Min-Plus-Matrixmultiplikations";
                    break;
            }

              File imageFile = new File("src/autobahnnetz_DE.png");
              javafx.scene.image.Image autobahnNetworkImage = new Image(imageFile.toURI().toString());
              List<Node> listOfNodesForPicture = new ArrayList<>();

              int counterToSkipFirstAndLastNode = 0;
              for (Integer w : wayForPicture) {
                  if (counterToSkipFirstAndLastNode == 0) {
                      counterToSkipFirstAndLastNode++;
                  } else if (counterToSkipFirstAndLastNode == wayForPicture.size()-1) {
                      break;
                  } else {
                      counterToSkipFirstAndLastNode++;
                      listOfNodesForPicture.add(graph.getNodeById(w));
                  }
              }
              imageView.setImage(MapManipulator.drawWayWithListOfNodes(autobahnNetworkImage, listOfNodesForPicture));

            //TODO: Algorithmus auch starten
            return "Routenbeschreibung von " + fromStr + " nach " + toStr + " mit dem " + algorithmus + "-Algorithmus:" + orders;
            //return "Wegberechnung von " + fromStr + " (" + fromIdString + ") nach " + toStr + " (" + toIdString + ") mit dem " + algorithmus + "-Algorithmus." + orders;
        }catch (Exception e){
            return "Zum Starten der Wegberechnung bitte erst Start und Ziel auswählen.";
        }

    }

    public OptionWindow getOptionWindow() {
        return optionWindow;
    }

    private void createHelpStructure(ArrayList<Integer> fromId, ArrayList<Integer> toId){
        idFrom = createHelperNode(fromId);
        idTo = createHelperNode(toId);
        nodesWithHelperConnection = toId;
        for(int id : fromId){
            if(id != idFrom)
                addHelperConnection(idFrom, id);
        }
        for(int id : toId){
            if(id != idTo)
                addHelperConnection(id, idTo);
        }
    }

    private int createHelperNode(ArrayList<Integer> Ids){
        if(Ids.size() == 1){
            return Ids.get(0);
        }else{
            Node helperNode = addHelperNode(Ids);
            graph.addNodesSorted(helperNode);
            return helperNode.getId();
        }
    }
    private Node addHelperNode(ArrayList<Integer> listOfNodes){
        int lat = 0;
        int lon = 0;
        for(int nodeId : listOfNodes){
            Node nd = graph.getNodeById(nodeId);
            lat += nd.getLatitude();
            lon+= nd.getLongitude();
        }
        lat = lat / listOfNodes.size();
        lon = lon / listOfNodes.size();

        return new Node(graph.getAmountOfNodes(),true, lat, lon, "HelperNode");
    }

    private void addHelperConnection(int fromId, int toId){
        graph.addEdge(fromId,toId,1, -1, "HelperWay", "Imaginary");
    }

    private void deleteHelpStructure(){
        // NIX OPTIMIEREN, VERURSACHT NUR FEHLER (ab Java 13 kanns gerne ausprobiert werden)
        ArrayList<Integer> ndl = new ArrayList<>();
        ArrayList<Connection> cnl = new ArrayList<>();
        if(graph.getNodeById(idTo).getName().equals("HelperNode")){
            for(int nd : nodesWithHelperConnection){
                for(Connection cn : graph.getAllConnectionsOfNode(nd)){
                   if(cn.getAim() == idTo) {
                       //graph.deleteConnection(nd, cn);
                       ndl.add(nd);
                       cnl.add(cn);
                   }
                }
            }
            for (int i = 0; i < ndl.size(); i++) {
                graph.deleteConnection(ndl.get(i), cnl.get(i));
            }
            graph.deleteLastNodeWithOutgoingConnections();
        }
        if(graph.getNodeById(idFrom).getName().equals("HelperNode")){
            graph.deleteLastNodeWithOutgoingConnections();
        }
    }
}
