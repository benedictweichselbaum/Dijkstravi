package application.graphNavigation;

import java.util.LinkedList;
import java.util.List;

public class Graph {

    int[][] autobahn;
    Node[] nodes;
    private List<Edge> edges;
    private int numberOfAddedNodes;
    private int numberOfAddedEdges;

    public Graph(int numberOfNodes) {
        this.autobahn = new int[numberOfNodes][numberOfNodes];
        this.nodes = new Node[numberOfNodes];
        edges=new LinkedList<>();
        this.numberOfAddedNodes = 0;
    }

    Edge[] getEdges(){
        return edges.toArray(Edge[]::new);
    }

    void addNode(Node node){
        if(nodes.length > numberOfAddedNodes){
            if(getMatrixNodeNumberById(node.getId()) == -1){
                nodes[numberOfAddedNodes] = node;
                autobahn[numberOfAddedNodes][numberOfAddedNodes] = 0;
                for(int i = 0; i < numberOfAddedNodes; i++){
                    autobahn[i][numberOfAddedNodes] = -1;
                    autobahn[numberOfAddedNodes][i] = -1;
                }
                numberOfAddedNodes++;
            }
        }
    }

    //we iterate twice throw the hole matrix for each edge, is this really clever? I will fix it later if possible
    int getMatrixNodeNumberById(int id){
        int pruefer = -1;
        for(int i = 0; i < numberOfAddedNodes; i++){
            if(nodes[i] == null){

            }else if(nodes[i].getId() == id){
                pruefer = i;
            }
        }
        return pruefer;
    }

    //A list of edges is needed for the Bellmann-Ford algorithm -> class Edge is needed apart from adjazenzmatrix
    void addEdge(int from, int to, int weight){
        int fromNodeMatrixNumber, toNodeMatrixNumber;

        fromNodeMatrixNumber = getMatrixNodeNumberById(from);
        toNodeMatrixNumber = getMatrixNodeNumberById(to);

        if(fromNodeMatrixNumber != -1 && toNodeMatrixNumber != -1 && fromNodeMatrixNumber != toNodeMatrixNumber){
            autobahn[fromNodeMatrixNumber][toNodeMatrixNumber] = weight;
            Edge edge=new Edge(fromNodeMatrixNumber,toNodeMatrixNumber,weight);
            edges.add(edge);
        }
    }
    
    void printOutMatrix(){
        int width = 4;
        System.out.print("    ");
        for(int i = 0; i < numberOfAddedNodes; i++)
            System.out.print(nodes[i].getId() + "   ");

        System.out.println();

        for(int i = 0; i < numberOfAddedNodes; i++){
            System.out.print(nodes[i].getId() + "   ");
            for(int j = 0; j < numberOfAddedNodes; j++)
                if(autobahn[i][j] != -1)
                    System.out.print((autobahn[i][j]+"   ").substring(0, width));
                else
                    System.out.print("    ");
            System.out.println();
        }
    }
}
