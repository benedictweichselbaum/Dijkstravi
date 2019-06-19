package application.graphNavigation;

public class GraphTest {

    public static void main(String... args) {
        Node node1 = new Node(1, false, 12, 13);
        Node node2 = new Node(2, false, 12, 13);
        Node node3 = new Node(3, false, 12, 13);
        Node node4 = new Node(4, false, 12, 13);
        Node node5 = new Node(5, false, 12, 13);
        Node node6 = new Node(6, false, 12, 13);

        Graph graph = new Graph(6, 6);

        graph.addNode(node1);
        graph.addNode(node2);
        graph.addNode(node3);
        graph.addNode(node4);
        graph.addNode(node5);
        graph.addNode(node6);

        graph.addEdge(1, 2, 32);

        graph.printOutMartrix();
    }
}
