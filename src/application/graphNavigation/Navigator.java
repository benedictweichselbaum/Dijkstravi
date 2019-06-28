package application.graphNavigation;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

abstract class Navigator {

    int INFINITE = Integer.MAX_VALUE;

    public abstract Stack<Integer> calculateShortestWay(Graph g, int startNode, int targetNode);

    public String directions(Graph g, Stack<Integer> way){
        String orders = "";
        String roadName;
        int meterTillNextOrder;
        int meterTillDestination;
        final String lineSeparator = System.getProperty("line.separator");

        if(way != null) {
            System.out.println(" ");
            orders = "Ihre Routenbeschreibung von " + way.peek() + " nach " + way.firstElement() + ":" + lineSeparator;

            int from = way.pop();
            Connection connection = g.getConnectionBetween2Points(from, way.peek());
            meterTillNextOrder = connection.getLength();
            meterTillDestination = connection.getLength();
            roadName = connection.getName();

            from = way.pop();
            while (!way.empty()) {
                connection = g.getConnectionBetween2Points(from, way.peek());

                if(connection.getName().equals(roadName) || connection.getName().trim().equals("")){
                    meterTillNextOrder = meterTillNextOrder + connection.getLength();
                }
                else{
                    orders = orders + "Folgen Sie der " + roadName + " für " + meterTillNextOrder/1000 + "km" + lineSeparator;
                    roadName = connection.getName();
                    meterTillNextOrder = connection.getLength();
                }

                meterTillDestination = meterTillDestination + connection.getLength();
                System.out.println(connection.getAllInformationsAsString());
                from = way.pop();
            }
            orders = orders + "Folgen Sie der " + roadName + " für " + meterTillNextOrder/1000 + "km" + lineSeparator;

            orders = orders + lineSeparator + "Entfernung: " + meterTillDestination/1000 + "km" + lineSeparator;
            orders = orders + lineSeparator + "Danke für die Navigation mit Dijkstravi!";

            System.out.println(" ");
            System.out.println(orders);
            return orders;
        }
        else{
            return "ERROR! Es gibt KEINEN Weg zwischen den ausgewählten Punkten!";
        }
    }
}
