package application.graphNavigation.directionGiver;

import application.DijkstraviController;
import application.Mathematics.MathematicOperations;
import application.graphNavigation.graph.Connection;
import application.graphNavigation.graph.Graph;
import javafx.application.Platform;

import java.util.Stack;

public class DirectionGiver {

    private final String lineSeparator = System.getProperty("line.separator");
    private Graph g;
    private String orders;
    private String roadName;
    private double meterTillNextOrder;
    private double secondsTillNextOrder;
    private double meterTillDestination;
    private double secondsTillDestination;
    private String location;
    private String destination;
    private boolean isOutputAutobahnChangeInProgress;
    private int personalMaxSpeed;
    private DijkstraviController dijkstraviController;

    public String directions(Graph g, Stack<Integer> way, int personalMaxSpeed, DijkstraviController dijkstraviController){
        this.g = g;
        this.personalMaxSpeed = personalMaxSpeed;
        this.dijkstraviController = dijkstraviController;
        location = "";
        destination = "";
        isOutputAutobahnChangeInProgress = false;
        if(way != null) {
            //System.out.println(way.size());
            orders = lineSeparator + lineSeparator;

            initWithFirstConnection(way);
            Connection connection;

            int from = way.pop();
            while (!way.empty()) {
                connection = treatmentNextConnection(way, from);

                meterTillDestination = meterTillDestination + connection.getLength();
                secondsTillDestination = secondsTillDestination + MathematicOperations.calculateTimeForConnection(connection, connection.getPersonalMaxSpeed(personalMaxSpeed));
                from = way.pop();
            }

            outputOrderAutobahnChange();
            orders = orderFollowRoad(g.getNodeById(from).getName());

            orderNavigationFinished();
        }
        else{
            outputWayNotFound();
        }
        return orders;
    }

    private void initWithFirstConnection(Stack<Integer> way) {
        int from = way.pop();
        Connection connection = g.getConnectionBetween2Points(from, way.peek());
        meterTillNextOrder = connection.getLength();
        secondsTillNextOrder = MathematicOperations.calculateTimeForConnection(connection, connection.getPersonalMaxSpeed(personalMaxSpeed));
        meterTillDestination = connection.getLength();
        secondsTillDestination = MathematicOperations.calculateTimeForConnection(connection, connection.getPersonalMaxSpeed(personalMaxSpeed));
        roadName = connection.getName();
    }

    private Connection treatmentNextConnection(Stack<Integer> way, int from) {
        Connection connection;
        connection = g.getConnectionBetween2Points(from, way.peek());
        double timeForConnection = MathematicOperations.calculateTimeForConnection(connection, connection.getPersonalMaxSpeed(personalMaxSpeed));

        String actualLocation = g.getNodeById(from).getName();
        String actualRoadName = connection.getName().trim();
        //System.out.println(connection.getAllInformationAsString() + " Von ID:" + from + " Von:" + actualLocation);


        if(roadName.trim().equals("") && !actualRoadName.equals("")){
            meterTillNextOrder = meterTillNextOrder + connection.getLength();
            secondsTillNextOrder = secondsTillNextOrder + timeForConnection;
            roadName = actualRoadName;
        }
        else if(actualLocation != null && actualRoadName.equals("")) {
            meterTillNextOrder = meterTillNextOrder + connection.getLength();
            secondsTillNextOrder = secondsTillNextOrder + timeForConnection;
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

            orders = orderFollowRoad("") + lineSeparator;
            roadName = actualRoadName;

            getDestination(connection);
            if(location.equals("") && actualLocation != null){
                location = actualLocation;
            }
            meterTillNextOrder = connection.getLength();
            secondsTillNextOrder = timeForConnection;
        }
        else{
            if(!actualRoadName.equals("") && !isOutputAutobahnChangeInProgress){
                location = "";
            }
            getDestination(connection);
            meterTillNextOrder = meterTillNextOrder + connection.getLength();
            secondsTillNextOrder = secondsTillNextOrder + timeForConnection;
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
            orders = orderAutobahnChange();
            location = "";
            destination = "";
        }
    }

    private String orderAutobahnChange(){
        location = location.replace("/", " / ");
        destination = destination.replace(";", " / ");
        roadName = roadName.replace(";", " / ");


        if(location.equals("")){
            if(!destination.equals("")){
                destination = " in Richtung " + destination;
            }
            return String.format((orders + "Fahren Sie auf die %s%s." + lineSeparator), roadName, destination);
        }

        if(location.contains("Kreuz") || location.contains("Dreieck")){
            location = "Am " + location;
        }
        else{
            location = "Bei " +  location;
        }
        if(!destination.equals("")){
            destination = String.format(" der Beschilderung in Richtung %s folgen und", getLimitedNumberOfDestinations(3));
        }
        return String.format((orders + "%s%s auf die %s fahren." + lineSeparator),
                location, destination, roadName);
    }

    private String orderFollowRoad(String finalDestination) {
        roadName = roadName.replace(";", " / ");
        String meterTillNextOrderOutput = "" + MathematicOperations.meterToKilometer(meterTillNextOrder, 3);
        String secondsTillNextOrderOutput = "" + MathematicOperations.secondsToHoursAndMinutes(secondsTillNextOrder);
        if(!finalDestination.equals("")){
            finalDestination = String.format(" bis zu Ihrem Ziel %s", finalDestination);
        }

        return String.format((orders + "Folgen Sie der %s für %s km%s. (%s)" + lineSeparator)
                , roadName, meterTillNextOrderOutput, finalDestination, secondsTillNextOrderOutput);
    }

    private void orderNavigationFinished() {
        orders = orders + lineSeparator + "Sie haben Ihr Ziel erreicht!" + lineSeparator;
        orders = orders + lineSeparator + "Entfernung: " + MathematicOperations.meterToKilometer(meterTillDestination, 3) + "km";
        orders = orders + lineSeparator + "Fahrzeit: " + MathematicOperations.secondsToHoursAndMinutes(secondsTillDestination);
        orders = orders + lineSeparator + "Danke für die Navigation mit Dijkstravi!";
        setDistanceAndDuration();
    }

    private void setDistanceAndDuration(){
        // Avoid throwing IllegalStateException by running from a non-JavaFX thread.
        Platform.runLater(
                () -> {
                    dijkstraviController.getLblDistance().setText(MathematicOperations.meterToKilometer(meterTillDestination, 3) + "km");
                    dijkstraviController.getLblDuration().setText(MathematicOperations.secondsToHoursAndMinutes(secondsTillDestination));
                }
        );
    }

    private void outputWayNotFound() {
        orders = lineSeparator + lineSeparator + "Bitte warten!"
                + lineSeparator + "Ihr gewünschter Zielort ist leider noch nicht von Ihrem Startpunkt aus über Autobahnen zu erreichen."
                + lineSeparator + lineSeparator + "Danke für die Navigation mit Dijkstravi!";
    }

    private String getLimitedNumberOfDestinations(int limit){
        String[] destinations = destination.split(" / ");
        String destinationsForOutput = destinations[0];

        for(int i = 1; i < limit; i++){
            destinationsForOutput = String.format("%s / " + destinations[i], destinationsForOutput);
        }

        return destinationsForOutput;
    }

}