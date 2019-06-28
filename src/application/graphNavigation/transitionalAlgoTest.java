package application.graphNavigation;

import java.util.ArrayList;

public class transitionalAlgoTest {
    public static void main(String... args) {
        Graph gr = new Graph();
        addSomeNodes(gr);
        insertSomeEdges(gr);
        printAllConnectionsOfAllNodes(gr);

        System.out.println("  ");

        System.out.println("Dijkstrvigator:");
        Navigator dijkstrvigator =new DijkstraOrAStar("Dijkstra");
        dijkstrvigator.calculateShortestWay(gr, 0, 3);

        System.out.println("  ");

        System.out.println("AStarigator:");
        Navigator aStarigator = new DijkstraOrAStar("AStar");
        aStarigator.calculateShortestWay(gr, 0, 3);
    }

    static void insertSomeEdges(Graph gr){
        gr.addEdge(0,1, 84100, 130, "A9", "Ingolstadt");
        gr.addEdge(1,0, 84100, 130, "A9", "München");
        gr.addEdge(1,3, 94300, 130, "A9", "Nürnberg");
        gr.addEdge(3,1, 94300, 130, "A9", "Ingolstadt");
        gr.addEdge(0,2, 76600, 130, "A92", "Landshut");
        gr.addEdge(2,0, 76600, 130, "A92", "München");
        gr.addEdge(2,3, 159000, 130, "B15n, A3", "Nürnberg");
        gr.addEdge(3,2, 159000, 130, "A3, B15n", "Landshut");
    }

    static void addSomeNodes(Graph gr){
        Node muenchen = new Node(0, true, 11.560985, 48.140235);
        Node ingolstadt = new Node(1, true, 11.425247, 48.765942);
        Node landshut = new Node(2, true, 12.142666, 48.547067);
        Node nuernberg = new Node(3, true, 11.078967, 49.446800);

        gr.addNodesSorted(muenchen);
        gr.addNodesSorted(ingolstadt);
        gr.addNodesSorted(landshut);
        gr.addNodesSorted(nuernberg);
    }

    static void printAllConnectionsOfAllNodes(Graph gr){

        for(int i = 0; i < gr.getAmountOfNodes(); i++)
            printAllConnectionsOfNode(i, gr);
    }

    static void printAllConnectionsOfNode(int id, Graph gr){
        ArrayList<Connection> connectionList = gr.getAllConnectionsOfNode(id);
        System.out.println("Vom Knoten mit der ID " + id + " gehen folgende Verbindungen aus:");
        for (Connection con: connectionList) {
            System.out.println("ZielID: " + con.destination + " Länge: " + con.length + " Straßenname: " + con.name);
        }
    }
}
