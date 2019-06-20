package application.graphNavigation;

public class Node2 {

    int id;
    boolean link;
    double longitude;
    double latitude;

    public Node2(int id, boolean isLink, double lon, double lat) {
        this.id = id;
        this.link = isLink;
        this.longitude = lon;
        this.latitude = lat;
    }

    public int getId() {
        return id;
    }

    public boolean isLink() {
        return link;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }
}

