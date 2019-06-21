package application.xmlParser;
import java.io.*;
import java.util.*;

import application.graphNavigation.Graph;
import application.graphNavigation.LatLonNode;
import application.graphNavigation.Node;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class XMLParser {

    Graph gr;
    HashMap<Long, Double> hmaplon = new HashMap<>();
    HashMap<Long, Double> hmaplat = new HashMap<>();
    List<Way> wayList = new ArrayList<>();

    private double progress = 0;

    public void init(){
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
            e.printStackTrace();
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }
    private LatLonNode createNode(long node)
    {
        LatLonNode nd = new LatLonNode(hmaplon.get(node), hmaplat.get(node));
        return nd;
    }

    private Node createNode2(int newNodeID, long node){
        Node nd2 = new Node(newNodeID, false, hmaplon.get(node), hmaplat.get(node));
        return nd2;
    }

    private void saveXML(List<Element> list){
        double singleProgessUnit = 0.25 / list.size();
        for (Element el : list) {
            progress = progress + singleProgessUnit;
            if(el.getName().equals("way")) {
                try {
                    long wayid = el.getAttribute("id").getLongValue();
                    List<Element> ndtagList = el.getChildren();
                    ArrayList<Attribute> nodes = new ArrayList<Attribute>();
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
                            if (k.getValue().equals("highway")) {
                                ishighway = true;
                                if (v.getValue().equals("motorway_link"))
                                    motorway_link = true;
                            }else if(k.getValue().equals("ref"))
                                name = v.getValue();
                            else if(k.getValue().equals("destination"))
                                destination = v.getValue();
                            else if(k.getValue().equals("maxspeed")){
                                if(v.getValue().equals("none"))
                                    maxspeed = -1;
                                else if(v.getValue().equals("signals") || v.getValue().equals("variable"))
                                    maxspeed = -3;
                                else{
                                    try{
                                        maxspeed = v.getIntValue();
                                    }catch (Exception e){
                                        maxspeed = -4;
                                        //System.out.println("Error! Maxspeed: " + v.getValue());
                                    }
                                }

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
                }catch (Exception e) {
                    //TODO (Occurs when conversion to int/double failed)
                    System.out.println("Error!" + el.getAttribute("id").getValue());
                }
            }
        }
    }

    private void wayInsertion(List<Way> wayList){
        int z = 0;
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

            Double exactLength = DistanceCalculator.calculateDistanceFromListOfNodes(nodeList);
            int length = exactLength.intValue();

            gr.addEdge(newID.get(ow.getFirst()), newID.get(ow.getLast()), length, ow.maxspeed, ow.name, ow.destinaton);
/*
            z++;
            if(z % 1000 == 0 || z < 20)
            System.out.println(z + ". Weg (" + ow.name + ") + mit Länge " + length + " Meter, Maximalgeschwindigkeit " + ow.maxspeed + " km/h in Richtung " + ow.destinaton + " erstellt.");
*/
        }
        System.out.println("Der Graph ist FERTIG!");
    }

    public ArrayList<Long> createListOfAllNeededNodes(List<Way> wayList){

        ArrayList<Long> nodeIDs = new ArrayList<>();
        double singleProgressUnit = 0.25 / wayList.size();
        for(Way ow : wayList) {
            progress = progress + singleProgressUnit;
            Long first = ow.getFirst();
            Long last = ow.getLast();
            boolean firstnew = true;
            boolean lastnew = true;
            for(int i = 0; i < nodeIDs.size() && (firstnew || lastnew); i++){
                if(nodeIDs.get(i).equals(first)){
                    firstnew = false;
                }
                if(nodeIDs.get(i).equals(last)){
                    lastnew = false;
                }
            }
            if(firstnew)
                nodeIDs.add(first);
            if(lastnew)
                nodeIDs.add(last);
        }

        return nodeIDs;
    }

    public ArrayList<Long> createListOfAllNeededNodesSpeed(List<Way> wayList){
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
