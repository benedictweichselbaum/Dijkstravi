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

public class XMLParser {

    public void runParser(){
        try {
            File inputFile = new File("german_autobahn.osm");
            SAXBuilder saxBuilder = new SAXBuilder();
            Document document = saxBuilder.build(inputFile);
            Element classElement = document.getRootElement();

            List<Element> listOfChildren = classElement.getChildren();

            //scanForWaysAndCreateWays(listOfChildren);

            Graph graphTest = createGraphFromXMLChildrendsList(listOfChildren);
            //graphTest.printOutMartrix();

        } catch(JDOMException e) {
            e.printStackTrace();
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public void scanForWaysAndCreateWays(List<Element> listOfChildrenContainingWays){
        for (Element way : listOfChildrenContainingWays) {
            if(way.getName().equals("way")){
                /*
                List<Element> ndTagList = way.getChildren();
                ArrayList<Attribute> wayNodesList = new ArrayList<>();
                boolean motorway_link = false; //true: node is motorway_link; false: node is motorway
                boolean ishighway = false;

                for (Element ndTag: ndTagList) {
                    if (ndTag.getName().equals("nd")) {
                        Attribute nd = ndTag.getAttribute("ref");
                        wayNodesList.add(nd);
                    }else if(ndTag.getName().equals("tag")){
                        Attribute k = ndTag.getAttribute("k");
                        Attribute v = ndTag.getAttribute("v");
                        if(k.getValue().equals("highway")) {
                            ishighway = true;
                            if(v.getValue().equals("motorway_link"))
                                motorway_link = true;
                        }
                    }
                }
                if(ishighway)
                    createWayWithMultipleNodes(wayNodesList, listOfChildrenContainingWays);*/
            }
        }
    }

    private int calculateLength(ArrayList<Attribute> nodes, List<Element> nodeList){
        // TODO: Nodes holen und mithilfe Längen- und Breitengraden länge berechnen
        return 2;
    }

    private Element getNodeFromListById(Attribute id, List<Element> wayList){
        for (Element node : wayList) {
            if(node.getName().equals("node")) {
                if(node.getAttribute("id").equals(id))
                    return node;
            }
        }
        return null;
    }

    private void createWayWithMultipleNodes(ArrayList<Attribute> nodesIds, List<Element> completeListOfChildren) //Creates way with nodes and adds them to
    {
        //createNode(getNodeFromListById(nodesIds.get(0), completeListOfChildren)); // first node
        //createNode(getNodeFromListById(nodesIds.get(nodesIds.size() - 1), completeListOfChildren));// last node
        int length = calculateLength(nodesIds, completeListOfChildren);
        //TODO: Den Knoten wird noch nocht mitgegeben, ob sie ein motorway_link sind, da nicht klar ist, ob der Start- oder der Endknoten ein motorway_link ist
        createEdgeInGraph(nodesIds.get(0), nodesIds.get(nodesIds.size() - 1), length);
        //System.out.println("Weg von " + nodesIds.get(0) + " zu " + nodesIds.get(nodesIds.size() - 1) + " mit Länge " + length + " Meter erstellt!" );
    }

    private Node createNode(Element node, boolean isLink)
    {
        try{
            Attribute nodeid =  node.getAttribute("id");
            Attribute nodelat =  node.getAttribute("lat");
            Attribute nodelon =  node.getAttribute("lon");
            return new Node(nodeid.getIntValue(), isLink, nodelon.getDoubleValue(), nodelat.getDoubleValue());
            //>>Graph<<.addNode(nd);
        }catch (Exception e) {
            return null;
        }
    }

    private void createEdgeInGraph(Attribute from, Attribute to, int length)
    {
        //TODO: Insert way in the Adjazenzmatrix
        try{
           //>>Graph<<.addEdge(from.getIntValue(), to.getIntValue(), length);
        }catch (Exception e) {
            //TODO (Occurs when conversion of id to int failed -> never)
        }
    }

    public Graph createGraphFromXMLChildrendsList (List<Element> listOfOSMChildren) {
        Graph graph;
        List<Element> nodesInOSM = new ArrayList<>();
        List<Element> waysInOSM = new ArrayList<>();
        for (Element node: listOfOSMChildren) {
            if (node.getName().equals("node"))
                nodesInOSM.add(node);
        }
        for (Element way: listOfOSMChildren) {
            if (way.getName().equals("way"))
               waysInOSM.add(way);
        }

        List<Way> listOfWays = new ArrayList<>();
        int progress = 0;
        int size = waysInOSM.size();
        for (Element wayToOperateOn: waysInOSM) {
            progress++;
            System.out.println(progress + "/" + size);
            List<Element> ndTagList = wayToOperateOn.getChildren();
            List<Attribute> nodesInWayByReference = new ArrayList<>();
            boolean motorway_link = false; //true: node is motorway_link; false: node is motorway
            boolean highway = false;

            for (Element ndTag: ndTagList) {
                if (ndTag.getName().equals("nd")) {
                    Attribute nd = ndTag.getAttribute("ref");
                    nodesInWayByReference.add(nd);
                }else if(ndTag.getName().equals("tag")){
                    Attribute k = ndTag.getAttribute("k");
                    Attribute v = ndTag.getAttribute("v");
                    if(k.getValue().equals("highway")) {
                        highway = true;
                        if (v.getValue().equals("motorway_link"))
                            motorway_link = true;
                    }
                }
            }
            if (highway)
            listOfWays.add(new Way(createListOfNodesByReference(nodesInWayByReference, nodesInOSM, motorway_link)));
        }

        int numberOfMotorwayLinkNodes = 0;
        for (Way wayInList: listOfWays) {
            wayInList.setDistance(calculateLengthOfWay(wayInList));
            if (wayInList.nodes.get(0).isLink()) {
                numberOfMotorwayLinkNodes = numberOfMotorwayLinkNodes + 2;
            }
        }

        int numberOfNodes = listOfWays.size()*2;

        graph = new Graph(numberOfNodes, numberOfMotorwayLinkNodes);

        /*for (Way wayInListOfWays: listOfWays) {
            graph.addNode(wayInListOfWays.nodes.get(0));
            graph.addNode(wayInListOfWays.nodes.get(wayInListOfWays.nodes.size()-1));
            graph.addEdge(wayInListOfWays.nodes.get(0).getId(),
                    wayInListOfWays.nodes.get(wayInListOfWays.nodes.size()-1).getId(),
                    wayInListOfWays.distance);
        }*/

        return graph;
    }

    private int calculateLengthOfWay (Way way) {
        return 2; //Dummy Value
    }

    private List<Node> createListOfNodesByReference (List<Attribute> listOfNodeReferences,
                                                     List<Element> listOfAvailableNodes,
                                                     boolean isMotorwayLink) {
        List<Node> listOfNodes = new LinkedList<>();
        for (Attribute attribute: listOfNodeReferences) {
            String reference = attribute.getValue();
            for (Element node: listOfAvailableNodes) {
                if (attribute.getValue().equals(node.getAttribute("id").getValue())) {
                    listOfNodes.add(createNode(node, isMotorwayLink));
                }
            }
        }

        return listOfNodes;
    }
}
