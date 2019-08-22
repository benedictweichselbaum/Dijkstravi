package application.graphNavigation.algorithms;


import application.graphNavigation.graph.Connection;
import application.graphNavigation.graph.Graph;

import java.util.*;

public class BellmanFord extends NavigationService {

    private Map<Integer, Integer> precursors;

    public Stack<Integer> calculateShortestWay(Graph g, int startNodeId, int targetNodeId) {


        HashMap<Integer, ArrayList<Connection>> links = g.getLinks();
        List<ArrayList<Integer>> aimLists = g.getListOfAllEdges();
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
        //relax(links, numberOfNodes, distance);
        relaxNew(links, numberOfNodes, distance);
        //relaxNewList(aimLists, links, numberOfNodes, distance);

        //printPrecursorsForDebugging();
        printRes(distance, numberOfNodes);
        return output(g, startNodeId, targetNodeId);
    }

    /*private void relaxNewList(List<ArrayList<Integer>> aimLists, HashMap<Integer, ArrayList<Connection>> links, int numberOfNodes, Map<Integer, Integer> distance) {
        for (int src = 0; src < numberOfNodes - 1; src++) {
            for (List<Integer> edges:aimLists) {
                for (int aim:edges) {

                    int weight = links.get(src).get().getLength();
                    int distanceOfSrc = distance.get(src);

                    if (distanceOfSrc != INFINITE) {
                        int newDistance = distanceOfSrc + weight;
                        if (newDistance < distance.get(aim)) {
                            distance.put(aim, newDistance);
                            precursors.put(aim, src);
                        }
                    }
                }
            }
        }
    }*/

    private void relaxNew(HashMap<Integer, ArrayList<Connection>> links, int numberOfNodes, Map<Integer, Integer> distance) {
        for (int src = 0; src < numberOfNodes - 1; src++) {
            for (int k = 0; k < links.size(); k++) {
                for (int j = 0; j < links.get(src).size(); j++) {
                    int dest = links.get(src).get(j).getAim();
                    int weight = links.get(src).get(j).getLength();

                    int distanceOfSrc = distance.get(src);
                    if (distanceOfSrc != INFINITE) {
                        int newDistance = distanceOfSrc + weight;
                        if (newDistance < distance.get(dest)) {
                            distance.put(dest, newDistance);
                            precursors.put(dest, src);
                        }
                    }
                }
            }
        }
    }

    private void relax(HashMap<Integer, ArrayList<Connection>> links, int numberOfNodes, Map<Integer, Integer> distance) {
        for (int src = 0; src < numberOfNodes - 1; src++) {
            for (int k = 0; k < links.get(src).size(); k++) {
                int dest = links.get(src).get(k).getAim();
                int weight = links.get(src).get(k).getLength();

                int distanceOfSrc = distance.get(src);
                if (distanceOfSrc != INFINITE) {
                    int newDistance = distanceOfSrc + weight;
                    if (newDistance < distance.get(dest)) {
                        distance.put(dest, newDistance);
                        precursors.put(dest, src);
                    }
                }
            }
        }
    }


    // A utility function used to print the solution fr
    private void printRes(Map<Integer, Integer> distance, int V) {
        System.out.println("Vertex   Distance from Source");
        for (int i = 0; i < V; ++i)
            System.out.println(i + "\t\t\t" + distance.get(i));
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
        if (g.getNodeById(startNode).getName().equals("HelperNode")) {
            way.pop();
        }
        return way;
    }


    private void printPrecursorsForDebugging() {
        for (int i = 0; i < precursors.size(); i++) {
            System.out.println("precursors: " + precursors.get(i) + "; position: " + i);
        }
    }
}

