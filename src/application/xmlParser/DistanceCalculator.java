package application.xmlParser;

import application.graphNavigation.Node;

import java.util.ArrayList;
import java.util.List;

public class DistanceCalculator {

    public static double calculateDistanceFromListOfNodes (List<Node> listOfNodes) {
        double finalDistance = 0;

        for (int i = 0; i <= listOfNodes.size() -2; i++) { //Bis zum vorletzten Element
            double lon1 = Math.toRadians(listOfNodes.get(i).getLongitude());
            double lon2 = Math.toRadians(listOfNodes.get(i+1).getLongitude());
            double lat1 = Math.toRadians(listOfNodes.get(i).getLatitude());
            double lat2 = Math.toRadians(listOfNodes.get(i+1).getLatitude());

            double dlon = lon2 - lon1;
            double dlat = lat2 - lat1;
            double a = Math.pow(Math.sin(dlat / 2), 2)
                    + Math.cos(lat1) * Math.cos(lat2)
                    * Math.pow(Math.sin(dlon / 2),2);

            double c = 2 * Math.asin(Math.sqrt(a));
            // Radius der Erde
            double r = 6371;

            finalDistance = finalDistance + (c * r);
        }

        return finalDistance * 1000; //Gibt das Ergebnis in Metern zurück
    }

    public static void main (String... args) {
        Node n1 = new Node(11.2425042, 49.1980249);
        Node n2 = new Node(11.2413157, 49.1993888);
        Node n3 = new Node(11.2376436, 49.2037429);
        Node n4 = new Node(11.2354029, 49.2063958);

        List<Node> nodeList = new ArrayList<>();
        nodeList.add(n1);
        nodeList.add(n2);
        nodeList.add(n3);
        nodeList.add(n4);

        System.out.println(calculateDistanceFromListOfNodes(nodeList));
    }
}
