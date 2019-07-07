package application.graphNavigation.graph;

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
