package application.graphNavigation.algorithms;

import application.graphNavigation.directionGiver.DirectionGiver;
import application.graphNavigation.graph.Graph;

import java.util.Stack;

public abstract class NavigationService {

    int INFINITE = Integer.MAX_VALUE;

    Double progress = 0.0;

    public abstract Stack<Integer> calculateShortestWay(Graph g, int startNode, int targetNode);

    public String directions(Graph g, Stack<Integer> way) {
        return new DirectionGiver().directions(g, way);
    }

    public Double getProgress() {
        return progress;
    }
}
