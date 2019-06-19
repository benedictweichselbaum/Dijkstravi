package application.graphNavigation;

public class Graph {

    private int[][] autobahn;
    private Node[] nodes;
    private Node[] motorway_linkNodes;
    private int numberOfAddedNodes;
    private int numberOfAddedMotorwayLinks;

    public Graph(int numberOfNodes, int numberOfMotorway_links) {
        this.autobahn = new int[numberOfNodes][numberOfNodes];
        this.nodes = new Node[numberOfNodes];
        this.motorway_linkNodes = new Node[numberOfMotorway_links];
        this.numberOfAddedNodes = 0;
        this.numberOfAddedMotorwayLinks = 0;
    }

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

    public void addNode(Node node){
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

            if (node.link) {
                motorway_linkNodes[numberOfAddedMotorwayLinks] = node;
                numberOfAddedMotorwayLinks++;
            }
        }
    }
    public void addEdge(int from, int to, int weight){
        int fromNodeMatrixNumber, toNodeMatrixNumber;

        fromNodeMatrixNumber = getMatrixNodeNumberById(from);
        toNodeMatrixNumber = getMatrixNodeNumberById(to);

        if(fromNodeMatrixNumber != -1 && toNodeMatrixNumber != -1 && fromNodeMatrixNumber != toNodeMatrixNumber){
            autobahn[fromNodeMatrixNumber][toNodeMatrixNumber] = weight;
        }
    }

    public void printOutMartrix(){
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
