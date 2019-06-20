package application.graphNavigation;

import java.util.ArrayList;

public class Graph2Test {

    Graph2 g2;
    public static void main(String... args) {
        Graph2 g2 = new Graph2();
        addSomeNodes(g2);
        insertEdge(g2);
        printAllConnectionsOfNode(1, g2);
        printAllConnectionsOfNode(2, g2);
        printAllConnectionsOfNode(3, g2);
    }

    static void insertEdge(Graph2 g2){
        g2.addEdge(2,3, 12);
        g2.addEdge(3,1, 1);
        g2.addEdge(2,4, 2);
    }

    static void addSomeNodes(Graph2 g2){
        Node2 node0 = new Node2(0, false, 12, 13);
        Node2 node1 = new Node2(1, false, 12, 13);
        Node2 node2 = new Node2(2, false, 12, 13);
        Node2 node3 = new Node2(3, false, 12, 13);
        Node2 node4 = new Node2(4, false, 12, 13);
        Node2 node5 = new Node2(5, false, 12, 13);
        Node2 node6 = new Node2(6, false, 12, 13);

        g2.addNodesSorted(node0);
        g2.addNodesSorted(node1);
        g2.addNodesSorted(node2);
        g2.addNodesSorted(node3);
        g2.addNodesSorted(node4);
        g2.addNodesSorted(node5);
        g2.addNodesSorted(node6);
    }

    static void printAllConnectionsOfNode(int id, Graph2 g2){
        ArrayList<Connection> connectionList = g2.getAllConnectionsOfNode(id);
        System.out.println("Vom Knoten mit der ID " + id + " gehen folgende Verbindungen aus:");
        for (Connection con: connectionList) {
            System.out.println("ZielID: " + con.destination + " Länge: " + con.length + " Straßenname: " + con.name);
        }
    }
}
