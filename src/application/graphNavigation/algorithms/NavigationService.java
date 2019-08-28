package application.graphNavigation.algorithms;

import application.Mathematics.MathematicOperations;
import application.graphNavigation.directionGiver.DirectionGiver;
import application.graphNavigation.graph.Connection;
import application.graphNavigation.graph.Graph;

import java.util.ArrayList;
import java.util.Stack;

public abstract class NavigationService {

    int INFINITE = Integer.MAX_VALUE;
    ArrayList<Integer> dist;
    ArrayList<Integer> predecessor;
    boolean fastestPath = true;
    int personalMaxSpeed;

    Double progress = 0.0;

    public Stack<Integer> initCalculateShortestWay(Graph g, int startNode, int targetNode, int maxSpeed, boolean fastestPath){
        this.fastestPath = fastestPath;
        this.personalMaxSpeed = maxSpeed;
        return calculateShortestWay(g, startNode, targetNode);
    }
    public abstract Stack<Integer> calculateShortestWay(Graph g, int startNode, int targetNode);

    String directions(Graph g, Stack<Integer> way, int maxSpeed, boolean fastestPath) {
        this.fastestPath = fastestPath;
        this.personalMaxSpeed = maxSpeed;
        return new DirectionGiver().directions(g, way, personalMaxSpeed);
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

    int getDistance(Connection con){

       // System.out.println(fastestPath + "gD fP");
        if(fastestPath) {
            //System.out.println(Math.round(MathematicOperations.calculateTimeForConnection(con, personalMaxSpeed)) + " " + personalMaxSpeed);
            return (int) Math.round(MathematicOperations.calculateTimeForConnection(con, personalMaxSpeed));
        }
        else
            return con.getLength();
    }
}
