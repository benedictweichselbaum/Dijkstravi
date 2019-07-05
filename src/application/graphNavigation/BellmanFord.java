package application.graphNavigation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

class BellmanFord extends NavigationService {

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
                    int newDistance=distanceOfSrc + weight;
                    if (newDistance < distance.get(dest)) {
                        distance.put(dest, newDistance);
                        precursors.put(dest, src);
                        //precursors.put(dest, src);
                    }
                }
            }
        }
        for (int i = 0; i < precursors.size(); i++) {
            System.out.println("precursors: " + precursors.get(i) + "; position: " + i);
        }
        printRes(distance, numberOfNodes);
        return output(startNodeId, targetNodeId);
    }

    // A utility function used to print the solution fr, just for debugging
    private void printRes(Map<Integer, Integer> distance, int V) {
        System.out.println("Vertex   Distance from Source");
        for (int i = 0; i < V; ++i)
            System.out.println(i + "\t\t\t" + distance.get(i));
    }

    private Stack<Integer> output(int startNode, int targetNode) {
        Stack<Integer> way = new Stack<>();
        int predecessor = targetNode;
        while ((precursors.get(predecessor) != INFINITE) && (predecessor != startNode)) {
            predecessor = precursors.get(predecessor);
            System.out.println("VertexNr precursor " + (predecessor));
            way.push(predecessor);
        }
        way.pop();
        return way;
    }
}