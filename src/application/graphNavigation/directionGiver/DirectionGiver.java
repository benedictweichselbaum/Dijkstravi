package application.graphNavigation.directionGiver;

import application.Mathematics.MathematicOperations;
import application.graphNavigation.graph.Connection;
import application.graphNavigation.graph.Graph;

import java.util.Stack;

public class DirectionGiver {

    private final String lineSeparator = System.getProperty("line.separator");
    Graph g;
    private String orders;
    private String roadName;
    private double meterTillNextOrder;
    private double meterTillDestination;

    public String directions(Graph g, Stack<Integer> way){
        this.g = g;
        if(way != null) {
            //System.out.println(lineSeparator);
            orders = lineSeparator + lineSeparator;

            initWithFirstConnection(g, way);
            Connection connection;

            int from = way.pop();
            while (!way.empty()) {
                connection = treatmentNextConnection(g, way, from);

                meterTillDestination = meterTillDestination + connection.getLength();
                //System.out.println(connection.getAllInformationsAsString());
                from = way.pop();
            }
            orders = orderFollowRoad(orders, roadName, meterTillNextOrder);

            orders = orderNavigationFinished(orders, meterTillDestination);
            //Sytem.out.println(lineSeparator + orders);
        }
        else{
            orders = outputWayNotFound();
        }
        return orders;
    }

    private void initWithFirstConnection(Graph g, Stack<Integer> way) {
        int from = way.pop();
        Connection connection = g.getConnectionBetween2Points(from, way.peek());
        meterTillNextOrder = connection.getLength();
        meterTillDestination = connection.getLength();
        roadName = connection.getName();
    }

    private Connection treatmentNextConnection(Graph g, Stack<Integer> way, int from) {
        Connection connection;
        connection = g.getConnectionBetween2Points(from, way.peek());

        if(connection.getName().equals(roadName) || connection.getName().trim().equals("")){
            meterTillNextOrder = meterTillNextOrder + connection.getLength();
        }
        else if(roadName.trim().equals("") && !connection.getName().trim().equals("")){
            meterTillNextOrder = meterTillNextOrder + connection.getLength();
            roadName = connection.getName();
        }
        else{
            orders = orderFollowRoad(orders, roadName, meterTillNextOrder);
            roadName = connection.getName();
            //orders = orderChangeRoad(orders,from,roadName);
            meterTillNextOrder = connection.getLength();
        }
        return connection;
    }

    private String orderFollowRoad(String orders, String roadName, double meterTillNextOrder) {
        return orders + "Folgen Sie der " + roadName + " für " + MathematicOperations.meterToKilometer(meterTillNextOrder, 3) + "km" + lineSeparator;
    }

    private String orderChangeRoad(String orders, int exit, String nextRoad) {
        return orders + "Nehmen Sie die Ausfahrt: " + exit + g.getNodeById(exit).getName() + " und fahren Sie auf die " + nextRoad + " in Richtung: X" + lineSeparator;
    }

    private String orderNavigationFinished(String orders, double meterTillDestination) {
        orders = orders + "Sie haben Ihr Ziel erreicht!" + lineSeparator;
        orders = orders + lineSeparator + "Entfernung: " + MathematicOperations.meterToKilometer(meterTillDestination, 3) + "km";
        orders = orders + lineSeparator + "Danke für die Navigation mit Dijkstravi!";
        return orders;
    }

    private String outputWayNotFound() {
        return lineSeparator + "ERROR! Es gibt KEINEN Weg zwischen den ausgewählten Punkten!";
    }
}
