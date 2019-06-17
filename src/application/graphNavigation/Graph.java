package application.graphNavigation;

public class Graph {

    private int[][] autobahn;
    private Node[] nodes;
    private int numberOfAddedNodes;

    public Graph(int numberOfNodes) {
        this.autobahn = new int[numberOfNodes][numberOfNodes];
        this.nodes = new Node[numberOfNodes];
        this.numberOfAddedNodes = 0;
    }

    int getMatrixNodeNumber(int id){
        int pruefer = -1;
        for(int i = 0; i < numberOfAddedNodes; i++){
            if(nodes[i] == null){

            }else if(nodes[i].getId() == id){
                pruefer = i;
            }
        }
        return pruefer;
    }

    void addNode(Node node){
        if(nodes.length > numberOfAddedNodes){
            if(getMatrixNodeNumber(node.getId()) == -1){
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
    void addEdge(Node from, Node to, int weight){
        int fromNodeMatrixNumber, toNodeMatrixNumber;

        fromNodeMatrixNumber = getMatrixNodeNumber(from.getId());
        toNodeMatrixNumber = getMatrixNodeNumber(to.getId());

        if(fromNodeMatrixNumber != -1 && toNodeMatrixNumber != -1 && fromNodeMatrixNumber != toNodeMatrixNumber){
            autobahn[fromNodeMatrixNumber][toNodeMatrixNumber] = weight;
        }
    }

    void ausgeben(){
        int breite = 4;
        System.out.print("    ");
        for(int i = 0; i < numberOfAddedNodes; i++)
            System.out.print(nodes[i].getId() + "   ");

        System.out.println();

        for(int i = 0; i < numberOfAddedNodes; i++){
            System.out.print(nodes[i].getId() + "   ");
            for(int j = 0; j < numberOfAddedNodes; j++)
                if(autobahn[i][j] != -1)
                    System.out.print((autobahn[i][j]+"   ").substring(0, breite));
                else
                    System.out.print("    ");
            System.out.println();
        }
    }
}
