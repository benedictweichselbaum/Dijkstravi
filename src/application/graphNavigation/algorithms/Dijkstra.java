
package application.graphNavigation.algorithms;

import application.graphNavigation.graph.Connection;
import application.graphNavigation.graph.Graph;
import application.graphNavigation.graph.Node;

import java.util.*;

public class Dijkstra extends AbstractAlgorithm {

    private ArrayList<Node> autobahn = new ArrayList<>();

    private int startNode;
    private int targetNode;

    private double latTargetNode;
    private double lngTargetNode;

    private int numberOfNodes;
    private Set<Integer> visited;
    private Map<Integer, Integer> distance;
    private Map<Integer, Integer> predecessor;
    private Map<Integer, Integer> reachableUnvisitedNotes;

    public Dijkstra(){

    }

    private void init(Graph g, int startNodeId, int targetNodeId) {
        autobahn = g.getAutobahn();

        startNode = startNodeId;
        targetNode = targetNodeId;
        latTargetNode = autobahn.get(targetNode).getLatitude();
        lngTargetNode = autobahn.get(targetNode).getLongitude();

        numberOfNodes = g.getAmountOfNodes();
        visited = new HashSet<>();
        distance = new HashMap<>();
        predecessor = new HashMap<>();

        // die rot markierten Knoten -> PP
        reachableUnvisitedNotes = new HashMap<>();
        reachableUnvisitedNotes.put(startNode, 0);

        for (int i = 0; i < numberOfNodes; i++) {
            distance.put(i, INFINITE);
        }
        distance.put(startNode, 0);
        predecessor.put(startNode, startNode);
    }

    public Stack<Integer> calculateShortestWay(Graph g, int startNodeId, int targetNodeId){
        if(g == null){
            return null;
        }

        int nodeNumber;
        init(g, startNodeId, targetNodeId);

        // wiederhole bis alle Knoten besucht sind / Zielknoten besucht ist
        Double progressUnit = 1.0/numberOfNodes;
        for (int i = 0; i < numberOfNodes; i++)
        {
            if(reachableUnvisitedNotes == null){
                break;
            }
            nodeNumber = getPositionOfUnvisitedNodeWithShortestDistance(reachableUnvisitedNotes);
            visited.add(nodeNumber);
            reachableUnvisitedNotes.remove(nodeNumber);

            //um nicht zu allen Knoten den kürzesten Weg vom Startknoten aus zu berechnen
            if(nodeNumber == targetNode){
                break;
            }

            calculateDistanceToUnvisitedNeighboringNodes(g, nodeNumber);
            progress += progressUnit;
        }
        if(visited.contains(targetNode)) {
            return output(g);
        }
        else{
            return null;
        }
    }

    private void calculateDistanceToUnvisitedNeighboringNodes(Graph g, int nodeNumber) {
        int newDistance;
        int predictedDistance;
        //falls Nachbarknoten noch nicht besucht
        ArrayList<Connection> allConnectionsOfNode = g.getAllConnectionsOfNode(nodeNumber);
        for (Connection connectionToNeighbor : allConnectionsOfNode){
            int neighboringNode = connectionToNeighbor.getAim();
            if (!visited.contains(neighboringNode))
            {
                int distanceToNeighbor = getDistance(connectionToNeighbor);
                predictedDistance = getPredictedDistance(neighboringNode);
                newDistance = distance.get(nodeNumber) + distanceToNeighbor;

                if (newDistance < distance.get(neighboringNode))
                {
                    distance.put(neighboringNode, newDistance);
                    predecessor.put(neighboringNode, nodeNumber);

                    reachableUnvisitedNotes.put(neighboringNode, (newDistance + predictedDistance));
                }
            }
        }
    }

    public int getPredictedDistance(int neighboringNode) {
        return 0;
    }

    private Stack<Integer> output(Graph g) {
        Stack<Integer> way = new Stack<>();
        int nodeNumber;

        if(!g.getNodeById(targetNode).getName().equals("HelperNode")){
            way.push(targetNode);
        }
        nodeNumber = targetNode;

        while (nodeNumber != startNode)
        {
            nodeNumber = predecessor.get(nodeNumber);
            way.push(nodeNumber);
        }
        if(g.getNodeById(startNode).getName().equals("HelperNode")){
            way.pop();
        }

        return way;
    }

    private int getPositionOfUnvisitedNodeWithShortestDistance(Map<Integer, Integer> nodeAndDistance) {

        Map.Entry<Integer, Integer> min = null;
        for (Map.Entry<Integer, Integer> entry : nodeAndDistance.entrySet()) {
            if (min == null || min.getValue() > entry.getValue()) {
                min = entry;
            }
        }
        if (min != null) {
            return min.getKey();
        }
        else{
            return 0;
        }
    }

    ArrayList<Node> getAutobahn() {
        return autobahn;
    }

    double getLatTargetNode() {
        return latTargetNode;
    }

    double getLngTargetNode() {
        return lngTargetNode;
    }
}



