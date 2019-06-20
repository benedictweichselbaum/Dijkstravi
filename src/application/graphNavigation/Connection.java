package application.graphNavigation;

public class Connection {
    public int destination;
    public int length;
    public boolean link;
    public int maxspeed;
    public String name;

    Connection(int aim, int lengthOfRoad, boolean motorway_link, int maximumspeed, String nameOfRoad){
        destination = aim;
        length = lengthOfRoad;
        link = motorway_link;
        maxspeed = maximumspeed;
        name = nameOfRoad;
    }
}
