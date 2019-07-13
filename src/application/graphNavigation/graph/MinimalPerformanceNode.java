package application.graphNavigation.graph;


/**
 * Class used for graph calculations.
 * The normal Node class creates objects that are to big and carry not useful
 * information for some calculations.
 * Therefor the MinimalPerformanceNode is way simpler and faster.
 */
public class MinimalPerformanceNode {

    private double longitude;
    private double latitude;

    public MinimalPerformanceNode(double lon, double lat) {
        this.longitude = lon;
        this.latitude = lat;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }
}
