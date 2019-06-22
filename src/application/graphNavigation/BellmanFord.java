package application.graphNavigation;


import java.util.ArrayList;
import java.util.List;

//matrix is not needed here, just an array of nodes and an array of edges!!!!!!!!!!

public class BellmanFord implements Navigator {

    private int[] precursors;
    private List<Long> res=new ArrayList<>();

    public void calculateShortestWay(Graph g, long startNodeId, long targetNodeId) {

        int startNode = g.getMatrixNodeNumberById(startNodeId);
        int targetNode = g.getMatrixNodeNumberById(targetNodeId);

        Node[] nodes = g.nodes;
        Edge[] edges = g.getEdges();

        int numberOfNodes = nodes.length;
        int numberOfEdges = edges.length;

        int[] distance = new int[numberOfNodes];
        //Node[] precursors=new Node[numberOfNodes];
        precursors = new int[numberOfNodes];
        //implementation in graph needed

        for (int i = 0; i < numberOfNodes; i++) {
            distance[i] = INFINITE;
            precursors[i] = INFINITE;
        }
        distance[startNode] = 0;

        //relax
        for (int j = 0; j < numberOfNodes; j++) {
            for (int k = 0; k < numberOfEdges; k++) {
                int src = edges[k].getSrc();
                int dest = edges[k].getDest();
                int weight = edges[k].getWeight();

                if ((distance[src] != INFINITE) && ((distance[src] + weight) < distance[dest])) {
                    distance[dest] = distance[src] + weight;
                    precursors[dest] = src;
                }
            }
        }

        printRes(distance, numberOfNodes);
        printWay(startNode, targetNode);
    }

    // A utility function used to print the solution fr
    private void printRes(int[] dist, int V) {
        System.out.println("Vertex   Distance from Source");
        for (int i = 0; i < V; ++i)
            System.out.println(i+1 + "\t\t\t" + dist[i]);
    }

    private void printWay(int startNode, int targetNode) {
        if ((precursors[targetNode] != INFINITE) && (precursors[targetNode] != startNode)) {
            //+1 because for graphical demonstration in console
            System.out.println("VertexNr " + (precursors[targetNode]+1));
            //res.add(...);
            printWay(startNode, precursors[targetNode]);
        }
    }
    long[] getResult(){
        return res.stream().mapToLong(l -> l).toArray();
    }
}
