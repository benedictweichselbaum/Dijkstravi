package application.graphNavigation;

import java.util.*;

public class DijkstraOrAStar implements Navigator{

    private Node[] nodes;
    private int[][] autobahn;

    private int startNode;
    private int targetNode;

    private String typeOfAlgorithm;
    private int numberOfNodes;
    private boolean[] visited;
    private int[] distance;
    private int[] predecessor;
    private Map<Integer, Integer> nodeAndDistance;

    public DijkstraOrAStar(String typeOfAlgorithm){
        //Dijkstra
        //AStar
        this.typeOfAlgorithm = typeOfAlgorithm;
    }


    private void init(Graph g, long startNodeId, long targetNodeId) {
        nodes = g.nodes;
        autobahn = g.autobahn;

        startNode = g.getMatrixNodeNumberById(startNodeId);
        targetNode = g.getMatrixNodeNumberById(targetNodeId);

        numberOfNodes = nodes.length;
        visited = new boolean[numberOfNodes];
        distance = new int[numberOfNodes];
        predecessor = new int[numberOfNodes];

        // die rot markierten Knoten -> PP
        nodeAndDistance = new HashMap<>();
        nodeAndDistance.put(startNode, 0);
    }

    public void calculateShortestWay(Graph g, long startNodeId, long targetNodeId){

        int nodeNumber;

        init(g, startNodeId, targetNodeId);

        double latTargetNode = nodes[targetNode].latitude;
        double lngTargetNode = nodes[targetNode].longitude;

        for (int i = 0; i < numberOfNodes; i++) {
            visited[i] = false;
            distance[i] = Integer.MAX_VALUE;
        }
        distance[startNode] = 0;
        predecessor[startNode] = startNode;

        // wiederhole bis alle Knoten besucht sind / Zielknoten besucht ist
        for (int i = 0; i < numberOfNodes; i++)
        {
            nodeNumber = getPositionOfUnvisitedNodeWithShortestDistance(nodeAndDistance);

            System.out.println("Knoten mit min. Distanz zu Startknoten: " + nodes[nodeNumber].getId());
            //um nicht zu allen Knoten den kürzesten Weg vom Startknoten aus zu berechnen
            if(nodes[nodeNumber].getId() == nodes[targetNode].getId()){
                break;
            }
            visited[nodeNumber] = true;
            nodeAndDistance.remove(nodeNumber);


            int newDistance;
            int predictedDistance;
            for (int neighboringNode = 0; neighboringNode < numberOfNodes; neighboringNode++)
            {
                if ((autobahn[nodeNumber][neighboringNode] > -1) && (!visited[neighboringNode]))
                {
                    if(typeOfAlgorithm.equals("Dijkstra")) {
                        predictedDistance = 0;
                    }
                    else{
                        //predictedDistance: prognostizierte Distanz vom Nachbarknoten zum Zielknoten
                        predictedDistance = DistanceCalculator.distance(latTargetNode, lngTargetNode, nodes[neighboringNode].latitude, nodes[neighboringNode].longitude);
                        System.out.println("Luftlinie von Knoten " + nodes[neighboringNode].getId() + " bis Zielknoten: " +predictedDistance);
                    }
                    newDistance = distance[nodeNumber] + autobahn[nodeNumber][neighboringNode];

                    if (newDistance < distance[neighboringNode])
                    {
                        distance[neighboringNode] = newDistance;
                        predecessor[neighboringNode] = nodeNumber;

                        nodeAndDistance.put(neighboringNode, (newDistance + predictedDistance));

                        System.out.println("von " + nodes[nodeNumber].getId() + " zu " + nodes[neighboringNode].getId() + " neue kuerzeste Distanz: " + newDistance);
                    }
                }
            }
        }

        output();
    }

    private void output() {
        int nodeNumber;
        System.out.println("Entfernung: " + distance[targetNode]);

        String pfad;
        pfad = String.valueOf(nodes[targetNode].getId());
        nodeNumber = targetNode;
        while (nodeNumber != startNode)
        {
            nodeNumber = predecessor[nodeNumber];
            pfad = nodes[nodeNumber].getId() + "/" + pfad;
        }
        System.out.println("Weg: " + pfad);
    }

    private int getPositionOfUnvisitedNodeWithShortestDistance(Map<Integer, Integer> nodeAndDistance) {

        Map.Entry<Integer, Integer> min = null;
        for (Map.Entry<Integer, Integer> entry : nodeAndDistance.entrySet()) {
            if (min == null || min.getValue() > entry.getValue()) {
                min = entry;
            }
        }
        return min.getKey();
    }
}
