package application.graphNavigation;

public class LatLonNode {

    double longitude;
    double latitude;

    public LatLonNode(double lon, double lat) {
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