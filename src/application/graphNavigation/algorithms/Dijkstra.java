
package application.graphNavigation.algorithms;

import application.graphNavigation.graph.Connection;
import application.graphNavigation.graph.Graph;
import application.graphNavigation.graph.Node;

import java.util.*;

/**
 * Implementation of the Dijkstra Algorithm. This algorithm calculates the fastest/shortest way from the startNode to the target node.
 */

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
    private Map<Integer, Integer> reachableUnvisitedNodes;


    private void init(Graph g, int startNodeId, int targetNodeId) {
        autobahn = g.getAutobahn();

        startNode = startNodeId;
        targetNode = targetNodeId;
        latTargetNode = autobahn.get(targetNode).getLatitude();
        lngTargetNode = autobahn.get(targetNode).getLongitude();

        numberOfNodes = g.getAmountOfNodes();
        visited = new HashSet<>();  // green marked nodes in power point
        distance = new HashMap<>();
        predecessor = new HashMap<>();

        reachableUnvisitedNodes = new HashMap<>();  // red marked nodes in power point
        reachableUnvisitedNodes.put(startNode, 0);

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

        // repeat until all nodes are visited / target node is found
        Double progressUnit = 1.0/numberOfNodes;
        for (int i = 0; i < numberOfNodes; i++)
        {
            if(reachableUnvisitedNodes == null){
                break;
            }
            nodeNumber = getPositionOfUnvisitedNodeWithShortestDistance(reachableUnvisitedNodes);
            visited.add(nodeNumber);
            reachableUnvisitedNodes.remove(nodeNumber);

            // to stop calculating when target node is found
            if(nodeNumber == targetNode){
                break;
            }

            calculateDistanceToUnvisitedNeighboringNodes(g, nodeNumber);
            progress += progressUnit;
        }
        //if visited contains target node, there exists a way
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

        ArrayList<Connection> allConnectionsOfNode = g.getAllConnectionsOfNode(nodeNumber);
        for (Connection connectionToNeighbor : allConnectionsOfNode){
            int neighboringNode = connectionToNeighbor.getAim();
            // in case neighboringNode node has not been visited yet
            if (!visited.contains(neighboringNode))
            {
                int distanceToNeighbor = getDistance(connectionToNeighbor);
                predictedDistance = getPredictedDistance(neighboringNode);
                newDistance = distance.get(nodeNumber) + distanceToNeighbor;

                if (newDistance < distance.get(neighboringNode))
                {
                    distance.put(neighboringNode, newDistance);
                    predecessor.put(neighboringNode, nodeNumber);

                    reachableUnvisitedNodes.put(neighboringNode, (newDistance + predictedDistance));
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

        // possible HelperNodes are excluded from stack
        // Result: stack includes way from start (top) to target (bottom)
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



