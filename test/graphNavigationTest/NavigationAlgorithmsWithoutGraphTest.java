package graphNavigationTest;

import application.graphNavigation.algorithms.*;
import application.graphNavigation.graph.Connection;
import application.graphNavigation.graph.Graph;
import application.graphNavigation.graph.Node;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for testing the navigation algorithms
 * With pop-operations the result could be read and verified
 * The values of the edges correspond with real values
 * Anyway, the results differ from real navigation systems, because only length-values from edges could be used
 */

class NavigationAlgorithmsWithoutGraphTest {

    //Graf ohne Verbindungen
    private static Graph g;

    @BeforeAll
    static void initGraph() {
        g = new Graph();
        addSomeNodes(g);
        insertSomeEdges(g);
        printAllConnectionsOfAllNodes(g);
    }

    @Test
    void dijkstra(){
        shortestPathCombinationOfSimpleWays(new Dijkstra());
        shortestPathWithStopoverToHorb(new Dijkstra());
        algorithmsHelperNodeIntegration(new Dijkstra());
    }

    @Test
    void aStar(){
        shortestPathCombinationOfSimpleWays(new AStar());
        shortestPathWithStopoverToHorb(new AStar());
        algorithmsHelperNodeIntegration(new AStar());
    }

    @Test
    void bellmanFord(){
        shortestPathWithStopoverToHorb(new BellmanFord());
        shortestPathCombinationOfSimpleWays(new BellmanFord());
        algorithmsHelperNodeIntegration(new BellmanFord());
    }

    @Test
    void shortestPathFaster(){
        shortestPathWithStopoverToHorb(new ShortestPathFasterAlgorithm());
        shortestPathCombinationOfSimpleWays(new ShortestPathFasterAlgorithm());
        algorithmsHelperNodeIntegration(new ShortestPathFasterAlgorithm());
    }

    @Test
    void bellmanFordFast(){
        shortestPathWithStopoverToHorb(new BellmanFordFast());
        shortestPathCombinationOfSimpleWays(new BellmanFordFast());
        algorithmsHelperNodeIntegration(new BellmanFordFast());
    }

    void shortestPathCombinationOfSimpleWays(NavigationService navigationService) {
        System.out.println("Algorithms combination of simple ways");
        Stack<Integer> way = navigationService.initCalculateShortestWay(g, 0, 3, 130, false);
        assertEquals(0, way.pop());
        assertEquals(1, way.pop());
        assertEquals(3, way.pop());
    }

    void shortestPathWithStopoverToHorb(NavigationService navigationService) {
        System.out.println("Algorithm with stopover to Horb");
        //Stack<Integer> way = navigationService.calculateShortestWay(g, 1, 7);
        Stack<Integer> way = navigationService.initCalculateShortestWay(g, 1, 7, 130, false);
        assertEquals(1, way.pop());
        assertEquals(3, way.pop());
        assertEquals(4, way.pop());
        assertEquals(5, way.pop());
        assertEquals(7, way.pop());
    }

    void algorithmsHelperNodeIntegration(NavigationService navigationService) {
        System.out.println("Algorithm Helper Node Integration");
        //Stack<Integer> way = navigationService.calculateShortestWay(g, 1, 7);
        Stack<Integer> way = navigationService.initCalculateShortestWay(g, 4, 8, 130, false);
        assertEquals(4, way.pop());
        assertEquals(5, way.pop());
        assertEquals(7, way.pop());
        assertTrue(way.isEmpty());
    }

    void fastestPathWithStopoverToHorb(NavigationService navigationService) {
        System.out.println("Algorithm with stopover to Horb");
        //Stack<Integer> way = navigationService.calculateShortestWay(g, 1, 7);
        Stack<Integer> way = navigationService.initCalculateShortestWay(g, 1, 7, 250, true);
        assertEquals(1, way.pop());
        assertEquals(3, way.pop());
        assertEquals(4, way.pop());
        assertEquals(5, way.pop());
        assertEquals(7, way.pop());
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
        gr.addEdge(4, 3, 172000, 130, "A6", "Nürnberg");
        gr.addEdge(3, 4, 172000, 130, "A6", "Heilbronn");
        gr.addEdge(4, 5, 53000, 130, "A81", "Stuttgart");
        gr.addEdge(5, 4, 53000, 130, "A81", "Heilbronn");
        gr.addEdge(6, 5, 92000, 130, "A8", "Stuttgart");
        gr.addEdge(5, 6, 92000, 130, "A8", "Ulm");
        gr.addEdge(6, 0, 155000, 130, "A8", "München");
        gr.addEdge(0, 6, 155000, 130, "A8", "Ulm");
        gr.addEdge(7, 5, 61900, 130, "A81", "Stuttgart");
        gr.addEdge(5, 7, 61900, 130, "A81", "Horb");
        gr.addEdge(8, 7, 61900, 130, "Helper Node", "Horb");
        gr.addEdge(7, 8, 61900, 130, "Horb", "Helper Node");
    }

    private static void addSomeNodes(Graph g) {
        Node muenchen = new Node(0, true, 48.140235, 11.560985, "München");
        Node ingolstadt = new Node(1, true, 48.765942, 11.425247, "Ingolstadt");
        Node landshut = new Node(2, true, 48.547067, 12.142666, "Landshut");
        Node nuernberg = new Node(3, true, 49.446800, 11.078967, "Nürnberg");
        Node heilbronn = new Node(4, true, 49.137541, 9.219953, "Heilbronn");
        Node stuttgart = new Node(5, true, 48.775408, 9.183056, "Stuttgart");
        Node ulm = new Node(6, true, 48.398468, 9.988471, "Ulm");
        Node horb = new Node(7, true, 48.445462, 8.696793, "Horb DHBW");
        Node helperNodeHorb = new Node(8, true, 48.445462, 8.696793, "HelperNode");
        Node unconnectedNode = new Node(9, true, 48.000000, 8.000000, "Unconnected Node");


        g.addNodesSorted(muenchen);
        g.addNodesSorted(ingolstadt);
        g.addNodesSorted(landshut);
        g.addNodesSorted(nuernberg);
        g.addNodesSorted(heilbronn);
        g.addNodesSorted(stuttgart);
        g.addNodesSorted(ulm);
        g.addNodesSorted(horb);
        g.addNodesSorted(helperNodeHorb);
        g.addNodesSorted(unconnectedNode);
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