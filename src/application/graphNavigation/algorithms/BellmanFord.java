package application.graphNavigation.algorithms;


import application.graphNavigation.graph.Connection;
import application.graphNavigation.graph.Graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class BellmanFord extends NavigationService {

    private Map<Integer, Integer> precursors;

    public Stack<Integer> calculateShortestWay(Graph g, int startNodeId, int targetNodeId) {


        HashMap<Integer, ArrayList<Connection>> links = g.getLinks();
        int numberOfNodes = g.getAmountOfNodes();
        //getNumberOfLinks() in class Graph
        //int numberOfEdges = links.size();
        Map<Integer, Integer> distance = new HashMap<>();
        precursors = new HashMap<>();

        for (int i = 0; i < numberOfNodes; i++) {
            distance.put(i, INFINITE);
            precursors.put(i, INFINITE);
        }
        distance.put(startNodeId, 0);

        //relax
        for (int src = 0; src < numberOfNodes; src++) {
            for (int k = 0; k < links.get(src).size(); k++) {
                int dest = links.get(src).get(k).getAim();
                int weight = links.get(src).get(k).getLength();

                int distanceOfSrc = distance.get(src);
                if (distanceOfSrc != INFINITE) {
                    int newDistance = distanceOfSrc + weight;
                    if (newDistance < distance.get(dest)) {
                        distance.put(dest, newDistance);
                        precursors.put(dest, src);
                        //precursors.put(dest, src);
                    }
                }
            }
        }

        System.out.println("Size precursors:" + precursors.size());

        //printPrecursorsForDebugging();
        //printRes(distance, numberOfNodes);
        return output(g, startNodeId, targetNodeId);
    }

    // A utility function used to print the solution fr
    private void printRes(Map<Integer, Integer> distance, int V) {
        System.out.println("Vertex   Distance from Source");
        for (int i = 0; i < V; ++i)
            System.out.println(i + "\t\t\t" + distance.get(i));
    }


    private Stack<Integer> output(Graph g, int startNode, int targetNode) {
        Stack<Integer> way = new Stack<>();
        if(!g.getNodeById(targetNode).getName().equals("HelperNode")){
            way.push(targetNode);
        }

        int predecessor = targetNode;
        printPrecursorsForDebugging();
        System.out.println("Bed1: " + (precursors.get(predecessor) != INFINITE));
        System.out.println("Bed2: " + (predecessor != startNode));
        while ((precursors.get(predecessor) != INFINITE) && (predecessor != startNode)) {
            System.out.println(" Ho! ");
            predecessor = precursors.get(predecessor);
            way.push(predecessor);
        }
        System.out.println("Size Way Transitional:" + way.size());
        if (g.getNodeById(startNode).getName().equals("HelperNode")) {
            way.pop();
        }
        System.out.println("Size Way:" + way.size());
        return way;
    }


    private void printPrecursorsForDebugging() {
        for (int i = 0; i < precursors.size(); i++) {
            if(precursors.get(i) != INFINITE) {
                System.out.println("precursors: " + precursors.get(i) + "; position: " + i);
            }
        }
    }
}

