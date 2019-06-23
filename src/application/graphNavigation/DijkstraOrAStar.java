package application.graphNavigation;

import java.util.*;

class DijkstraOrAStar implements Navigator{

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

    DijkstraOrAStar(String typeOfAlgorithm){
        //Dijkstra
        //AStar
        this.typeOfAlgorithm = typeOfAlgorithm;
    }


    private void init(Graph g, long startNodeId, long targetNodeId) {
        nodes = g.getNodes();
        autobahn = g.getAutobahn();

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

        double latTargetNode = nodes[targetNode].getLatitude();
        double lngTargetNode = nodes[targetNode].getLongitude();

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

            System.out.println("Knoten mit min. Distanz: " + nodes[nodeNumber].getId());
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
                if ((autobahn[nodeNumber][neighboringNode] != INFINITE) && (!visited[neighboringNode]))
                {
                    if(typeOfAlgorithm.equals("Dijkstra")) {
                        predictedDistance = 0;
                    }
                    else{
                        //predictedDistance: prognostizierte Distanz vom Nachbarknoten zum Zielknoten
                        predictedDistance = DistanceCalculator.distance(latTargetNode, lngTargetNode, nodes[neighboringNode].getLatitude(), nodes[neighboringNode].getLongitude());
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
        double totalDistance = ((double)distance[targetNode]/1000);
        System.out.println("Entfernung: " + distance[targetNode] + "m");
        System.out.println("Entfernung: " + totalDistance + "km");

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
        //According to Lint this could cause a NullPointerException, please look after it
        return min.getKey();
    }
}
