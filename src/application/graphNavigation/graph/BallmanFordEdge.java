package application.graphNavigation.graph;

//needed for BellmanFord
class BallmanFordEdge {

    private int src;
    private int dest;
    private int weight;

    BallmanFordEdge(int src, int dest, int weight){
        this.src=src;
        this.dest=dest;
        this.weight=weight;
    }
}
