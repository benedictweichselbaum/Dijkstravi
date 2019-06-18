package application.xmlParser;
import java.io.*;
import java.util.*;

import application.graphNavigation.Node;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.w3c.dom.Attr;

public class XMLParser {

    public void init(){
        try {
            File inputFile = new File("german_autobahn.osm");
            SAXBuilder saxBuilder = new SAXBuilder();
            Document document = saxBuilder.build(inputFile);
            Element classElement = document.getRootElement();

            List<Element> wayList = classElement.getChildren();

            insertWays(wayList);

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
        createNode(getNode(nodes.get(0), nodesList)); // first node
        createNode(getNode(nodes.get(nodes.size() - 1), nodesList));// last node
        int length = calculateLength(nodes, nodesList);
        //TODO: Den Knoten wird noch nocht mitgegeben, ob sie ein motorway_link sind, da nicht klar ist, ob der Start- oder der Endknoten ein motorway_link ist
        createWay(nodes.get(0), nodes.get(nodes.size() - 1), length);
        System.out.println("Weg von " + nodes.get(0) + " zu " + nodes.get(nodes.size() - 1) + " mit Länge " + length + " Meter erstellt!" );
    }

    private void createNode(Element node)
    {
        try{
            Attribute nodeid =  node.getAttribute("id");
            Attribute nodelat =  node.getAttribute("lat");
            Attribute nodelon =  node.getAttribute("lon");
            Node nd = new Node(nodeid.getIntValue(), false, nodelon.getDoubleValue(), nodelat.getDoubleValue());
            //>>Graph<<.addNode(nd);
        }catch (Exception e) {
            //TODO (Occurs when conversion to int/double failed)
        }
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
}
