package application.Mathematics;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MathematicOperations {

    public static void main(String... args) {
       double time = MathematicOperations.calculateTimeForDistance(5010, 130);
       System.out.println(time);
       System.out.println(Math.round(time));
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static double calculateTimeForDistance(int distance, int speed){
        //Entfernung (distance) in Metern
        //Geschwindigkeit (speed) in km/h

        double speedInMeterPerSeconds = (double) speed / 3.6;
        double timeInSeconds = distance / speedInMeterPerSeconds;
        return timeInSeconds;
    }

}
