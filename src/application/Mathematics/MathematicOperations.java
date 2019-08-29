package application.Mathematics;

import application.graphNavigation.graph.Connection;

public abstract class MathematicOperations {

    public static double meterToKilometer(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        double factor = Math.pow(10, places);
        value = (value / 1000) * factor;
        return Math.round(value) / factor;
    }

    public static double calculateTimeForDistance(int distance, int speed){
        //Entfernung (distance) in Metern
        //Geschwindigkeit (speed) in km/h

        double speedInMeterPerSeconds = (double) speed / 3.6;
        return distance / speedInMeterPerSeconds;
    }

    public static double calculateTimeForConnection(Connection connection, int personalMaxSpeed){
        //Entfernung (distance) in Metern
        //Geschwindigkeit (speed) in km/h

        double speedInMeterPerSecond = (double) connection.getPersonalMaxSpeed(personalMaxSpeed) / 3.6;
        return connection.getLength() / speedInMeterPerSecond;
    }

    public static String secondsToHoursAndMinutes(double seconds){
        int hours = (int) (seconds / 3600);
        int minutes = (int) Math.round((seconds % 3600)/60);

        if(minutes == 0 && (((int)(seconds % 3600)/60)) == 59){
            hours = hours + 1;
        }
        else if(minutes == 0){
            return "< 1 Min.";
        }

        return String.format("%d Std. %d Min.", hours, minutes);
    }

}
