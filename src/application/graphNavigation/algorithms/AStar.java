package application.graphNavigation.algorithms;

import application.unitConverter.UnitConverter;
import application.graphNavigation.graph.MinimalPerformanceNode;

/**
 * "Implementation" of the AStar Algorithm. It's based on the Dijkstra Algorithm.
 * This algorithm calculates the fastest/shortest way from the startNode to the target node.
 */

public class AStar extends Dijkstra{

    @Override
    public int getPredictedDistance(int neighboringNode) {
        // predictedDistance: predicted distance (seconds/meters) from neighboring node to target node

        double distanceExact = application.xmlParser.DistanceCalculator.calculateDistanceBetweenTwoNodes(
                new MinimalPerformanceNode(getLngTargetNode(), getLatTargetNode()),
                new MinimalPerformanceNode(getAutobahn().get(neighboringNode).getLongitude(),
                        getAutobahn().get(neighboringNode).getLatitude()
                ));

        int distance = (int) Math.round(distanceExact);

        if(this.isFastestPath()){
            // distance (seconds) - will be used as heuristic to find the fastest path
            return (int) Math.round(UnitConverter.calculateTimeForDistance(distance, this.getPersonalMaxSpeed()));
        }
        else {
            // distance (meters) - will be used as heuristic to find the shortest path
            return distance;
        }
    }
}
