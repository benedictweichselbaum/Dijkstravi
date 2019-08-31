package application.xmlParser;
import java.util.*;

import org.jdom2.Attribute;

/*
* The relevant information is cached as "Way"s because of speed issues.
* After finished creation of the Graph this class is no longer needed.
 */

public class Way {
    private ArrayList<Attribute> nodeList;
    private String name;
    private String destination;
    private int maxSpeed;

    Way(ArrayList<Attribute> list, int speed, String nameOfRoad, String roadDestination){
        nodeList = list;
        name = nameOfRoad;
        destination = roadDestination;
        maxSpeed = speed;
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

    int getmaxspeed(){return maxSpeed;}

    String getDestination(){return destination;}

    public String getName(){return name;}
}
