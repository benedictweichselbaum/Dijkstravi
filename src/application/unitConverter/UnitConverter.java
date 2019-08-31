package application.unitConverter;

import application.graphNavigation.graph.Connection;
/**
 * This class provides methods to convert units.
 */
public abstract class UnitConverter {

    public static double meterToKilometer(double value, int places) {
        if (places < 0){
            places = 0;
        }

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

        return calculateTimeForDistance(connection.getLength(), connection.getPersonalMaxSpeed(personalMaxSpeed));
    }

    public static String secondsToHoursAndMinutes(double seconds){
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
