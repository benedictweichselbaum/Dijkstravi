package application.graphNavigation.algorithms;

import application.DijkstraviController;
import application.unitConverter.UnitConverter;
import application.graphNavigation.directionsCreator.DirectionsCreator;
import application.graphNavigation.graph.Connection;
import application.graphNavigation.graph.Graph;

import java.util.ArrayList;
import java.util.Stack;
/**
 * Abstract superclass of the algorithms.
 */
public abstract class AbstractAlgorithm {

    int INFINITE = Integer.MAX_VALUE;
    ArrayList<Integer> dist;
    ArrayList<Integer> predecessor;
    private boolean fastestPath = true;
    private int personalMaxSpeed;

    Double progress = 0.0;

    public Stack<Integer> initCalculateShortestWay(Graph g, int startNode, int targetNode, int maxSpeed, boolean fastestPath){
        this.fastestPath = fastestPath;
        personalMaxSpeed = maxSpeed;
        return calculateShortestWay(g, startNode, targetNode);
    }
    public abstract Stack<Integer> calculateShortestWay(Graph g, int startNode, int targetNode);

    String directions(Graph g, Stack<Integer> way, DijkstraviController dijkstraviController) {
        return new DirectionsCreator().directions(g, way, personalMaxSpeed, dijkstraviController);
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

    Stack<Integer> giveWay(int startNode, int targetNode, Graph g){
        Stack<Integer> path = new Stack<>();
        if(!g.getNodeById(targetNode).getName().equals("HelperNode")){
            path.add(targetNode);
        }
        int aktkn = targetNode;

        do{
            path.add(predecessor.get(aktkn));
            aktkn = predecessor.get(aktkn);
        }while(predecessor.get(aktkn) != startNode && dist.get(aktkn) < INFINITE);

        if(!g.getNodeById(startNode).getName().equals("HelperNode")){
            path.add(startNode);
        }
        return path;
    }

    int getDistance(Connection con){
        if(fastestPath) {
            return (int) Math.round(UnitConverter.calculateTimeForConnection(con, personalMaxSpeed));
        }
        else
            return con.getLength();
    }

    boolean isFastestPath() {
        return fastestPath;
    }

    int getPersonalMaxSpeed() {
        return personalMaxSpeed;
    }
}
