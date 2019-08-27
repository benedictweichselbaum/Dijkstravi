package application.graphNavigation.algorithms;

import application.graphNavigation.directionGiver.DirectionGiver;
import application.graphNavigation.graph.Graph;

import java.util.ArrayList;
import java.util.Stack;

public abstract class NavigationService {

    int INFINITE = Integer.MAX_VALUE;
    ArrayList<Integer> dist;
    ArrayList<Integer> predecessor;

    Double progress = 0.0;

    public abstract Stack<Integer> calculateShortestWay(Graph g, int startNode, int targetNode);

    String directions(Graph g, Stack<Integer> way, int maxSpeed) {
        return new DirectionGiver().directions(g, way, maxSpeed);
    }

    public Double getProgress() {
        return progress;
    }

    void initArrays(int amountOfNodes){
        dist = new ArrayList<>(amountOfNodes);
        predecessor = new ArrayList<>(amountOfNodes);

        for(int i = 0; i < amountOfNodes; i++){
            dist.add(INFINITE);
            predecessor.add(INFINITE);
        }
    }

    Stack<Integer> giveWay(int startNode, int targetNode){
        Stack<Integer> path = new Stack<>();
        int aktkn = targetNode;

        do{
            path.add(predecessor.get(aktkn));
            aktkn = predecessor.get(aktkn);
        }while(predecessor.get(aktkn) != startNode && dist.get(aktkn) < INFINITE);

        return path;
    }
}
