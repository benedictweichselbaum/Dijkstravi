package application.graphNavigation;

public class Node {

    int id;
    boolean link;
    double longitude;
    double latitude;

    public Node (int id, boolean isLink, double lon, double lat) {
        this.id = id;
        this.link = isLink;
        this.longitude = lon;
        this.latitude = lat;
    }

    public int getId() {
        return id;
    }
}
