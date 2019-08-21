package application.graphNavigation.algorithms;

import application.graphNavigation.graph.Connection;
import application.graphNavigation.graph.Graph;

import java.util.ArrayList;
import java.util.Stack;

public class bff extends NavigationService {

    ArrayList<bfnode> bfnodeList;

    @Override
    public Stack<Integer> calculateShortestWay(Graph g, int startNode, int targetNode) {

        bfnodeList = new ArrayList<>();

        for(int i = 0; i < g.getAmountOfNodes(); i++){
            bfnodeList.add(new bfnode());
        }
        bfnodeList.get(startNode).dist = 0;
        System.out.println(g.getAmountOfNodes() + " Knoten geklont");

        for(int i = 1; i < g.getAmountOfNodes(); i++){
            //for (bfnode nod: bfnodeList) {
            for(int j = 0; j < g.getAmountOfNodes(); j++){
                for(Connection con : g.getAllConnectionsOfNode(j)) {
                    if(bfnodeList.get(j).dist < INFINITE && bfnodeList.get(j).dist + con.getLength() < bfnodeList.get(con.getAim()).dist){
                        bfnode tempnode = bfnodeList.get(con.getAim());
                        tempnode.dist = bfnodeList.get(j).dist + con.getLength();
                        tempnode.predecessor = j;
                        //System.out.println("Neue Entfernung für ID " + con.getAim() + ": " + tempnode.dist);
                        bfnodeList.set(con.getAim(), tempnode);
                    }
                }
            }
            if(i % 5000 == 0)
                System.out.println("Bei Durchgang " + i);
        }

        System.out.println("Fertig mit Wegberechnung. Entfernung: " + bfnodeList.get(targetNode).dist);

        Stack<Integer> path = new Stack<>();

        int aktkn = targetNode;

        do{
            path.add(bfnodeList.get(aktkn).predecessor);
            aktkn = bfnodeList.get(aktkn).predecessor;
        }while(bfnodeList.get(targetNode).predecessor != startNode && bfnodeList.get(aktkn).dist < INFINITE);

        return null;
    }
}
