
package application.graphNavigation;

import java.lang.reflect.Array;
import java.util.*;

class DijkstraOrAStar extends Navigator{

    ArrayList<Node> autobahn = new ArrayList<>();
    HashMap<Integer, ArrayList<Connection>> links = new HashMap<>();

    private int startNode;
    private int targetNode;

    private String typeOfAlgorithm;
    private int numberOfNodes;
    private Set<Integer> visited;
    private Map<Integer, Integer> distance;
    private Map<Integer, Integer> predecessor;
    private Map<Integer, Integer> nodeAndDistance;

    DijkstraOrAStar(String typeOfAlgorithm){
        //Dijkstra
        //AStar
        this.typeOfAlgorithm = typeOfAlgorithm;
    }


    private void init(Graph g, int startNodeId, int targetNodeId) {
        autobahn = g.autobahn;
        links = g.links;

        startNode = startNodeId;
        targetNode = targetNodeId;

        numberOfNodes = g.getAmountOfNodes();
        visited = new HashSet<>();
        distance = new HashMap<>();
        predecessor = new HashMap<>();

        // die rot markierten Knoten -> PP
        nodeAndDistance = new HashMap<>();
        nodeAndDistance.put(startNode, 0);
    }

    public Stack<Integer> calculateShortestWay(Graph g, int startNodeId, int targetNodeId){

        int nodeNumber;

        init(g, startNodeId, targetNodeId);

        double latTargetNode = autobahn.get(targetNode).getLatitude();
        double lngTargetNode = autobahn.get(targetNode).getLongitude();

        for (int i = 0; i < numberOfNodes; i++) {
            distance.put(i, INFINITE);
        }
        distance.put(startNode, 0);
        predecessor.put(startNode, startNode);

        // wiederhole bis alle Knoten besucht sind / Zielknoten besucht ist
        for (int i = 0; i < numberOfNodes; i++)
        {
            if(nodeAndDistance == null){
                break;
            }
            nodeNumber = getPositionOfUnvisitedNodeWithShortestDistance(nodeAndDistance);
            visited.add(nodeNumber);
            nodeAndDistance.remove(nodeNumber);

            System.out.println("Knoten mit min. Distanz: " + nodeNumber);
            //um nicht zu allen Knoten den kürzesten Weg vom Startknoten aus zu berechnen
            if(nodeNumber == targetNode){
                break;
            }


            int newDistance;
            int predictedDistance;
            //falls Nachbarknoten noch nicht besucht
            ArrayList<Connection> allConnectionsOfNode = g.getAllConnectionsOfNode(nodeNumber);
            for (Connection connectionToNeighbor : allConnectionsOfNode){
                int neighboringNode = connectionToNeighbor.aim;
                if (!visited.contains(neighboringNode))
                {
                    int distanceToNeighbor = connectionToNeighbor.length;
                    System.out.println(connectionToNeighbor.getAllInformationsAsString());
                    if(typeOfAlgorithm.equals("Dijkstra")) {
                        predictedDistance = 0;
                    }
                    else{
                        //predictedDistance: prognostizierte Distanz vom Nachbarknoten zum Zielknoten
                        predictedDistance = application.xmlParser.DistanceCalculator.calculateDistanceBetweenTwoPoints(latTargetNode, lngTargetNode, autobahn.get(neighboringNode).getLatitude(), autobahn.get(neighboringNode).getLongitude());
                        System.out.println("Luftlinie von Knoten " + neighboringNode + " bis Zielknoten: " + predictedDistance);
                    }
                    newDistance = distance.get(nodeNumber) + distanceToNeighbor;

                    if (newDistance < distance.get(neighboringNode))
                    {
                        distance.put(neighboringNode, newDistance);
                        predecessor.put(neighboringNode, nodeNumber);

                        nodeAndDistance.put(neighboringNode, (newDistance + predictedDistance));

                        System.out.println("von " + nodeNumber + " zu " + neighboringNode + " neue kuerzeste Distanz: " + newDistance);
                    }
                }
            }
        }
        if(visited.contains(targetNode)) {
            System.out.println("Es gibt einen Weg!");
            return output();
        }
        else{
            System.out.println("ERROR! Es gibt KEINEN Weg!");
            return null;
        }
    }

    private Stack<Integer> output() {
        Stack<Integer> way = new Stack<>();
        int nodeNumber;
        double totalDistance = ((double)distance.get(targetNode)/1000);
        System.out.println("Entfernung: " + distance.get(targetNode) + "m");
        System.out.println("Entfernung: " + totalDistance + "km");

        /*String pfad;
        pfad = String.valueOf(targetNode);*/
        way.push(targetNode);
        nodeNumber = targetNode;
        while (nodeNumber != startNode)
        {
            nodeNumber = predecessor.get(nodeNumber);
            way.push(nodeNumber);
            //pfad = nodeNumber + "/" + pfad;
        }
        //System.out.println("Weg: " + pfad);
        /*while (!way.empty()){
            System.out.print(way.peek() + "/");
        }*/

        return way;
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

