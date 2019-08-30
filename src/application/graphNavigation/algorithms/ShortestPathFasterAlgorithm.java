package application.graphNavigation.algorithms;

import application.graphNavigation.graph.Connection;
import application.graphNavigation.graph.Graph;

import java.util.*;

public class ShortestPathFasterAlgorithm extends AbstractAlgorithm {

    @Override
    public Stack<Integer> calculateShortestWay(Graph g, int startNode, int targetNode) {

        int amountOfNodes = g.getAmountOfNodes();
        double progressUnit = (0.2/(amountOfNodes))/100;
        initArrays(amountOfNodes);
        dist.set(startNode, 0);

        Queue<Integer> queue = new LinkedList<>();
        queue.add(startNode);

        while(queue.size() > 0) {
            progress += progressUnit;
            Integer u = queue.poll();
            for(Connection con : g.getAllConnectionsOfNode(u)) {
                if(dist.get(u) + getDistance(con) < dist.get(con.getAim())){
                    dist.set(con.getAim(), dist.get(u) + getDistance(con));
                    predecessor.set(con.getAim(),u);

                    if(!queue.contains(con.getAim())){
                        queue.add(con.getAim());

                    }
                }
            }
        }
        progress = 1.0;

        return giveWay(startNode, targetNode, g);
    }
}
