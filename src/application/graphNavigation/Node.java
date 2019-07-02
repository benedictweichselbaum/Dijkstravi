package application.graphNavigation;

public class Node {

    private int id;
    private boolean link;
    private double longitude;
    private double latitude;
    private String name;

    public Node(int id, boolean isLink, double lat, double lon, String name) {
        this.id = id;
        this.link = isLink;
        this.longitude = lon;
        this.latitude = lat;
        this.name = name;

    }

    int getId() {
        return id;
    }

    @SuppressWarnings("unused")
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

