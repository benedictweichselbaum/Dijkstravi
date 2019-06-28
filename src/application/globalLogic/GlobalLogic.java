package application.globalLogic;

import application.graphNavigation.Graph;
import application.xmlParser.XMLParser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

import java.util.ArrayList;
import java.util.HashMap;

public class GlobalLogic {

    Graph graph;
    HashMap<String, ArrayList<Integer>> listOfExits;
    HashMap<Integer, String> listOfExitsById;
    ComboBox from;
    ComboBox to;

    public GlobalLogic (ComboBox from, ComboBox to) {
        this.from = from;
        this.to = to;
        listOfExitsById = new HashMap<>();
        XMLParser xmlParser = new XMLParser(listOfExitsById);
        graph = xmlParser.init();

        fillListOfExits();
        initComboBoxes();
    }

    private void initComboBoxes () {
        ObservableList<String> observableList =  FXCollections.observableArrayList();

        listOfExits.forEach((n, a) -> observableList.add(n));
        this.from.setItems(observableList);
        this.to.setItems(observableList);
    }

    private void fillListOfExits(){
        listOfExits = new HashMap<>();

        listOfExitsById.forEach((l,b) -> addExitToListOfExits(l,b));
    }

    private void addExitToListOfExits(int id, String name){
        System.out.println(id + name);
        ArrayList<Integer> list = listOfExits.get(name);
        if(list == null)
            list = new ArrayList<>();
        list.add(id);
        listOfExits.put(name, list);
    }
}
