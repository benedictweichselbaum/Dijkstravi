package application.unitConverter;

import application.graphNavigation.graph.Connection;
/**
 * This class provides methods to convert units.
 */
public abstract class UnitConverter {

    public static double meterToKilometer(double value, int places) {
        if (value < 0 || places < 0){
            throw new IllegalArgumentException("Converting m to km failed because of negativ parameters!");
        }

        double factor = Math.pow(10, places);
        value = (value / 1000) * factor;
        return Math.round(value) / factor;
    }

    public static double calculateTimeForDistance(double distance, int speed){
        //Entfernung (distance) in Metern
        //Geschwindigkeit (speed) in km/h

        if (distance < 0 || speed <= 0){
            throw new IllegalArgumentException("Time-Calculation for Distance failed");
        }

        double speedInMeterPerSeconds = (double) speed / 3.6;
        return distance / speedInMeterPerSeconds;

    }

    public static double calculateTimeForConnection(Connection connection, int personalMaxSpeed){

        return calculateTimeForDistance(connection.getLength(), connection.getPersonalMaxSpeed(personalMaxSpeed));
    }

    public static String secondsToHoursAndMinutes(double seconds){
        if (seconds < 0){
            throw new IllegalArgumentException("Converting seconds failed - value of seconds negative!");
        }

        int hours = (int) (seconds / 3600);
        int minutes = (int) Math.round((seconds % 3600)/60);

        if(minutes == 60){
            hours = hours + 1;
            minutes = 0;
        }

        if(seconds < 60) {
            return "< 1 Min.";
        }
        else{
            return String.format("%d Std. %d Min.", hours, minutes);
        }
    }

}
