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

        Node node10 = new Node(10, true, 12, 13);
        Node node11 = new Node(11, true, 12, 13);
        Node node12 = new Node(12, true, 12, 13);
        Node node13 = new Node(13, true, 12, 13);

        Graph graph = new Graph(13);

        graph.addNode(node1);
        graph.addNode(node2);
        graph.addNode(node3);
        graph.addNode(node4);
        graph.addNode(node5);
        graph.addNode(node6);
        graph.addNode(node7);
        graph.addNode(node8);
        graph.addNode(node9);

        graph.addNode(node10);
        graph.addNode(node11);
        graph.addNode(node12);
        graph.addNode(node13);

        graph.addEdge(1, 2, 2);
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

        //irgendwelche Kanten nicht von Uhl
        graph.addEdge(1, 10, 1);
        graph.addEdge(10, 12, 2);
        graph.addEdge(12, 11, 4);

        graph.printOutMartrix();

        System.out.println("  ");

        System.out.println("BellmanFordigator:");
        Navigator navigator = new BellmanFord();
        navigator.calculateShortestWay(graph, 1, 11);


        System.out.println("  ");

        System.out.println("Dijkstrvigator:");
        Navigator dijkstrvigator = new DijkstraOrAStar("Dijkstra");
        dijkstrvigator.calculateShortestWay(graph, 1, 4);


        System.out.println("  ");
        System.out.println("  ");

        Node muenchen = new Node(1, true, 48.140235, 11.560985);
        Node ingolstadt = new Node(2, true, 48.765942, 11.425247);
        Node landshut = new Node(3, true, 48.547067, 12.142666);
        Node nuernberg = new Node(4, true, 49.446800, 11.078967);

        Graph gRoad = new Graph(4);

        gRoad.addNode(muenchen);
        gRoad.addNode(ingolstadt);
        gRoad.addNode(landshut);
        gRoad.addNode(nuernberg);

        gRoad.addEdge(1, 2, 84100);
        gRoad.addEdge(2, 1, 84100);
        gRoad.addEdge(2, 4, 94300);
        gRoad.addEdge(4, 2, 94300);
        gRoad.addEdge(1, 3, 76600);
        gRoad.addEdge(3, 1, 76600);
        gRoad.addEdge(3, 4, 159000);
        gRoad.addEdge(4, 3, 159000);

        gRoad.printOutMartrix();

        System.out.println("AStarigator:");
        Navigator aStarigator = new DijkstraOrAStar("AStar");
        aStarigator.calculateShortestWay(gRoad, 1, 4);
    }
}
