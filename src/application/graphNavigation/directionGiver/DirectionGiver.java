package application.graphNavigation.directionGiver;

import application.Mathematics.MathematicOperations;
import application.graphNavigation.graph.Connection;
import application.graphNavigation.graph.Graph;
import application.menuBarDialogs.optionDialog.OptionWindow;

import java.util.Stack;

public class DirectionGiver {

    private final String lineSeparator = System.getProperty("line.separator");
    private Graph g;
    private String orders;
    private String roadName;
    private double meterTillNextOrder;
    private double meterTillDestination;
    private double secondsTillDestination;
    private String location;
    private String destination;
    private boolean isOutputAutobahnChangeInProgress;

    public String directions(Graph g, Stack<Integer> way, int personalMaxSpeed){
        this.g = g;
        location = "";
        destination = "";
        isOutputAutobahnChangeInProgress = false;
        if(way != null) {
            //System.out.println(way.size());
            orders = lineSeparator + lineSeparator;

            initWithFirstConnection(g, way, personalMaxSpeed);
            Connection connection;

            int from = way.pop();
            while (!way.empty()) {
                connection = treatmentNextConnection(g, way, from);

                meterTillDestination = meterTillDestination + connection.getLength();
                secondsTillDestination = secondsTillDestination + MathematicOperations.calculateTimeForConnection(connection, personalMaxSpeed);
                from = way.pop();
            }

            outputOrderAutobahnChange();
            orders = orderFollowRoad(orders, roadName, meterTillNextOrder, g.getNodeById(from).getName());

            orders = orderNavigationFinished(orders);
        }
        else{
            orders = outputWayNotFound();
        }
        return orders;
    }

    private void initWithFirstConnection(Graph g, Stack<Integer> way, int personalMaxSpeed) {
        int from = way.pop();
        Connection connection = g.getConnectionBetween2Points(from, way.peek());
        meterTillNextOrder = connection.getLength();
        meterTillDestination = connection.getLength();
        secondsTillDestination = MathematicOperations.calculateTimeForConnection(connection, personalMaxSpeed);
        roadName = connection.getName();
    }

    private Connection treatmentNextConnection(Graph g, Stack<Integer> way, int from) {
        Connection connection;
        connection = g.getConnectionBetween2Points(from, way.peek());

        String actualLocation = g.getNodeById(from).getName();
        String actualRoadName = connection.getName().trim();
        System.out.println(connection.getAllInformationAsString() + " Von ID:" + from + " Von:" + actualLocation);

        if(roadName.trim().equals("") && !actualRoadName.equals("")){
            meterTillNextOrder = meterTillNextOrder + connection.getLength();
            roadName = actualRoadName;
        }
        else if(actualLocation != null && actualRoadName.equals("")) {
            meterTillNextOrder = meterTillNextOrder + connection.getLength();
            if ((actualLocation.contains("Kreuz") || actualLocation.contains("Dreieck"))){
                outputOrderAutobahnChange();
                isOutputAutobahnChangeInProgress = false;

                location = actualLocation;
                destination = connection.getDestination();
            }
            getDestination(connection);
        }
        else if(!actualRoadName.equals(roadName) && !actualRoadName.equals("")){
            outputOrderAutobahnChange();
            isOutputAutobahnChangeInProgress = true;

            orders = orderFollowRoad(orders, roadName, meterTillNextOrder, "") + lineSeparator;
            roadName = actualRoadName;

            getDestination(connection);
            if(location.equals("") && actualLocation != null){
                location = actualLocation;
            }
            meterTillNextOrder = connection.getLength();
        }
        else{
            if(!actualRoadName.equals("") && !isOutputAutobahnChangeInProgress){
                location = "";
            }
            getDestination(connection);
            meterTillNextOrder = meterTillNextOrder + connection.getLength();
        }
        return connection;
    }

    private void getDestination(Connection connection) {
        if (destination.equals("") && isOutputAutobahnChangeInProgress) {
            destination = connection.getDestination();
        }
    }

    private void outputOrderAutobahnChange() {
        if (isOutputAutobahnChangeInProgress) {
            orders = orderAutobahnChange(location, destination, roadName);
            location = "";
            destination = "";
        }
    }

    private String orderAutobahnChange(String location, String destination, String street){
        location = location.replace("/", " / ");
        destination = destination.replace(";", " / ");
        street = street.replace(";", " / ");


        if(location.equals("")){
            if(!destination.equals("")){
                destination = " in Richtung " + destination;
            }
            return String.format((orders + "Fahren Sie auf die %s%s." + lineSeparator), street, destination);
        }

        if(location.contains("Kreuz") || location.contains("Dreieck")){
            location = "Am " + location;
        }
        else{
            location = "Bei " +  location;
        }
        if(!destination.equals("")){
            destination = String.format(" der Beschilderung in Richtung %s folgen und", destination);
        }
        return String.format((orders + "%s%s auf die %s fahren." + lineSeparator),
                location, destination, street);
    }

    private String orderFollowRoad(String orders, String street, double meterTillNextOrder, String finalDestination) {
        street = street.replace(";", " / ");
        String meterTillNextOrderOutput = "" + MathematicOperations.meterToKilometer(meterTillNextOrder, 3);
        if(!finalDestination.equals("")){
            finalDestination = String.format(" bis zu Ihrem Ziel %s", finalDestination);
        }

        return String.format((orders + "Folgen Sie der %s für %s km%s." + lineSeparator), street, meterTillNextOrderOutput, finalDestination);
    }

    private String orderNavigationFinished(String orders) {
        orders = orders + lineSeparator + "Sie haben Ihr Ziel erreicht!" + lineSeparator;
        orders = orders + lineSeparator + "Entfernung: " + MathematicOperations.meterToKilometer(meterTillDestination, 3) + "km";
        orders = orders + lineSeparator + "Fahrzeit: " + MathematicOperations.secondsToHoursAndMinutes(secondsTillDestination);
        orders = orders + lineSeparator + "Danke für die Navigation mit Dijkstravi!";
        return orders;
    }

    private String outputWayNotFound() {
        return lineSeparator + "ERROR! Es gibt KEINEN Weg zwischen den ausgewählten Punkten!";
    }

}