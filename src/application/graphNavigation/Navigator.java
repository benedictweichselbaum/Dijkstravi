package application.graphNavigation;

public interface Navigator {
    void calculateShortestWay(Graph g, long startNode, long targetNode);
}
