package application.graphNavigation;

import java.util.ArrayList;

public class GraphTest {

    public static void main(String... args) {
        Graph gr = new Graph();
        addSomeNodes(gr);
        insertSomeEdges(gr);
        printAllConnectionsOfAllNodes(gr);
    }

    static void insertSomeEdges(Graph gr){
        gr.addEdge(2,3, 12, 130, "A X", "Bsp");
        gr.addEdge(3,1, 1, 130, "A X", "Bsp");
        gr.addEdge(2,4, 2, 130, "A X", "Bsp");
        gr.addEdge(6,4, 5, 130, "A X", "Bsp");
        gr.addEdge(4,3, 6, 130, "A X", "Bsp");
        gr.addEdge(4,6, 9, 130, "A X", "Bsp");
        gr.addEdge(0,6, 6, 130, "A X", "Bsp");
        gr.addEdge(0,4, 15, 130, "A X", "Bsp");
        gr.addEdge(1,2, 32, 130, "A X", "Bsp");
        gr.addEdge(1,4, 6, 130, "A X", "Bsp");
        gr.addEdge(1,3, 3, 130, "A X", "Bsp");
        gr.addEdge(5,1, 5, 130, "A X", "Bsp");
        gr.addEdge(5,6, 15, 130, "A X", "Bsp");
        gr.addEdge(5,4, 23, 130, "A X", "Bsp");
    }

    static void addSomeNodes(Graph gr){
        /*
        Node node0 = new Node(0, false, 12, 12);
        Node node1 = new Node(1, false, 16, 13);
        Node node2 = new Node(2, false, 14, 14);
        Node node3 = new Node(3, false, 18, 15);
        Node node4 = new Node(4, false, 12, 16);
        Node node5 = new Node(5, false, 18, 17);
        Node node6 = new Node(6, false, 19, 18);

        gr.addNodesSorted(node0);
        gr.addNodesSorted(node1);
        gr.addNodesSorted(node2);
        gr.addNodesSorted(node3);
        gr.addNodesSorted(node4);
        gr.addNodesSorted(node5);
        gr.addNodesSorted(node6);

         */
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
