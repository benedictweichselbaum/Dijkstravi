package application.graphNavigation.algorithms;

import application.graphNavigation.graph.Connection;
import application.graphNavigation.graph.Graph;

import java.util.Stack;

public class BellmanFordFast extends AbstractAlgorithm {

    @Override
    public Stack<Integer> calculateShortestWay(Graph g, int startNode, int targetNode) {
        int amountOfNodes = g.getAmountOfNodes();
        initArrays(amountOfNodes);
        dist.set(startNode, 0);

        boolean change = true;
        double progressUnit = 1.0/(amountOfNodes);
        for(int i = 1; i < amountOfNodes; i++){
            for(int k = 0; k < amountOfNodes; k++){
                for(Connection con : g.getAllConnectionsOfNode(k)) {
                    if(dist.get(k) < INFINITE && dist.get(k) + getDistance(con) < dist.get(con.getAim())){
                        dist.set(con.getAim(), dist.get(k) + getDistance(con));
                        predecessor.set(con.getAim(),k);
                        change = true;
                    }
                }
            }
            progress += progressUnit;
            if(!change)
                i = amountOfNodes;
            change = false;
        }

        return giveWay(startNode, targetNode, g);
    }
}