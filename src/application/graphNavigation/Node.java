package application.graphNavigation;

public class Node {

    int id;
    boolean link;
    double longitude;
    double latitude;
    String name;

    public Node(int id, boolean isLink, double lat, double lon, String name) {
        this.id = id;
        this.link = isLink;
        this.longitude = lon;
        this.latitude = lat;
        this.name = name;

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

