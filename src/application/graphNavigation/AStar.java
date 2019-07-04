package application.graphNavigation;

public class AStar extends Dijkstra{

    public int getPredictedDistance(int neighboringNode) {
        int predictedDistance;
        double latNeighboringNode = getAutobahn().get(neighboringNode).getLatitude();
        double lngNeighboringNode = getAutobahn().get(neighboringNode).getLongitude();
        //predictedDistance: prognostizierte Distanz vom Nachbarknoten zum Zielknoten
        predictedDistance = application.xmlParser.DistanceCalculator.calculateDistanceBetweenTwoPoints(getLatTargetNode(), getLngTargetNode(), latNeighboringNode, lngNeighboringNode);
        //System.out.println("Luftlinie von Knoten " + neighboringNode + " bis Zielknoten: " + predictedDistance);
        return predictedDistance;
    }

}
