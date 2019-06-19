package application.graphNavigation;

public class Node {

    long id;

    boolean link;
    double longitude;
    double latitude;

    public Node (long id, boolean isLink, double lon, double lat) {
        this.id = id;
        this.link = isLink;
        this.longitude = lon;
        this.latitude = lat;
    }

    public long getId() {
        return id;
    }

    public boolean isLink() {
        return link;
    }
}
