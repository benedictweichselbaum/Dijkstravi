package application.graphNavigation.algorithms;


import application.graphNavigation.graph.Connection;
import application.graphNavigation.graph.Graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;


public class BellmanFord extends NavigationService {

    private HashMap<Integer, ArrayList<Connection>> links;
    private Map<Integer, Integer> precursors;
    private Map<Integer, Integer> distance;

    public Stack<Integer> calculateShortestWay(Graph g, int startNodeId, int targetNodeId) {

        links = g.getLinks();
        int numberOfNodes = g.getAmountOfNodes();
        distance = new HashMap<>(numberOfNodes);
        precursors = new HashMap<>(numberOfNodes);

        for (int i = 0; i < numberOfNodes; i++) {
            distance.put(i, INFINITE);
            precursors.put(i, INFINITE);
        }
        distance.put(startNodeId, 0);
        relax(numberOfNodes);

        return output(g, startNodeId, targetNodeId);
    }

    private void relax(int numberOfNodes) {
        double progressUnit = 1.0/(numberOfNodes);
        boolean change = true;
        for (int i = 1; i < numberOfNodes; i++) {
            for(int src = 0; src < numberOfNodes; src++){
                for (int k = 0; k < links.get(src).size(); k++) {
                    int dest = links.get(src).get(k).getAim();
                    int weight = links.get(src).get(k).getLength();

                    int distanceOfSrc = distance.get(src);
                    if (distanceOfSrc != INFINITE) {
                        int newDistance = distanceOfSrc + weight;
                        if (newDistance < distance.get(dest)) {
                            distance.put(dest, newDistance);
                            precursors.put(dest, src);
                            change=true;
                        }
                    }
                }
            }
            progress += progressUnit;
            if(!change){
                i = numberOfNodes;
            }
            change = false;
        }
    }

    private Stack<Integer> output(Graph g, int startNode, int targetNode) {
        Stack<Integer> way = new Stack<>();
        if (!g.getNodeById(targetNode).getName().equals("HelperNode")) {
            way.push(targetNode);
        }
        int predecessor = targetNode;
        while ((precursors.get(predecessor) != INFINITE) && (predecessor != startNode)) {
            predecessor = precursors.get(predecessor);
            way.push(predecessor);
        }
        if (g.getNodeById(startNode).getName().equals("HelperNode") && !way.empty()) {
            way.pop();
        }

        if(way.empty() || way.size() == 1){
            return null;
        }
        else{
            return way;
        }
    }
}

