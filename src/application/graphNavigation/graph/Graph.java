package application.graphNavigation.graph;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Data structure graph realized with adjacency lists.
 * The HashMap with the adjacency lists maps the ID of a node
 * to its adjacency list and therefore to its neighbouring nodes.
 * Annotation: The ID of each node equals the index of the autobahn array.
 */
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
            al.add(new Connection(end, length, maxspeed, name, destination));
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
        //System.out.println(result.getAllInformationsAsString());
        return connections.stream()
                .filter(connection -> connection.getAim() == id2)
                .findAny()
                .orElse(null);
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
        //deleteConnection(autobahn.size() -1, cn);
        ArrayList<Connection> cnl = new ArrayList<>(getAllConnectionsOfNode(autobahn.size() - 1));

        for (Connection connection : cnl) {
            deleteConnection(autobahn.size() - 1, connection);
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
