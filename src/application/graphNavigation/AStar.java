package application.graphNavigation;

public class AStar extends Dijkstra{

    @Override
    public int getPredictedDistance(int neighboringNode) {
        //predictedDistance: prognostizierte Distanz vom Nachbarknoten zum Zielknoten
        //System.out.println("Luftlinie von Knoten " + neighboringNode + " bis Zielknoten: " + predictedDistance);
         return (int) application.xmlParser.DistanceCalculator.calculateDistanceBetweenTwoNodes(
                new MinimalPerformanceNode(getLatTargetNode(), getLngTargetNode()),
                new MinimalPerformanceNode(getAutobahn().get(neighboringNode).getLatitude(),
                        getAutobahn().get(neighboringNode).getLongitude()
        ));
    }
}
