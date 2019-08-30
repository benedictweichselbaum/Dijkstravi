package distanceCalculatorTest;

import application.graphNavigation.graph.MinimalPerformanceNode;
import application.xmlParser.DistanceCalculator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DistanceCalculatorTest {

    private static MinimalPerformanceNode node1;
    private static MinimalPerformanceNode node2;

    @BeforeAll
    static void initNodes () {
         node1 = new MinimalPerformanceNode(10, 56);
         node2 = new MinimalPerformanceNode(10, 55);
    }

    @Test
    void testDistanceBetweenTwoNodes () {
        double distanceFromDistanceCalculator = DistanceCalculator.calculateDistanceBetweenTwoNodes(node1, node2);
        assertEquals(11119, Math.round(distanceFromDistanceCalculator*100)/1000);
    }
}
