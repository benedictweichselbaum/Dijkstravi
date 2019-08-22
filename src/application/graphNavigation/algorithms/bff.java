package application.graphNavigation.algorithms;

import application.graphNavigation.graph.Connection;
import application.graphNavigation.graph.Graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class bff extends NavigationService {

    ArrayList<bfnode> bfnodeList;

    HashMap<Integer, Integer> dist;
    HashMap<Integer, Integer> predecessor;

    ArrayList<Integer> dista;
    ArrayList<Integer> predecessora;

    @Override
    public Stack<Integer> calculateShortestWay(Graph g, int startNode, int targetNode) {

        dista = new ArrayList<>(100275);
        predecessora = new ArrayList<>(100275);

        int amountofnodes = g.getAmountOfNodes();

        for(int i = 0; i < amountofnodes; i++){
            dista.add(INFINITE);
            predecessora.add(INFINITE);
        }
        dista.set(startNode, 0);
        System.out.println("Alle Knoten in ArrayLists erstellt.");

        boolean change = true;
        for(int i = 1; i < amountofnodes; i++){
            for(int k = 0; k < amountofnodes; k++){
                for(Connection con : g.getAllConnectionsOfNode(k)) {
                    if(dista.get(k) < INFINITE && dista.get(k) + con.getLength() < dista.get(con.getAim())){
                        dista.set(con.getAim(), dista.get(k) + con.getLength());
                        predecessora.set(con.getAim(),k);
                        change = true;
                    }
                }
            }

            if(!change)
                i = amountofnodes;
            change = false;
        }

        System.out.println("Fertig mit Wegberechnung. Entfernung: " + dista.get(targetNode));

        Stack<Integer> path = new Stack<>();

        int aktkn = targetNode;

        do{
            path.add(predecessora.get(aktkn));
            aktkn = predecessora.get(aktkn);
        }while(predecessora.get(aktkn) != startNode && dista.get(aktkn) < INFINITE);

        return path;
    }

    public Stack<Integer> calculateShortestWayHashMap(Graph g, int startNode, int targetNode) {

        //bfnodeList = new ArrayList<>();

        dist = new HashMap<>(100275);
        predecessor = new HashMap<>(100275);

        for(int i = 0; i < g.getAmountOfNodes(); i++){
            dist.put(i, INFINITE);
            predecessor.put(i, INFINITE);
        }
        dist.put(startNode, 0);
        System.out.println("Alle Knoten in HashMaps erstellt.");

        int amountofnodes = g.getAmountOfNodes();

        for(int i = 1; i < amountofnodes; i++){
            //for (bfnode nod: bfnodeList) {
            for(int j = 0; j < amountofnodes; j++){
                for(Connection con : g.getAllConnectionsOfNode(j)) {
                    if(dist.get(j) < INFINITE && dist.get(j) + con.getLength() < dist.get(con.getAim())){
                        dist.put(i, dist.get(j) + con.getLength());
                        predecessor.put(i,j);
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

        return path;
    }


    public Stack<Integer> calculateShortestWayold(Graph g, int startNode, int targetNode) {

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

        return path;
    }
}
