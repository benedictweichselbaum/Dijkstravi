package application.graphNavigation;

import java.util.ArrayList;
import java.util.HashMap;

public class Graph {

    private ArrayList<Node> autobahn = new ArrayList<>();
    private HashMap<Integer, ArrayList<Connection>> links = new HashMap<>();

    public void addNodesSorted(Node nd){
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

    @SuppressWarnings("unused")
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

    public Node getNodeById(int id){
        return autobahn.get(id);
    }

    public void deleteLastNodeWithOutgoingConnections(){
        for(Connection cn : getAllConnectionsOfNode(autobahn.size()-1)){
            deleteConnection(autobahn.size() -1, cn);
        }
        autobahn.remove(autobahn.size()-1);
    }

    public void deleteConnection(int from, Connection to){
        ArrayList<Connection> conList = links.get(from);
        conList.remove(to);
        links.put(from, conList);
    }

}
