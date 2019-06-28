package application.graphNavigation;

import java.util.Stack;

abstract class Navigator {

    int INFINITE = Integer.MAX_VALUE;

    public abstract Stack<Integer> calculateShortestWay(Graph g, int startNode, int targetNode);

    public String directions(Stack<Integer> way){
        return "";
    }
}
