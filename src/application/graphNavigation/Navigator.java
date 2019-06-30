package application.graphNavigation;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Stack;

abstract class Navigator {

    int INFINITE = Integer.MAX_VALUE;
    final String lineSeparator = System.getProperty("line.separator");

    public abstract Stack<Integer> calculateShortestWay(Graph g, int startNode, int targetNode);

    public String directions(Graph g, Stack<Integer> way){
        String orders;
        String roadName;
        double meterTillNextOrder;
        double meterTillDestination;

        if(way != null) {
            System.out.println(lineSeparator);
            orders = orderNavigationStart(way);

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
                    orders = orderFollowRoad(orders, roadName, meterTillNextOrder);
                    roadName = connection.getName();
                    meterTillNextOrder = connection.getLength();
                }

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

    private String orderNavigationStart(Stack<Integer> way) {
        return  "Ihre Routenbeschreibung von " + way.peek() + " nach " + way.firstElement() + ":" + lineSeparator;
    }

    private String orderFollowRoad(String orders, String roadName, double meterTillNextOrder) {
        return orders + "Folgen Sie der " + roadName + " für " + round(meterTillNextOrder / 1000, 1) + "km" + lineSeparator;
    }

    private String orderNavigationFinished(String orders, double meterTillDestination) {
        orders = orders + lineSeparator + "Entfernung: " + round(meterTillDestination/1000, 1) + "km" + lineSeparator;
        orders = orders + lineSeparator + "Danke für die Navigation mit Dijkstravi!";
        return orders;
    }

    private String outputWayNotFound() {
        return "ERROR! Es gibt KEINEN Weg zwischen den ausgewählten Punkten!";
    }

    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
