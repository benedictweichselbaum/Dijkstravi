package application.graphNavigation;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

class Graph {

    private int[][] autobahn;
    private Node[] nodes;
    private List<Edge> edges;
    private int numberOfAddedNodes;
    private final int INFINITE=Integer.MAX_VALUE;

    Graph(int numberOfNodes) {
        this.autobahn = new int[numberOfNodes][numberOfNodes];
        this.nodes = new Node[numberOfNodes];
        edges=new LinkedList<>();
        this.numberOfAddedNodes=0;
    }


    int getMatrixNodeNumberById(long id){
        int pruefer = INFINITE;
        for(int i = 0; i < numberOfAddedNodes; i++){
            if(nodes[i] == null){

            }else if(nodes[i].getId() == id){
                pruefer = i;
            }
        }
        return pruefer;
    }

    /*
    * read out the Id of the NodeNumber in the matrix, not working, but is needed later
    long getIdByMatrixNodeNumber(int nodeNumber){
        long pruefer = INFINITE;
        for(int i = 0; i < numberOfAddedNodes; i++){
            if(nodes[i] == null){

            }else if(nodes[i].getId() == id){
                pruefer = i;
            }
        }
        return pruefer;
    }*/

    void addNode(Node node){
        if(nodes.length > numberOfAddedNodes){
            if(getMatrixNodeNumberById(node.getId()) == INFINITE){
                nodes[numberOfAddedNodes] = node;
                autobahn[numberOfAddedNodes][numberOfAddedNodes] = 0;
                for(int i = 0; i < numberOfAddedNodes; i++){
                    autobahn[i][numberOfAddedNodes] = INFINITE;
                    autobahn[numberOfAddedNodes][i] = INFINITE;
                }
                numberOfAddedNodes++;
            }
        }
    }
    void addEdge(int from, int to, int weight){
        int fromNodeMatrixNumber, toNodeMatrixNumber;

        fromNodeMatrixNumber = getMatrixNodeNumberById(from);
        toNodeMatrixNumber = getMatrixNodeNumberById(to);

        if(fromNodeMatrixNumber != INFINITE && toNodeMatrixNumber != INFINITE && fromNodeMatrixNumber != toNodeMatrixNumber){
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
                if(autobahn[i][j] != INFINITE)
                    System.out.print((autobahn[i][j]+"   ").substring(0, width));
                else
                    System.out.print("    ");
            System.out.println();
        }
    }

    int[][] getAutobahn(){
        return Arrays.stream(autobahn).map(int[]::clone).toArray(int[][]::new);
    }

    Node[] getNodes(){
        return Arrays.copyOf(nodes, nodes.length);
    }

    Edge[] getEdges() {
        return edges.toArray(Edge[]::new);
    }
}
