package application.xmlParser;

import application.graphNavigation.graph.MinimalPerformanceNode;

import java.util.List;

 public abstract class DistanceCalculator {

     /**
      * Takes to Nodes and calculates the distance between them on the earth's surface.
      * The curvature of the earth is considered.
      */
    public static double calculateDistanceBetweenTwoNodes (MinimalPerformanceNode node1, MinimalPerformanceNode node2) {
        double lon1 = Math.toRadians(node1.getLongitude());
        double lon2 = Math.toRadians(node2.getLongitude());
        double lat1 = Math.toRadians(node1.getLatitude());
        double lat2 = Math.toRadians(node2.getLatitude());

        if (lon1 == lon2 && lat1 == lat2) return 0;

        double differenceInLongitude = lon2 - lon1;
        double differenceInLatitude = lat2 - lat1;
        double a = Math.pow(Math.sin(differenceInLatitude / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.pow(Math.sin(differenceInLongitude / 2), 2);

        double c = 2 * Math.asin(Math.sqrt(a));
        // Radius of earth
        double r = 6371;

        return (c * r) * 1000;
    }

    static double calculateDistanceBetweenAListOfNodes (List<MinimalPerformanceNode> minimalPerformanceNodeList) {
        double distanceOfList = 0;

        for (int i = 0; i <= minimalPerformanceNodeList.size()-2; i++)
            distanceOfList += calculateDistanceBetweenTwoNodes(minimalPerformanceNodeList.get(i),
                                                                minimalPerformanceNodeList.get(i+1));

        return distanceOfList;
    }
}
