package application.graphNavigation;

public class Node {

    long id;
    boolean link;
    double longitude;
    double latitude;

    public Node (long id, boolean isLink, double lat, double lon) {
        this.id = id;
        this.link = isLink;
        this.latitude = lat;
        this.longitude = lon;
    }

    public long getId() {
        return id;
    }
}
