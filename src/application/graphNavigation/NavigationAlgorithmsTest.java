package application.graphNavigation;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

class NavigationAlgorithmsTest {

    private static Graph g;

    @BeforeAll
    static void initGraph() {
        g = new Graph();
        addSomeNodes(g);
        insertSomeEdges(g);
        printAllConnectionsOfAllNodes(g);
    }

    @Test
    void bellmanFord() {
        System.out.println("BellmanFord:");
        NavigationService bellmanFord = new BellmanFord();
        Stack<Integer> way = bellmanFord.calculateShortestWay(g, 0, 3);
        for(int connection:way){
            System.out.println("Way found: "+connection);
        }
        assertEquals(0, way.pop());
        assertEquals(1, way.pop());
        assertEquals(3, way.pop());
    }

    @Test
    void dijkstra() {
        System.out.println("Dijkstra:");
        NavigationService dijkstra = new Dijkstra();
        Stack<Integer> way = dijkstra.calculateShortestWay(g, 0, 3);
        assertEquals(0, way.pop());
        assertEquals(1, way.pop());
        assertEquals(3, way.pop());
    }

    @Test
    void aStar() {
        System.out.println("AStar:");
        NavigationService aStar = new AStar();
        Stack<Integer> way = aStar.calculateShortestWay(g, 0, 3);
        assertEquals(0, way.pop());
        assertEquals(1, way.pop());
        assertEquals(3, way.pop());
    }

    private static void insertSomeEdges(Graph gr) {
        gr.addEdge(0, 1, 84100, 130, "A9", "Ingolstadt");
        gr.addEdge(1, 0, 84100, 130, "A9", "München");
        gr.addEdge(1, 3, 94300, 130, "A9", "Nürnberg");
        gr.addEdge(3, 1, 94300, 130, "A9", "Ingolstadt");
        gr.addEdge(0, 2, 76600, 130, "A92", "Landshut");
        gr.addEdge(2, 0, 76600, 130, "A92", "München");
        gr.addEdge(2, 3, 159000, 130, "B15n, A3", "Nürnberg");
        gr.addEdge(3, 2, 159000, 130, "A3, B15n", "Landshut");
    }

    private static void addSomeNodes(Graph g) {
        Node muenchen = new Node(0, true, 11.560985, 48.140235, "München");
        Node ingolstadt = new Node(1, true, 11.425247, 48.765942, "Ingolstadt");
        Node landshut = new Node(2, true, 12.142666, 48.547067, "Landshut");
        Node nuernberg = new Node(3, true, 11.078967, 49.446800, "Nürnberg");

        g.addNodesSorted(muenchen);
        g.addNodesSorted(ingolstadt);
        g.addNodesSorted(landshut);
        g.addNodesSorted(nuernberg);
    }

    private static void printAllConnectionsOfAllNodes(Graph gr) {

        for (int i = 0; i < gr.getAmountOfNodes(); i++)
            printAllConnectionsOfNode(i, gr);
    }

    private static void printAllConnectionsOfNode(int id, Graph gr) {
        ArrayList<Connection> connectionList = gr.getAllConnectionsOfNode(id);
        System.out.println("Vom Knoten mit der ID " + id + " gehen folgende Verbindungen aus:");
        for (Connection con : connectionList) {
            System.out.println("ZielID: " + con.getDestination() + " Länge: " + con.getLength() + " Straßenname: " + con.getName());
        }
    }
}