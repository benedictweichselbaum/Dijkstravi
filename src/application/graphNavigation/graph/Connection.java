package application.graphNavigation.graph;

/**
 * Class that carries information of the connection to the next node.
 */
public class Connection {
    private int aim;
    private int length;
    private int maxSpeed; //-1: unlimited; -2: no information; -3: variable signals
    private String name;
    private String destination;

    Connection(int roadAim, int lengthOfRoad, int maximumSpeed, String nameOfRoad, String roadDestination){
        aim = roadAim;
        length = lengthOfRoad;
        maxSpeed = maximumSpeed;
        name = nameOfRoad;
        destination = roadDestination;
    }

    public int getPersonalMaxSpeed(int myMaxspeed){
        if(maxSpeed > 0 && maxSpeed <= myMaxspeed) {
            return maxSpeed;
        }
        else {
            return myMaxspeed;
        }
    }

    public int getAim() {
        return aim;
    }

    public int getLength() {
        return length;
    }

    public String getName() {
        return name;
    }

    public String getDestination() {
        return destination;
    }
}
