package application.xmlParser;
import java.util.*;

import org.jdom2.Attribute;

public class Way {
    private ArrayList<Attribute> nodeList;
    private boolean motorway_link;
    private long id;
    private String name;
    private String destinaton;
    private int maxspeed;

    Way(long wayID, ArrayList<Attribute> list, boolean ismw_link, int speed, String nameOfRoad, String roadDestination){
        nodeList = list;
        motorway_link = ismw_link;
        id = wayID;
        name = nameOfRoad;
        destinaton = roadDestination;
        maxspeed = speed;
    }

    //public int getLength(){
    //     return 3;
    //}

    long getFirst(){
        try{
            return nodeList.get(0).getLongValue();
        }catch (Exception e){
            return 0;
        }
    }

    long getLast(){
        try{
            return nodeList.get(nodeList.size() -1).getLongValue();
        }catch (Exception e){
            return 0;
        }
    }

    ArrayList<Attribute> getListOfIDsOfNodes(){
        return  nodeList;
    }

    public long getID(){
        return  id;
    }

    public int getmaxspeed(){return maxspeed;}

    public String getDestinaton(){return destinaton;}

    public String getName(){return name;}
}
