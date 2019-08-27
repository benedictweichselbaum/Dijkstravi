package application.graphNavigation.graph;

public class Connection {
    private int aim;
    private int length;
    private boolean link;
    private int maxspeed; //-1: unlimited; -2: no information; -3: variable signals
    private String name;
    private String destination;

    Connection(int roadaim, int lengthOfRoad, boolean motorway_link, int maximumspeed, String nameOfRoad, String roadDestination){
        aim = roadaim;
        length = lengthOfRoad;
        link = motorway_link;
        maxspeed = maximumspeed;
        name = nameOfRoad;
        destination = roadDestination;
    }

    public int getPersonalMaxSpeed(int myMaxspeed){
        if(maxspeed > 0 && maxspeed <= myMaxspeed) {
            return maxspeed;
        }
        else {
            return myMaxspeed;
        }
    }

    public String getAllInformationAsString(){
        return "Ziel: " + getAim() + " Länge: " + getLength() + " maxSpeed: " + getMaxspeed() + " Name: " + getName() + " Ziel: " + getDestination();
    }

    public int getAim() {
        return aim;
    }

    public int getLength() {
        return length;
    }

    public int getMaxspeed() {
        return maxspeed;
    }

    public String getName() {
        return name;
    }

    public String getDestination() {
        return destination;
    }
}
