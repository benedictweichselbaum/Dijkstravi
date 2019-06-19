package application.xmlParser;
import java.io.*;
import java.util.*;

import application.graphNavigation.Node;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class XMLaltTest {

    HashMap<Long, Double> hmaplon = new HashMap<>();
    HashMap<Long, Double> hmaplat = new HashMap<>();
    //double[][] knoten;
    List<OneWay> OneWayList = new ArrayList<>();


    public void init(){
        try {
            File inputFile = new File("german_autobahn.osm");
            SAXBuilder saxBuilder = new SAXBuilder();
            Document document = saxBuilder.build(inputFile);
            Element classElement = document.getRootElement();

            List<Element> wayList = classElement.getChildren();

            //int maxnode = getMaxIdOfNodes(wayList);
            //System.out.println(maxnode);
            //knoten = new double[1000000000][2];

            //System.out.println("Array mit Länge " + maxnode + " erstellt.");
            saveXML(wayList);

            wayInsertion(OneWayList);
            //insertWays(wayList);

        } catch(JDOMException e) {
            e.printStackTrace();
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public void insertWays(List<Element> wayList){
        for (Element way : wayList) {
            if(way.getName().equals("way")){
                List<Element> ndtagList = way.getChildren();
                ArrayList<Attribute> nodes = new ArrayList<Attribute>();
                boolean motorway_link = false; //true: node is motorway_link; false: node is motorway
                boolean ishighway = false;

                for (Element ndtag: ndtagList) {
                    if (ndtag.getName().equals("nd")) {
                        Attribute nd = ndtag.getAttribute("ref");
                        nodes.add(nd);
                    }else if(ndtag.getName().equals("tag")){
                        Attribute k = ndtag.getAttribute("k");
                        Attribute v = ndtag.getAttribute("v");
                        if(k.getValue().equals("highway")) {
                            ishighway = true;
                            if(v.getValue().equals("motorway_link"))
                                motorway_link = true;
                        }
                    }
                }

                if(ishighway)
                    create(nodes, wayList);
            }
        }
    }

    private int calculateLength(ArrayList<Attribute> nodes, List<Element> nodeList){
        // TODO: Nodes holen und mithilfe Längen- und Breitengraden länge berechnen
        return 2;
    }

    private Element getNode(Attribute id, List<Element> wayList){
        for (Element node : wayList) {
            if(node.getName().equals("node")) {
                if(node.getAttribute("id").equals(id))
                    return node;
            }
        }
        return null;
    }

    private void create(ArrayList<Attribute> nodes, List<Element> nodesList) //Creates way with nodes and adds them to
    {
      //  createNode(getNode(nodes.get(0), nodesList)); // first node
        //createNode(getNode(nodes.get(nodes.size() - 1), nodesList));// last node
        int length = calculateLength(nodes, nodesList);
        //TODO: Den Knoten wird noch nicht mitgegeben, ob sie ein motorway_link sind, da nicht klar ist, ob der Start- oder der Endknoten ein motorway_link ist
        createWay(nodes.get(0), nodes.get(nodes.size() - 1), length);
        System.out.println("Weg von " + nodes.get(0) + " zu " + nodes.get(nodes.size() - 1) + " mit Länge " + length + " Meter erstellt!" );
    }

    private void createNode(long node)
    {
      //  Node nd = new Node(node, false, knoten[node][1], knoten[node][0]);
        Node nd = new Node(node, false, hmaplon.get(node), hmaplat.get(node));
    }

    private void createWay(Attribute from, Attribute to, int length)
    {
        //TODO: Insert way in the Adjazenzmatrix
        try{
            //>>Graph<<.addEdge(from.getIntValue(), to.getIntValue(), length);
        }catch (Exception e) {
            //TODO (Occurs when conversion of id to int failed -> never)
        }
    }

    private void saveXML(List<Element> list){
        for (Element el : list) {
            if(el.getName().equals("way")) {
                List<Element> ndtagList = el.getChildren();
                ArrayList<Attribute> nodes = new ArrayList<Attribute>();
                boolean motorway_link = false; //true: node is motorway_link; false: node is motorway
                boolean ishighway = false;

                for (Element ndtag: ndtagList) {
                    if (ndtag.getName().equals("nd")) {
                        Attribute nd = ndtag.getAttribute("ref");
                        nodes.add(nd);
                    }else if(ndtag.getName().equals("tag")){
                        Attribute k = ndtag.getAttribute("k");
                        Attribute v = ndtag.getAttribute("v");
                        if(k.getValue().equals("highway")) {
                            ishighway = true;
                            if(v.getValue().equals("motorway_link"))
                                motorway_link = true;
                        }
                    }
                }

                if(ishighway) {
                    OneWay ow = new OneWay(nodes, motorway_link);
                    OneWayList.add(ow);
                }
            }else if(el.getName().equals("node")){
                try{
                    long nodeid =  el.getAttribute("id").getLongValue();
                    //System.out.println("id");
                    //knoten[nodeid][0] = el.getAttribute("lat").getDoubleValue();
                    //knoten[nodeid][1] = el.getAttribute("lon").getDoubleValue();
                    hmaplat.put(nodeid, el.getAttribute("lat").getDoubleValue());
                    //System.out.println("lat");
                    hmaplon.put(nodeid, el.getAttribute("lon").getDoubleValue());
                    //System.out.println("lon");
                    //Node nd = new Node(nodeid.getIntValue(), false, nodelon.getDoubleValue(), nodelat.getDoubleValue());
                    //>>Graph<<.addNode(nd);
                }catch (Exception e) {
                    //TODO (Occurs when conversion to int/double failed)
                    System.out.println("Error!" + el.getAttribute("id").getValue());
                }
            }
        }
    }

    private int getMaxIdOfNodes(List<Element> list)
    {
        /*
        int count = 0;
        for (Element el : list){
            if(el.getName().equals("node"))
                count++;
        }
        return count;

         */
        int max = 0;
        for (Element el: list){
            if(el.getName().equals("node"))
            {
                try{
                    if(el.getAttribute("id").getIntValue() > max)
                        max = el.getAttribute("id").getIntValue();
                }catch(Exception e){

                }
            }

        }

        return max;
    }

    private void wayInsertion(List<OneWay> wayList){
        int z = 0;
        System.out.println("Länge: " + wayList.size());
       // for(int i = 0; i < wayList.size())

        for(OneWay ow : wayList){
            //TODO: Weg einfügen
            createNode(ow.getFirst());
            createNode(ow.getLast());
            z++;
            if(z % 100 == 0 || z < 20)
            System.out.println(z + "Weg erstellt.");

        }
    }
}
