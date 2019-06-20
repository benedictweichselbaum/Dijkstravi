package application.xmlParser;
import java.io.*;
import java.util.*;

import application.graphNavigation.Graph;
import application.graphNavigation.Node;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class XMLaltTest {

    Graph gr;
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
        System.out.println("Länge: " + wayList.size());

        ArrayList<Long> allNeededNodes = createListOfAllNeededNodes(wayList);
       System.out.println("Es werden " + allNeededNodes.size() + " Knoten benötigt.");
        int numberOfMotorway_links = wayList.size(); //Natürlich falsch... TODO
        gr = new Graph(allNeededNodes.size(), numberOfMotorway_links);

        for (Long nodeID: allNeededNodes){
            gr.addNode(createNode(nodeID));
        }

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

            gr.addEdge(gr.getMatrixNodeNumberById(ow.getFirst()), gr.getMatrixNodeNumberById(ow.getLast()), length);

            z++;
            if(z % 1000 == 0 || z < 20)
            System.out.println(z + ". Weg mit Länge " + length + " Meter erstellt.");

        }
    }

    public ArrayList<Long> createListOfAllNeededNodes(List<OneWay> wayList){
        ArrayList<Long> nodeIDs = new ArrayList<>();

        for(OneWay ow : wayList) {
            Long first = ow.getFirst();
            Long last = ow.getLast();
            boolean firstnew = true;
            boolean lastnew = true;
            for(int i = 0; i < nodeIDs.size() && (firstnew || lastnew); i++){
                if(nodeIDs.get(i) == first){
                    firstnew = false;
                }
                if(nodeIDs.get(i) == last){
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
