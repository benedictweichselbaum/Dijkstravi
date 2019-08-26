package application.Mathematics;

import application.graphNavigation.graph.Connection;

public abstract class MathematicOperations {

    public static void main(String... args) {
       double time = MathematicOperations.calculateTimeForDistance(5010, 130);
       System.out.println(time);
       System.out.println(Math.round(time));

       System.out.println(meterToKilometer(1234.56789, 0));
       System.out.println(meterToKilometer(1234.56789, 1));
       System.out.println(meterToKilometer(1234.56789, 2));
       System.out.println(meterToKilometer(1234.56789, 3));
       System.out.println(meterToKilometer(1234.56789, 4));
    }

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
        int secondsRound = (int) Math.round(seconds);
        return String.format("%d Std. %d Min.", (secondsRound / 3600), (secondsRound % 3600)/60);
    }

}
