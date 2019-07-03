package application.graphNavigation;

import application.Mathematics.MathematicOperations;

import java.util.Stack;

public class Directions {

    private final String lineSeparator = System.getProperty("line.separator");
    private String orders;
    private String roadName;
    private double meterTillNextOrder;
    private double meterTillDestination;

    public String directions(Graph g, Stack<Integer> way){

        if(way != null) {
            System.out.println(lineSeparator);
            orders = orderNavigationStart(way);

            initWithFirstConnection(g, way);
            Connection connection;

            int from = way.pop();
            while (!way.empty()) {
                connection = treatmentNextConnection(g, way, from);

                meterTillDestination = meterTillDestination + connection.getLength();
                System.out.println(connection.getAllInformationsAsString());
                from = way.pop();
            }
            orders = orderFollowRoad(orders, roadName, meterTillNextOrder);

            orders = orderNavigationFinished(orders, meterTillDestination);
            System.out.println(lineSeparator + orders);
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
        else{
            orders = orderFollowRoad(orders, roadName, meterTillNextOrder);
            roadName = connection.getName();
            meterTillNextOrder = connection.getLength();
        }
        return connection;
    }

    private String orderNavigationStart(Stack<Integer> way) {
        return  "Ihre Routenbeschreibung von " + way.peek() + " nach " + way.firstElement() + ":" + lineSeparator;
    }

    private String orderFollowRoad(String orders, String roadName, double meterTillNextOrder) {
        return orders + "Folgen Sie der " + roadName + " für " + MathematicOperations.meterToKilometer(meterTillNextOrder, 3) + "km" + lineSeparator;
    }

    private String orderNavigationFinished(String orders, double meterTillDestination) {
        orders = orders + lineSeparator + "Entfernung: " + MathematicOperations.meterToKilometer(meterTillDestination, 3) + "km" + lineSeparator;
        orders = orders + lineSeparator + "Danke für die Navigation mit Dijkstravi!";
        return orders;
    }

    private String outputWayNotFound() {
        return "ERROR! Es gibt KEINEN Weg zwischen den ausgewählten Punkten!";
    }
}
