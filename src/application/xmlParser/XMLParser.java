package application.xmlParser;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.*;

import application.graphNavigation.graph.Graph;
import application.graphNavigation.graph.MinimalPerformanceNode;
import application.graphNavigation.graph.Node;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/*
* This class reads the xml-File with all highways, picks the important information, calculates the length and creates the graph.
* The used algorithm has multiple optimizations and the created graph contains only relevant information.
 */
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

    public Graph init(){
        try {
            Path temp = Files.createTempFile("resource-", "ext");
            Files.copy(this.getClass().getResourceAsStream("german_autobahn.osm"), temp, StandardCopyOption.REPLACE_EXISTING);

            FileInputStream fileInputStream = new FileInputStream(temp.toFile());
            byte[] bytes = new byte[fileInputStream.available()];
            fileInputStream.read(bytes);
            fileInputStream.close();
            File inputFile = new File("osm.tmp");
            FileOutputStream outputStream = new FileOutputStream(inputFile);
            outputStream.write(bytes);
            outputStream.flush();
            outputStream.close();
            SAXBuilder saxBuilder = new SAXBuilder();
            Document document = saxBuilder.build(inputFile);
            Element classElement = document.getRootElement();

            List<Element> wayList = classElement.getChildren();

            saveXML(wayList);
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
    private MinimalPerformanceNode createNode(long node)
    {
        return new MinimalPerformanceNode(hmaplon.get(node), hmaplat.get(node));
    }

    private Node createNode2(int newNodeID, long node){
        String name = hmapname.get(node);
        if(name != null)
            listOfExits.put(newNodeID, name);
        return new Node(newNodeID, false, hmaplat.get(node), hmaplon.get(node), name);
    }

    private void saveXML(List<Element> list){
        for (Element el : list) {
            if(el.getName().equals("way")) {
                try {
                    addWay(el);
                }catch (Exception e){
                    //(Occurs when conversion to int/double failed) should never appear
                    System.out.println("Error! Way id conversion failed: " + el.getAttribute("id").getValue());
                }
            }else if(el.getName().equals("node")){
                try{
                    addNode(el);
                }catch (Exception e) {
                    //(Occurs when conversion to int/double failed) should never appear
                    System.out.println("Error!" + el.getAttribute("id").getValue());
                }
            }
        }
    }

    private void addNode(Element el) throws Exception{
        long nodeId =  el.getAttribute("id").getLongValue();

        hmaplat.put(nodeId, el.getAttribute("lat").getDoubleValue());
        hmaplon.put(nodeId, el.getAttribute("lon").getDoubleValue());

        List<Element> tagList = el.getChildren();

        for (Element tag : tagList) {
            if (tag.getAttribute("k").getValue().equals("name")) {
                hmapname.put(nodeId, tag.getAttribute("v").getValue());
            }
        }
    }

    private void addWay(Element el) throws Exception{
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
                            }
                        }
                        break;
                }
            }
        }

        if (ishighway) {
            Way ow = new Way(nodes, maxspeed, name, destination);
            wayList.add(ow);
        }
    }

    private void wayInsertion(List<Way> wayList){
        ArrayList<Long> allNeededNodes = createListOfAllNeededNodesSpeed(wayList);

        gr = new Graph();

        HashMap<Long, Integer> newID = new HashMap<>();
        for(int i = 0; i < allNeededNodes.size(); i++){
            gr.addNodesSorted(createNode2(i,allNeededNodes.get(i)));
            newID.put(allNeededNodes.get(i),i);
        }

        for(Way ow : wayList){
            addWayAsEdgeToGraph(ow, newID);
        }
    }

    private void addWayAsEdgeToGraph(Way ow, HashMap<Long, Integer> newID){
        List<MinimalPerformanceNode> nodeList = addWayToNodeList(ow);
        @SuppressWarnings("all")
        Double exactLength = DistanceCalculator.calculateDistanceBetweenAListOfNodes(nodeList);
        int length = exactLength.intValue();

        gr.addEdge(newID.get(ow.getFirst()), newID.get(ow.getLast()),
                length, ow.getmaxspeed(),
                ow.getName(),
                ow.getDestination());
    }

    private List<MinimalPerformanceNode> addWayToNodeList(Way ow){
        ArrayList<Attribute> nodeIDList = ow.getListOfIDsOfNodes();
        List<MinimalPerformanceNode> nodeList = new ArrayList<>();
        try {
            for (Attribute a: nodeIDList) {
                nodeList.add(createNode(a.getLongValue()));
            }
        }catch(Exception e){
            System.out.println("Conversion error!");
        }

        return nodeList;
    }

    private ArrayList<Long> createListOfAllNeededNodesSpeed(List<Way> wayList){
        HashMap<Long,Boolean> nodeIDsMap = new HashMap<>();
        for(Way ow : wayList) {
            nodeIDsMap.put(ow.getFirst(), true);
            nodeIDsMap.put(ow.getLast(), true);
        }
        ArrayList<Long> nodeIDs = new ArrayList<>();
        nodeIDsMap.forEach((l,b) -> nodeIDs.add(l));
        return  nodeIDs;
    }
}