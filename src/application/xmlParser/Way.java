package application.xmlParser;

import application.graphNavigation.Node;
import org.jdom2.Attribute;

import java.util.List;

public class Way {
    List<Node> nodes;
    int distance;

    Way (List<Node> listOfNodes) {
        this.nodes = listOfNodes;
        distance = -1;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
