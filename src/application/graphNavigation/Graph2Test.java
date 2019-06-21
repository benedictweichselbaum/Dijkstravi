package application.graphNavigation;

import java.util.ArrayList;

public class Graph2Test {

    Graph2 g2;
    public static void main(String... args) {
        Graph2 g2 = new Graph2();
        addSomeNodes(g2);
        insertSomeEdges(g2);
        printAllConnectionsOfAllNodes(g2);
    }

    static void insertSomeEdges(Graph2 g2){
        g2.addEdge(2,3, 12, 130, "A X", "Bsp");
        g2.addEdge(3,1, 1, 130, "A X", "Bsp");
        g2.addEdge(2,4, 2, 130, "A X", "Bsp");
        g2.addEdge(6,4, 5, 130, "A X", "Bsp");
        g2.addEdge(4,3, 6, 130, "A X", "Bsp");
        g2.addEdge(4,6, 9, 130, "A X", "Bsp");
        g2.addEdge(0,6, 6, 130, "A X", "Bsp");
        g2.addEdge(0,4, 15, 130, "A X", "Bsp");
        g2.addEdge(1,2, 32, 130, "A X", "Bsp");
        g2.addEdge(1,4, 6, 130, "A X", "Bsp");
        g2.addEdge(1,3, 3, 130, "A X", "Bsp");
        g2.addEdge(5,1, 5, 130, "A X", "Bsp");
        g2.addEdge(5,6, 15, 130, "A X", "Bsp");
        g2.addEdge(5,4, 23, 130, "A X", "Bsp");
    }

    static void addSomeNodes(Graph2 g2){
        Node2 node0 = new Node2(0, false, 12, 12);
        Node2 node1 = new Node2(1, false, 16, 13);
        Node2 node2 = new Node2(2, false, 14, 14);
        Node2 node3 = new Node2(3, false, 18, 15);
        Node2 node4 = new Node2(4, false, 12, 16);
        Node2 node5 = new Node2(5, false, 18, 17);
        Node2 node6 = new Node2(6, false, 19, 18);

        g2.addNodesSorted(node0);
        g2.addNodesSorted(node1);
        g2.addNodesSorted(node2);
        g2.addNodesSorted(node3);
        g2.addNodesSorted(node4);
        g2.addNodesSorted(node5);
        g2.addNodesSorted(node6);
    }

    static void printAllConnectionsOfAllNodes(Graph2 g2){

        for(int i = 0; i < g2.getAmountOfNodes(); i++)
            printAllConnectionsOfNode(i, g2);
    }

    static void printAllConnectionsOfNode(int id, Graph2 g2){
        ArrayList<Connection> connectionList = g2.getAllConnectionsOfNode(id);
        System.out.println("Vom Knoten mit der ID " + id + " gehen folgende Verbindungen aus:");
        for (Connection con: connectionList) {
            System.out.println("ZielID: " + con.destination + " Länge: " + con.length + " Straßenname: " + con.name);
        }
    }
}
