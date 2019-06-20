package application.graphNavigation;

public class NavigatorAlgorithmsTests {

    public static void main(String... args) {

        //Graph aus Uhl's PP

        Node node1 = new Node(1, true, 12, 13);
        Node node2 = new Node(2, true, 12, 13);
        Node node3 = new Node(3, true, 12, 13);
        Node node4 = new Node(4, true, 12, 13);
        Node node5 = new Node(5, true, 12, 13);
        Node node6 = new Node(6, true, 12, 13);
        Node node7 = new Node(7, true, 12, 13);
        Node node8 = new Node(8, true, 12, 13);
        Node node9 = new Node(9, true, 12, 13);

        Graph graph = new Graph(9);

        graph.addNode(node1);
        graph.addNode(node2);
        graph.addNode(node3);
        graph.addNode(node4);
        graph.addNode(node5);
        graph.addNode(node6);
        graph.addNode(node7);
        graph.addNode(node8);
        graph.addNode(node9);

        graph.addEdge(1, 2, 2);

        //irgendwelche Kanten nicht von Uhl
        graph.addEdge(1, 7, 15);
        graph.addEdge(1, 6, 9);
        graph.addEdge(2, 1, 2);
        graph.addEdge(2, 7, 6);
        graph.addEdge(2, 3, 4);
        graph.addEdge(3, 2, 4);
        graph.addEdge(3, 9, 15);
        graph.addEdge(3, 4, 2);
        graph.addEdge(4, 3, 2);
        graph.addEdge(4, 9, 1);
        graph.addEdge(4, 5, 1);
        graph.addEdge(5, 4, 1);
        graph.addEdge(5, 8, 3);
        graph.addEdge(5, 6, 6);
        graph.addEdge(6, 5, 6);
        graph.addEdge(6, 8, 11);
        graph.addEdge(6, 1, 9);
        graph.addEdge(7, 1, 15);
        graph.addEdge(7, 2, 6);
        graph.addEdge(7, 8, 15);
        graph.addEdge(7, 9, 2);
        graph.addEdge(8, 5, 3);
        graph.addEdge(8, 6, 11);
        graph.addEdge(8, 7, 15);
        graph.addEdge(8, 9, 4);
        graph.addEdge(9, 3, 15);
        graph.addEdge(9, 4, 1);
        graph.addEdge(9, 7, 2);
        graph.addEdge(9, 8, 4);

        graph.printOutMatrix();

        Navigator navigator = new BellmanFord();
        navigator.calculateShortestWay(graph, 1, 5);
    }
}
