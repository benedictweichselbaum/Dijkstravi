package application.graphNavigation;


//funktioniert noch nicht ganz aber fürs erste, mach morgen weiter
public class BellmanFord implements Navigator {

    private final int INFINITE = Integer.MAX_VALUE;
    private int[] precursors;

    public void calculateShortestWay(Graph g, int startNode, int targetNode) {

        startNode = g.getMatrixNodeNumberById(startNode);
        targetNode = g.getMatrixNodeNumberById(targetNode);

        int[][] matrix = g.autobahn;
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

                if (distance[src] != INFINITE && distance[src] + weight < distance[dest]) {
                    distance[dest] = distance[src] + weight;
                    precursors[dest] = src;
                }
            }
        }

        printErg(distance, numberOfNodes);
        printWay(startNode,targetNode);
    }

    // A utility function used to print the solution fr
    private void printErg(int[] dist, int V) {
        System.out.println("Vertex   Distance from Source");
        for (int i = 0; i < V; ++i)
            System.out.println(i + "\t\t" + dist[i]);
    }

    private void printWay(int startNode, int targetNode) {
        if ((precursors[targetNode] != INFINITE) && (precursors[targetNode] != startNode)) {
            System.out.println("Knotennr " + precursors[targetNode]);
            printWay(startNode,precursors[targetNode]);
        }
    }
}
