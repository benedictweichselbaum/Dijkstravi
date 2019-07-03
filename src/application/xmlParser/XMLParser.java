package application.xmlParser;
import java.io.*;
import java.util.*;

import application.graphNavigation.Connection;
import application.graphNavigation.Graph;
import application.graphNavigation.LatLonNode;
import application.graphNavigation.Node;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class XMLParser {

    private Graph gr;
    private HashMap<Long, Double> hmaplon = new HashMap<>();
    private HashMap<Long, Double> hmaplat = new HashMap<>();
    private HashMap<Long, String> hmapname = new HashMap<>();
    private List<Way> wayList = new ArrayList<>();
    private HashMap<Integer, String> listOfExits;

    public XMLParser (HashMap<Integer, String> hashMap) {
        this.listOfExits = hashMap;
    }

    private double progress = 0;

    public Graph init(){
        try {
            System.out.println("Lade die XML.");
            File inputFile = new File("german_autobahn.osm");
            SAXBuilder saxBuilder = new SAXBuilder();
            Document document = saxBuilder.build(inputFile);
            Element classElement = document.getRootElement();

            List<Element> wayList = classElement.getChildren();

            System.out.println("Starte mit Auslesen der XML.");
            saveXML(wayList);
            System.out.println("XML ausgelesen. Beginne nun Auswertung der Daten.");
            wayInsertion(this.wayList);

        } catch(JDOMException e) {
            System.out.println("A JDOMException occured!");
            e.printStackTrace();
        } catch(IOException ioe) {
            System.out.println("An IOException occured!");
            ioe.printStackTrace();
        }
        return gr;
    }
    private LatLonNode createNode(long node)
    {
        return new LatLonNode(hmaplon.get(node), hmaplat.get(node));
    }

    private Node createNode2(int newNodeID, long node){
        String name = hmapname.get(node);
        if(name != null)
            listOfExits.put(newNodeID, name);
        return new Node(newNodeID, false, hmaplat.get(node), hmaplon.get(node), name);
    }

    private void saveXML(List<Element> list){
        double singleProgessUnit = 0.25 / list.size();
        for (Element el : list) {
            progress = progress + singleProgessUnit;
            if(el.getName().equals("way")) {
                try {
                    long wayid = el.getAttribute("id").getLongValue();
                    List<Element> ndtagList = el.getChildren();
                    ArrayList<Attribute> nodes = new ArrayList<>();
                    boolean motorway_link = false; //true: node is motorway_link; false: node is motorway
                    boolean ishighway = false;
                    String name = "";
                    String destination = "";
                    int maxspeed = -2;

                    for (Element ndtag : ndtagList) {
                        if (ndtag.getName().equals("nd")) {
                            Attribute nd = ndtag.getAttribute("ref");
                            nodes.add(nd);
                        } else if (ndtag.getName().equals("tag")) {
                            Attribute k = ndtag.getAttribute("k");
                            Attribute v = ndtag.getAttribute("v");
                            switch (k.getValue()) {
                                case "highway":
                                    ishighway = true;
                                    if (v.getValue().equals("motorway_link"))
                                        motorway_link = true;
                                    break;
                                case "ref":
                                    name = v.getValue();
                                    break;
                                case "destination":
                                    destination = v.getValue();
                                    break;
                                case "maxspeed":
                                    if (v.getValue().equals("none"))
                                        maxspeed = -1;
                                    else if (v.getValue().equals("signals") || v.getValue().equals("variable"))
                                        maxspeed = -3;
                                    else {
                                        try {
                                            maxspeed = v.getIntValue();
                                        } catch (Exception e) {
                                            maxspeed = -4;
                                            //System.out.println("Error! Maxspeed: " + v.getValue());
                                        }
                                    }
                                    break;
                            }

                        }
                    }

                    if (ishighway) {
                        Way ow = new Way(wayid ,nodes, motorway_link, maxspeed, name, destination);
                        wayList.add(ow);
                    }
                }catch (Exception e){
                    System.out.println("Error! Way id conversion failed: " + el.getAttribute("id").getValue());
                }
            }else if(el.getName().equals("node")){
                try{
                    long nodeid =  el.getAttribute("id").getLongValue();

                    hmaplat.put(nodeid, el.getAttribute("lat").getDoubleValue());
                    hmaplon.put(nodeid, el.getAttribute("lon").getDoubleValue());

                    List<Element> tagList = el.getChildren();

                    for (Element tag : tagList) {
                        if (tag.getAttribute("k").getValue().equals("name")) {
                            hmapname.put(nodeid, tag.getAttribute("v").getValue());
                        }
                    }
                }catch (Exception e) {
                    //TODO (Occurs when conversion to int/double failed)
                    System.out.println("Error!" + el.getAttribute("id").getValue());
                }


            }
        }
    }

    private void wayInsertion(List<Way> wayList){
        //int z = 0;
        System.out.println("Anzahl der Wege: " + wayList.size() + " Ermittle jetzt die Anzahl der relevanten Knoten.");

        //ArrayList<Long> allNeededNodes = createListOfAllNeededNodes(wayList);
        ArrayList<Long> allNeededNodes = createListOfAllNeededNodesSpeed(wayList);
       System.out.println("Es werden " + allNeededNodes.size() + " Knoten benötigt. Erstelle jetzt den Graphen.");

        gr = new Graph();
        System.out.println("Graph erstellt. Füge jetzt die Knoten ein.");

        HashMap<Long, Integer> newID = new HashMap<>();
        double singleProgessUnit = 0.25 / allNeededNodes.size();
        for(int i = 0; i < allNeededNodes.size(); i++){
            progress = progress + singleProgessUnit;
            gr.addNodesSorted(createNode2(i,allNeededNodes.get(i)));
            newID.put(allNeededNodes.get(i),i);
        }

        System.out.println("Alle Knoten eingefügt. Füge jetzt die Wege ein.");

        singleProgessUnit = 0.25 / wayList.size();
        for(Way ow : wayList){
            progress = progress + singleProgessUnit;
            ArrayList<Attribute> nodeIDList = ow.getListOfIDsOfNodes();
            List<LatLonNode> nodeList = new ArrayList<>();
            try {
                for (Attribute a: nodeIDList) {
                    nodeList.add(createNode(a.getLongValue()));
                }
            }catch(Exception e){
                System.out.println("Conversion error!");
            }

            @SuppressWarnings("all")
            Double exactLength = DistanceCalculator.calculateDistanceFromListOfNodes(nodeList);
            int length = exactLength.intValue();

            gr.addEdge(newID.get(ow.getFirst()), newID.get(ow.getLast()),
                                                            length, ow.getmaxspeed(),
                                                            ow.getName(),
                                                            ow.getDestinaton());
/*
            z++;
            if(z % 1000 == 0 || z < 20)
            System.out.println(z + ". Weg (" + ow.name + ") + mit Länge " + length + " Meter, Maximalgeschwindigkeit " + ow.maxspeed + " km/h in Richtung " + ow.destinaton + " erstellt.");
*/
        }
        System.out.println("Der Graph ist FERTIG!");

        test();
    }

    public void test(){
        gr.startAStarigator(89261, 70158);
        //gr.startDijkstrvigator(89261, 70158);
        //System.out.println(gr.getConnectionBetween2Points(0,4));
        /*Connection con = gr.getConnectionBetween2Points(0, 1);
        con.getAllInformationsAsString();*/
    }

    private ArrayList<Long> createListOfAllNeededNodesSpeed(List<Way> wayList){
        HashMap<Long,Boolean> nodeIDsMap = new HashMap<>();
        double singleProgressUnit = 0.25 / wayList.size();
        for(Way ow : wayList) {
            progress = progress + singleProgressUnit;
            nodeIDsMap.put(ow.getFirst(), true);
            nodeIDsMap.put(ow.getLast(), true);
        }
        ArrayList<Long> nodeIDs = new ArrayList<>();
        nodeIDsMap.forEach((l,b) -> nodeIDs.add(l));
        return  nodeIDs;
    }

    public double getProgress() {
        return progress;
    }
}
