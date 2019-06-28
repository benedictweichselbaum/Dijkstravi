package application.globalLogic;

import application.graphNavigation.Graph;
import application.starterProgressDialog.GraphCreater;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

import javax.swing.*;
import java.awt.*;
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
        this.showCreatingDialog();
        fillListOfExits();
        initComboBoxes();
    }

    private void initComboBoxes () {
        ObservableList<String> observableList =  FXCollections.observableArrayList();

        listOfExits.forEach((n, a) -> observableList.add(n));
        this.from.setItems(observableList.sorted());
        this.to.setItems(observableList.sorted());
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

    private void showCreatingDialog() {
        JFrame jFrame = new JFrame("Graph berechnen");
        Container pane = jFrame.getContentPane();
        JProgressBar jProgressBar = new JProgressBar(0, 100);
        jProgressBar.setValue(0);
        JLabel jLabel = new JLabel("Der Graph wird berechnet...");
        jFrame.setLayout(new FlowLayout());
        jFrame.setLocation(800, 400);

        pane.add(jLabel);
        pane.add(jProgressBar);

        jFrame.setSize(350, 100);
        jFrame.setResizable(false);
        jFrame.setVisible(true);

        GraphCreater graphCreater = new GraphCreater(jProgressBar, jLabel, listOfExitsById);

        graphCreater.start();

        synchronized (graphCreater) {
            try {
                graphCreater.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        this.graph = graphCreater.getGraph();
        jFrame.dispose();
    }
}
