package application.graphNavigation;

class Node {

    private long id;
    private boolean link;
    private double longitude;
    private double latitude;

    Node (long id, boolean isLink, double lat, double lon) {
        this.id = id;
        this.link = isLink;
        this.latitude = lat;
        this.longitude = lon;
    }

    long getId() {
        return id;
    }

    double getLongitude(){
        return longitude;
    }

    double getLatitude(){
        return latitude;
    }

    boolean getLink(){
        return link;
    }
}
