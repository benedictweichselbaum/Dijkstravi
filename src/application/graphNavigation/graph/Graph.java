package application.graphNavigation.graph;

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

    public Connection getConnectionBetween2Points(int id1, int id2){
        ArrayList<Connection> connections = links.get(id1);
        Connection result = connections.stream()
                .filter(connection -> connection.getAim() == id2)
                .findAny()
                .orElse(null);
        //System.out.println(result.getAllInformationsAsString());
        return result;
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

    @SuppressWarnings("unused")
    public ArrayList<ArrayList<Integer>> getListOfAllEdges(){
        ArrayList<ArrayList<Integer>> list = new ArrayList<>();
        for(int i = 0; i < autobahn.size(); i++){
            ArrayList<Integer> listOfAims = new ArrayList<>();
            for(Connection c: links.get(i)){
                listOfAims.add(c.getAim());
            }
            list.add(listOfAims);
        }
        return list;
    }

    public Node getNodeById(int id){
        return autobahn.get(id);
    }

    public void deleteLastNodeWithOutgoingConnections(){
        // JA, DA SIND VERBESSERUNGSVORSCHLÄGE, ABER NICHT VOR JAVA 13 VERSUCHEN ZU ÄNDERN! GEHT NICHT(IMMER)!
        ArrayList<Connection> cnl = new ArrayList<>();
        for(Connection cn : getAllConnectionsOfNode(autobahn.size()-1)){
            //deleteConnection(autobahn.size() -1, cn);
            cnl.add(cn);
        }

        for(int i = 0; i < cnl.size(); i++){
            deleteConnection(autobahn.size() -1, cnl.get(i));
        }
        autobahn.remove(autobahn.size()-1);
    }

    public void deleteConnection(int from, Connection to){
        ArrayList<Connection> conList = links.get(from);
        conList.remove(to);
        links.put(from, conList);
    }

    public ArrayList<Node> getAutobahn() {
        return autobahn;
    }

    public HashMap<Integer, ArrayList<Connection>> getLinks() {
        return links;
    }

}
