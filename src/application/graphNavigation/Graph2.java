package application.graphNavigation;

import java.util.ArrayList;
import java.util.HashMap;

public class Graph2 {

    ArrayList<Node2> autobahn = new ArrayList<>();
    HashMap<Integer, ArrayList<Connection>> links = new HashMap<>();

    public void addNodesSorted(Node2 nd){
        autobahn.add(nd);
        links.put(nd.getId(), new ArrayList<>());
    }

    public void addEdge(int start, int end, int length, int maxspeed, String name, String destination){
        if(start <= autobahn.size() && end <= autobahn.size()){
            ArrayList<Connection> al = links.get(start);
            al.add(new Connection(end, length,false, maxspeed, name, destination));
            links.put(start, al);
        }else{
            System.out.println("Liste war wohl zu kurz");
        }
    }

    public ArrayList<Connection> getAllConnectionsOfNode(int id){
        return links.get(id);
    }

    public int getAmountOfNodes(){
        return autobahn.size();
    }

    public ArrayList<ArrayList<Integer>> getListOfAllEdges(){
        ArrayList<ArrayList<Integer>> list = new ArrayList<>();
        for(int i = 0; i < autobahn.size(); i++){
            ArrayList<Integer> listOfAims = new ArrayList<>();
            for(Connection c: links.get(i)){
                listOfAims.add(c.aim);
            }
            list.add(listOfAims);
        }
        return list;
    }

}
