package application.graphNavigation;

public class Connection {
    int aim;
    int length;
    boolean link;
    int maxspeed; //-1: unlimited; -2: no information; -3: variable signals
    String name;
    String destination;

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
}
