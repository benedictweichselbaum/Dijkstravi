package application.graphNavigation;

import application.Mathematics.MathematicOperations;
import application.xmlParser.XMLParser;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Stack;

abstract class NavigationService {

    int INFINITE = Integer.MAX_VALUE;

    public abstract Stack<Integer> calculateShortestWay(Graph g, int startNode, int targetNode);

    public String directions(Graph g, Stack<Integer> way) {
        return new Directions().directions(g, way);
    }
}
