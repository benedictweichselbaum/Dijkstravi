package application.graphNavigation;

public class Connection {
    public int aim;
    public int length;
    public boolean link;
    public int maxspeed; //-1: unlimited; -2: no information; -3: variable signals
    public String name;
    public String destination;

    Connection(int roadaim, int lengthOfRoad, boolean motorway_link, int maximumspeed, String nameOfRoad, String roadDestination){
        aim = roadaim;
        length = lengthOfRoad;
        link = motorway_link;
        maxspeed = maximumspeed;
        name = nameOfRoad;
        destination = roadDestination;
    }

    public int getPersonalMaxSpeed(int myMaxspeed){
        if(maxspeed > 0 && maxspeed <= myMaxspeed)
            return maxspeed;
        else
            return myMaxspeed;
    }

    public String getAllInformationsAsString(){
        return "Ziel: " + getAim() + " Länge: " + getLength() + " maxSpeed: " + getMaxspeed() + " Name: " + getName() + " Ziel: " + getDestination();
    }

    public int getAim() {
        return aim;
    }

    public void setAim(int aim) {
        this.aim = aim;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public boolean isLink() {
        return link;
    }

    public void setLink(boolean link) {
        this.link = link;
    }

    public int getMaxspeed() {
        return maxspeed;
    }

    public void setMaxspeed(int maxspeed) {
        this.maxspeed = maxspeed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
