package application.unitConverterTest;

import application.unitConverter.UnitConverter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UnitConverterTest {

    @Test
    void testMeterToKilometerRoundCheck () {
        assertEquals(12, UnitConverter.meterToKilometer(12400, 0));
        assertEquals(13, UnitConverter.meterToKilometer(12900, 0));

        assertEquals(12.11, UnitConverter.meterToKilometer(12111, 2));
        assertEquals(13.00, UnitConverter.meterToKilometer(12999, 2));

        assertEquals(12.851, UnitConverter.meterToKilometer(12851,3));

        assertEquals(0.00000, UnitConverter.meterToKilometer(0.000, 5));
    }

    @Test
    void testMeterToKilometerValueNegative () {
        boolean thrown = false;
        try {
            UnitConverter.meterToKilometer(-5, 5);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue(thrown);
    }

    @Test
    void testMeterToKilometerPlacesNegative () {
        boolean thrown = false;
        try {
            UnitConverter.meterToKilometer(5, -5);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue(thrown);
    }

    @Test
    void testCalculateTimeForDistance () {
        assertEquals(0.0, UnitConverter.calculateTimeForDistance(0, 130));

        assertEquals(2000.0, UnitConverter.calculateTimeForDistance(30000, 54));
    }

    @Test
    void testCalculateTimeForDistanceDistanceNegative () {
        boolean thrown = false;
        try {
            UnitConverter.calculateTimeForDistance(-10, 130);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue(thrown);
    }

    @Test
    void testCalculateTimeForDistanceSpeedNegative () {
        boolean thrown = false;
        try {
            UnitConverter.calculateTimeForDistance(1000, -10);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue(thrown);
    }

    @Test
    void testCalculateTimeForDistanceSpeedZero () {
        boolean thrown = false;
        try {
            UnitConverter.calculateTimeForDistance(1000, 0);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue(thrown);
    }

    @Test
    void testSecondsToHoursAndMinutes () {
        assertEquals("< 1 Min.", UnitConverter.secondsToHoursAndMinutes(0.0));
        assertEquals("< 1 Min.", UnitConverter.secondsToHoursAndMinutes(29));
        assertEquals("< 1 Min.", UnitConverter.secondsToHoursAndMinutes(59.9));

        assertEquals("0 Std. 1 Min.", UnitConverter.secondsToHoursAndMinutes(60));
        assertEquals("1 Std. 0 Min.", UnitConverter.secondsToHoursAndMinutes(3600.0));
        assertEquals("1 Std. 30 Min.", UnitConverter.secondsToHoursAndMinutes(5400.0));

        assertEquals("1 Std. 0 Min.", UnitConverter.secondsToHoursAndMinutes(3570.0));
        assertEquals("0 Std. 59 Min.", UnitConverter.secondsToHoursAndMinutes(3569.9));
    }

    @Test
    void testSecondsToHoursAndMinutesSecondsNegative () {
        boolean thrown = false;
        try {
            UnitConverter.secondsToHoursAndMinutes(-10);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue(thrown);
    }
}