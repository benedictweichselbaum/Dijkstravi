package application.xmlParser;
import java.io.*;
import java.util.*;

import application.graphNavigation.Node;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class OneWay {
    ArrayList<Attribute> nodeList;
    boolean motorway_link;
    long id;
    String name;
    String destinaton;
    int maxspeed;

    public OneWay(long wayID, ArrayList<Attribute> list, boolean ismw_link, int speed, String nameOfRoad, String roadDestination){
        nodeList = list;
        motorway_link = ismw_link;
        id = wayID;
        name = nameOfRoad;
        destinaton = roadDestination;
        maxspeed = speed;
    }

    public int getLength(){
         return 3;
    }

    public long getFirst(){
        try{
            return nodeList.get(0).getLongValue();
        }catch (Exception e){
            return 0;
        }
    }

    public long getLast(){
        try{
            return nodeList.get(nodeList.size() -1).getLongValue();
        }catch (Exception e){
            return 0;
        }
    }

    public ArrayList<Attribute> getListOfIDsOfNodes(){
        return  nodeList;
    }

    public long getID(){
        return  id;
    }

    public int getmaxspeed(){return maxspeed;}

    public String getDestinaton(){return destinaton;}

    public String getName(){return name;}
}
