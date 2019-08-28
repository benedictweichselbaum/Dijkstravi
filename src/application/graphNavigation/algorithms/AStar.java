package application.graphNavigation.algorithms;

import application.Mathematics.MathematicOperations;
import application.graphNavigation.graph.MinimalPerformanceNode;

public class AStar extends Dijkstra{

    @Override
    public int getPredictedDistance(int neighboringNode) {
        //predictedDistance: prognostizierte Distanz vom Nachbarknoten zum Zielknoten
        int distance = (int) application.xmlParser.DistanceCalculator.calculateDistanceBetweenTwoNodes(
                new MinimalPerformanceNode(getLngTargetNode(), getLatTargetNode()),
                new MinimalPerformanceNode(getAutobahn().get(neighboringNode).getLongitude(),
                        getAutobahn().get(neighboringNode).getLatitude()
                ));

        if(this.isFastestPath()){
            return (int) Math.round(MathematicOperations.calculateTimeForDistance(distance, this.getPersonalMaxSpeed()));
        }
        else {
            return distance;
        }

        /*return (int) application.xmlParser.DistanceCalculator.calculateDistanceBetweenTwoNodes(
                new MinimalPerformanceNode( -8.5278405,37.1221372),
                new MinimalPerformanceNode( 10.9407158,49.4394070
                ));*/ //Erg:2076824
    }
}
