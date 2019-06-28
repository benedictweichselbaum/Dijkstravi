
package application.graphNavigation;

import java.util.*;

class DijkstraOrAStar implements Navigator{

    ArrayList<Node> autobahn = new ArrayList<>();
    HashMap<Integer, ArrayList<Connection>> links = new HashMap<>();

    /*private Node[] nodes;
    private int[][] autobahn;*/

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


    private void init(Graph g, int startNodeId, int targetNodeId) {
        /*nodes = g.getNodes();
        autobahn = g.getAutobahn();*/
        autobahn = g.autobahn;
        links = g.links;

        startNode = startNodeId;
        targetNode = targetNodeId;

        numberOfNodes = g.getAmountOfNodes();
        visited = new boolean[numberOfNodes];
        distance = new int[numberOfNodes];
        predecessor = new int[numberOfNodes];

        // die rot markierten Knoten -> PP
        nodeAndDistance = new HashMap<>();
        nodeAndDistance.put(startNode, 0);
    }

    public void calculateShortestWay(Graph g, int startNodeId, int targetNodeId){

        int nodeNumber;

        init(g, startNodeId, targetNodeId);

        double latTargetNode = autobahn.get(targetNode).getLatitude();
        double lngTargetNode = autobahn.get(targetNode).getLongitude();

        for (int i = 0; i < numberOfNodes; i++) {
            visited[i] = false;
            distance[i] = INFINITE;
        }
        distance[startNode] = 0;
        predecessor[startNode] = startNode;

        // wiederhole bis alle Knoten besucht sind / Zielknoten besucht ist
        for (int i = 0; i < numberOfNodes; i++)
        {
            nodeNumber = getPositionOfUnvisitedNodeWithShortestDistance(nodeAndDistance);

            System.out.println("Knoten mit min. Distanz: " + nodeNumber);
            //um nicht zu allen Knoten den kürzesten Weg vom Startknoten aus zu berechnen
            if(nodeNumber == targetNode){
                break;
            }
            visited[nodeNumber] = true;
            nodeAndDistance.remove(nodeNumber);


            int newDistance;
            int predictedDistance;
            //falls Nachbarknoten noch nicht besucht
            ArrayList<Connection> allConnectionsOfNode = g.getAllConnectionsOfNode(nodeNumber);
            for (Connection connectionToNeighbor : allConnectionsOfNode){
                int neighboringNode = connectionToNeighbor.aim;
                if ((!visited[neighboringNode]))
                {
                    int distanceToNeighbor = connectionToNeighbor.length;
                    if(typeOfAlgorithm.equals("Dijkstra")) {
                        predictedDistance = 0;
                    }
                    else{
                        //predictedDistance: prognostizierte Distanz vom Nachbarknoten zum Zielknoten
                        predictedDistance = DistanceCalculator.distance(latTargetNode, lngTargetNode, autobahn.get(neighboringNode).getLatitude(), autobahn.get(neighboringNode).getLongitude());
                        System.out.println("Luftlinie von Knoten " + neighboringNode + " bis Zielknoten: " + predictedDistance);
                    }
                    newDistance = distance[nodeNumber] + distanceToNeighbor;

                    if (newDistance < distance[neighboringNode])
                    {
                        distance[neighboringNode] = newDistance;
                        predecessor[neighboringNode] = nodeNumber;

                        nodeAndDistance.put(neighboringNode, (newDistance + predictedDistance));

                        System.out.println("von " + nodeNumber + " zu " + neighboringNode + " neue kuerzeste Distanz: " + newDistance);
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
        pfad = String.valueOf(targetNode);
        nodeNumber = targetNode;
        while (nodeNumber != startNode)
        {
            nodeNumber = predecessor[nodeNumber];
            pfad = nodeNumber + "/" + pfad;
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

