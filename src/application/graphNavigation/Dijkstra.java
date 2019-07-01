
package application.graphNavigation;

import java.util.*;

class Dijkstra extends Navigator{

    private ArrayList<Node> autobahn = new ArrayList<>();
    private HashMap<Integer, ArrayList<Connection>> links = new HashMap<>();

    private int startNode;
    private int targetNode;

    private double latTargetNode;
    private double lngTargetNode;

    private String typeOfAlgorithm;
    private int numberOfNodes;
    private Set<Integer> visited;
    private Map<Integer, Integer> distance;
    private Map<Integer, Integer> predecessor;
    private Map<Integer, Integer> reachableUnvisitedNotes;

    Dijkstra(){

    }

    private void init(Graph g, int startNodeId, int targetNodeId) {
        autobahn = g.autobahn;
        links = g.links;

        startNode = startNodeId;
        targetNode = targetNodeId;
        latTargetNode = autobahn.get(targetNode).getLatitude();
        lngTargetNode = autobahn.get(targetNode).getLongitude();

        numberOfNodes = g.getAmountOfNodes();
        visited = new HashSet<>();
        distance = new HashMap<>();
        predecessor = new HashMap<>();

        // die rot markierten Knoten -> PP
        reachableUnvisitedNotes = new HashMap<>();
        reachableUnvisitedNotes.put(startNode, 0);

        for (int i = 0; i < numberOfNodes; i++) {
            distance.put(i, INFINITE);
        }
        distance.put(startNode, 0);
        predecessor.put(startNode, startNode);
    }

    public Stack<Integer> calculateShortestWay(Graph g, int startNodeId, int targetNodeId){
        if(g == null){
            return null;
        }

        int nodeNumber;
        init(g, startNodeId, targetNodeId);

        // wiederhole bis alle Knoten besucht sind / Zielknoten besucht ist
        for (int i = 0; i < numberOfNodes; i++)
        {
            if(reachableUnvisitedNotes == null){
                break;
            }
            nodeNumber = getPositionOfUnvisitedNodeWithShortestDistance(reachableUnvisitedNotes);
            visited.add(nodeNumber);
            reachableUnvisitedNotes.remove(nodeNumber);

            //System.out.println("Knoten mit min. Distanz: " + nodeNumber);
            //um nicht zu allen Knoten den kürzesten Weg vom Startknoten aus zu berechnen
            if(nodeNumber == targetNode){
                break;
            }

            calculateDistanceToUnvisitedNeighboringNodes(g, nodeNumber);
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

    private void calculateDistanceToUnvisitedNeighboringNodes(Graph g, int nodeNumber) {
        int newDistance;
        int predictedDistance;
        //falls Nachbarknoten noch nicht besucht
        ArrayList<Connection> allConnectionsOfNode = g.getAllConnectionsOfNode(nodeNumber);
        for (Connection connectionToNeighbor : allConnectionsOfNode){
            int neighboringNode = connectionToNeighbor.getAim();
            if (!visited.contains(neighboringNode))
            {
                int distanceToNeighbor = connectionToNeighbor.getLength();
                //System.out.println(connectionToNeighbor.getAllInformationsAsString());
                predictedDistance = getPredictedDistance(neighboringNode);
                newDistance = distance.get(nodeNumber) + distanceToNeighbor;

                if (newDistance < distance.get(neighboringNode))
                {
                    distance.put(neighboringNode, newDistance);
                    predecessor.put(neighboringNode, nodeNumber);

                    reachableUnvisitedNotes.put(neighboringNode, (newDistance + predictedDistance));
                    //System.out.println("von " + nodeNumber + " zu " + neighboringNode + " neue kuerzeste Distanz: " + newDistance);
                }
            }
        }
    }

    public int getPredictedDistance(int neighboringNode) {
        return 0;
    }

    private Stack<Integer> output() {
        Stack<Integer> way = new Stack<>();
        int nodeNumber;
        double totalDistance = ((double)distance.get(targetNode)/1000);
        System.out.println("Entfernung: " + distance.get(targetNode) + "m");
        System.out.println("Entfernung: " + totalDistance + "km");

        way.push(targetNode);
        nodeNumber = targetNode;
        while (nodeNumber != startNode)
        {
            nodeNumber = predecessor.get(nodeNumber);
            way.push(nodeNumber);
        }

        return way;
    }

    private int getPositionOfUnvisitedNodeWithShortestDistance(Map<Integer, Integer> nodeAndDistance) {

        Map.Entry<Integer, Integer> min = null;
        for (Map.Entry<Integer, Integer> entry : nodeAndDistance.entrySet()) {
            if (min == null || min.getValue() > entry.getValue()) {
                min = entry;
            }
        }
        if (min != null) {
            return min.getKey();
        }
        else{
            return 0;
        }
    }

    public ArrayList<Node> getAutobahn() {
        return autobahn;
    }

    public double getLatTargetNode() {
        return latTargetNode;
    }

    public double getLngTargetNode() {
        return lngTargetNode;
    }
}
