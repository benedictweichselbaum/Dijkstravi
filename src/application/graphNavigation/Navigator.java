package application.graphNavigation;

interface Navigator {

    int INFINITE = Integer.MAX_VALUE;

    void calculateShortestWay(Graph g, long startNode, long targetNode);
}
