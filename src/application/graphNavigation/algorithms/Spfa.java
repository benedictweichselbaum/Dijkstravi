package application.graphNavigation.algorithms;

import application.graphNavigation.graph.Connection;
import application.graphNavigation.graph.Graph;

import java.util.*;

public class Spfa extends NavigationService {

    private static final int INFINITE = 2000000000;
    private ArrayList<Integer> dist;
    private ArrayList<Integer> predecessor;

    @Override
    public Stack<Integer> calculateShortestWay(Graph g, int startNode, int targetNode) {

        int amountOfNodes = g.getAmountOfNodes();
        double progressUnit = (0.2/(amountOfNodes))/100;
        dist = new ArrayList<>(amountOfNodes);
        predecessor = new ArrayList<>(amountOfNodes);

        for(int i = 0; i < amountOfNodes; i++){
            dist.add(INFINITE);
            predecessor.add(INFINITE);
        }
        dist.set(startNode, 0);

        Queue<Integer> queue = new LinkedList<>();
        queue.add(startNode);

        int z = 0;
        boolean t = false;
        while(queue.size() > 0) {
            z++;
            progress += progressUnit;
            if(progress >= 1.0 && !t) {
                System.out.println("progress");
                t = true;
            }
            Integer u = queue.poll();
            for(Connection con : g.getAllConnectionsOfNode(u)) {
                if(dist.get(u) + con.getLength() < dist.get(con.getAim())){
                    dist.set(con.getAim(), dist.get(u) + con.getLength());
                    predecessor.set(con.getAim(),u);

                    if(!queue.contains(con.getAim())){
                        queue.add(con.getAim());

                    }
                }
            }
        }

        progress = 1.0;

        System.out.println(z + " Durchläufe");

        Stack<Integer> path = new Stack<>();

        int aktkn = targetNode;

        do{
            path.add(predecessor.get(aktkn));
            aktkn = predecessor.get(aktkn);
        }while(predecessor.get(aktkn) != startNode && dist.get(aktkn) < INFINITE);

        return path;
    }
}
