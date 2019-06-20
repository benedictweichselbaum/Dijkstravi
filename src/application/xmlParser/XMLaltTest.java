package application.xmlParser;
import java.io.*;
import java.util.*;

import application.graphNavigation.Graph;
import application.graphNavigation.Graph2;
import application.graphNavigation.Node;
import application.graphNavigation.Node2;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class XMLaltTest {

    Graph gr;
    Graph2 gr2;
    HashMap<Long, Double> hmaplon = new HashMap<>();
    HashMap<Long, Double> hmaplat = new HashMap<>();
    List<OneWay> OneWayList = new ArrayList<>();


    public void init(){
        try {
            File inputFile = new File("german_autobahn.osm");
            SAXBuilder saxBuilder = new SAXBuilder();
            Document document = saxBuilder.build(inputFile);
            Element classElement = document.getRootElement();

            List<Element> wayList = classElement.getChildren();

            saveXML(wayList);
            System.out.println("XML ausgelesen. Beginne nun Auswertung der Daten.");
            wayInsertion(OneWayList);

        } catch(JDOMException e) {
            e.printStackTrace();
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }
    private Node createNode(long node)
    {
      //  Node nd = new Node(node, false, knoten[node][1], knoten[node][0]);
        Node nd = new Node(node, false, hmaplon.get(node), hmaplat.get(node));
        return nd;
    }

    private Node2 createNode2(int newNodeID, long node){
        Node2 nd2 = new Node2(newNodeID, false, hmaplon.get(node), hmaplat.get(node));
        return nd2;
    }

    private void saveXML(List<Element> list){
        for (Element el : list) {
            if(el.getName().equals("way")) {
                try {
                    long wayid = el.getAttribute("id").getLongValue();
                    List<Element> ndtagList = el.getChildren();
                    ArrayList<Attribute> nodes = new ArrayList<Attribute>();
                    boolean motorway_link = false; //true: node is motorway_link; false: node is motorway
                    boolean ishighway = false;

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
                            }
                        }
                    }

                    if (ishighway) {
                        OneWay ow = new OneWay(wayid ,nodes, motorway_link);
                        OneWayList.add(ow);
                    }
                }catch (Exception e){
                    System.out.println("Error! Way id conversion failed: " + el.getAttribute("id").getValue());
                }
            }else if(el.getName().equals("node")){
                try{
                    long nodeid =  el.getAttribute("id").getLongValue();

                    hmaplat.put(nodeid, el.getAttribute("lat").getDoubleValue());
                    hmaplon.put(nodeid, el.getAttribute("lon").getDoubleValue());
                    //>>Graph<<.addNode(nd);
                }catch (Exception e) {
                    //TODO (Occurs when conversion to int/double failed)
                    System.out.println("Error!" + el.getAttribute("id").getValue());
                }
            }
        }
    }

    private void wayInsertion(List<OneWay> wayList){
        int z = 0;
        System.out.println("Anzahl der Wege: " + wayList.size() + " Ermittle jetzt die Anzahl der relevanten Knoten.");

        ArrayList<Long> allNeededNodes = createListOfAllNeededNodes(wayList);
       System.out.println("Es werden " + allNeededNodes.size() + " Knoten benötigt. Erstelle jetzt den Graphen.");
        int numberOfMotorway_links = 20000;//wayList.size(); //Natürlich falsch... TODO
        //gr = new Graph(allNeededNodes.size(), numberOfMotorway_links);
        gr2 = new Graph2();
        System.out.println("Graph erstellt. Füge jetzt die Knoten ein.");
        /*
        for (Long nodeID: allNeededNodes){
            gr.addNode(createNode(nodeID));
        }
         */
        HashMap<Long, Integer> newID = new HashMap<>();

        for(int i = 0; i < allNeededNodes.size(); i++){
            gr2.addNodesSorted(createNode2(i,allNeededNodes.get(i)));
            newID.put(allNeededNodes.get(i),i);
        }

        System.out.println("Alle Knoten eingefügt. Füge jetzt die Wege ein.");

        for(OneWay ow : wayList){
            //TODO: Weg einfügen
            /*
            createNode(ow.getFirst());
            createNode(ow.getLast());
            */

            ArrayList<Attribute> nodeIDList = ow.getListofIDsOfNodes();
            List<Node> nodeList = new ArrayList<>();
            try {
                for (Attribute a: nodeIDList) {
                    nodeList.add(createNode(a.getLongValue()));
                }
            }catch(Exception e){
                System.out.println("Conversion error!");
            }

            Double exactLength = DistanceCalculator.calculateDistanceFromListOfNodes(nodeList);
            int length = exactLength.intValue();

            gr2.addEdge(newID.get(ow.getFirst()), newID.get(ow.getLast()), length);

            z++;
            if(z % 1000 == 0 || z < 20)
            System.out.println(z + ". Weg mit Länge " + length + " Meter erstellt.");

        }
        System.out.println("Der Graph ist FERTIG!'");
    }

    public ArrayList<Long> createListOfAllNeededNodes(List<OneWay> wayList){

        ArrayList<Long> nodeIDs = new ArrayList<>();
        for(OneWay ow : wayList) {
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
}
