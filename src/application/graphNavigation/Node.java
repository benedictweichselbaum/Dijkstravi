package application.graphNavigation;

public class Node {

    double longitude;
    double latitude;

    public Node (double lon, double lat) {
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
