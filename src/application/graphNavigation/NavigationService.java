package application.graphNavigation;

import java.util.Stack;

public abstract class NavigationService {

    int INFINITE = Integer.MAX_VALUE;

    public abstract Stack<Integer> calculateShortestWay(Graph g, int startNode, int targetNode);

    public String directions(Graph g, Stack<Integer> way) {
        return new Directions().directions(g, way);
    }

}
