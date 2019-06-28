package application.graphNavigation;

import java.util.ArrayList;
import java.util.HashMap;

public class Graph {

    ArrayList<Node> autobahn = new ArrayList<>();
    HashMap<Integer, ArrayList<Connection>> links = new HashMap<>();

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

    public Connection getConnectionBetween2Points(int id1, int id2){
        ArrayList<Connection> connections = links.get(id1);
        Connection result = connections.stream()
                .filter(connection -> connection.getAim() == id2)
                .findAny()
                .orElse(null);
        //System.out.println(result.getAllInformationsAsString());
        return result;
    }

    public void startAStarigator(int startNode, int targetNode){
        System.out.println("AStarigator:");
        Navigator aStarigator = new DijkstraOrAStar("AStar");
        aStarigator.directions(this, aStarigator.calculateShortestWay(this, startNode, targetNode));
    }

    public void test(){
        for (int i = 0; i < links.size(); i++) {
            ArrayList<Connection> arrayList = links.get(i);
            System.out.println(" ");
            System.out.println("Nachfolger von Knoten " + i);
            for (Connection c : arrayList) {
                System.out.println(c.getAllInformationsAsString());
            }
        }
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
