package application.globalLogic;

import application.graphNavigation.algorithms.AlgorithmThread;
import application.graphNavigation.algorithms.Dijkstra;
import application.graphNavigation.algorithms.AbstractAlgorithm;
import application.graphNavigation.algorithms.navigationExceptions.NoWayFoundException;
import application.graphNavigation.graph.Graph;
import application.graphNavigation.graph.Node;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

class NoWayFoundExceptionTest {
    private static Graph g;

    @BeforeAll
    static void initGraph() {
        g = new Graph();
        addSomeNodes(g);
        insertSomeEdges(g);
    }

    @Test
    void algorithmThrowsNoWayFoundExceptionIfNecessary() {
        System.out.println("NoWayFoundExceptionThrown:");
        boolean thrown = false;
        try {
            AbstractAlgorithm abstractAlgorithm =new Dijkstra();
            Stack<Integer> way = abstractAlgorithm.initCalculateShortestWay(g, 0,2,130,false);
            AlgorithmThread at=new AlgorithmThread();
            boolean connectionFound=at.connectionFound(way);
        } catch (NoWayFoundException e) {
            thrown = true;
        }

        assertTrue(thrown);
    }

    private static void insertSomeEdges(Graph gr) {
        gr.addEdge(0, 1, 84100, 130, "A9", "Ingolstadt");
        gr.addEdge(1, 0, 84100, 130, "A9", "München");
        gr.addEdge(1, 3, 94300, 130, "A9", "Nürnberg");
    }

    private static void addSomeNodes(Graph g) {
        Node muenchen = new Node(0, true, 48.140235,11.560985,  "München");
        Node ingolstadt = new Node(1, true,  48.765942, 11.425247,"Ingolstadt");
        Node landshut = new Node(2, true,  48.547067,12.142666, "Landshut");
        Node nuernberg = new Node(3, true,  49.446800,11.078967, "Nürnberg");


        g.addNodesSorted(muenchen);
        g.addNodesSorted(ingolstadt);
        g.addNodesSorted(landshut);
        g.addNodesSorted(nuernberg);
    }
}