package application;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import application.autocompleteComboBox.AutoCompleteComboBoxListener;
import application.globalLogic.GlobalLogic;
import application.graphNavigation.Node;
import application.imageManipulation.MapManipulator;
import application.imageManipulation.Zoomer;
import application.menuBarDialogs.informationWindow.InformationWindow;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;


public class DijkstraviController implements Initializable {

    @FXML
    AnchorPane pane;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnCalcRoute;

    @FXML
    private RadioButton rbAStrern;

    @FXML
    private RadioButton rbBellmanFord;

    @FXML
    private RadioButton rbDijkstra;

    @FXML
    private RadioButton rbMinPlusMma;

    @FXML
    private TextArea txtAreaRoute;

    @FXML
    private ImageView imgViewAutobahn;

    @FXML
    private MenuItem btnExit;

    @FXML
    private ProgressBar pbAlgorithms;

    @FXML
    private Label lblProgress;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private ComboBox cbFrom;

    @FXML ComboBox cbTo;

    private ToggleGroup algRadioButtonGroup;
    private GlobalLogic globalLogic;
    private Zoomer zoomer;

    @FXML
    void clickedCalcRoute(ActionEvent event) {
       if(algRadioButtonGroup.getSelectedToggle() == rbDijkstra)
           txtAreaRoute.setText(globalLogic.calculateWay(0));
       else if(algRadioButtonGroup.getSelectedToggle() == rbAStrern)
           txtAreaRoute.setText(globalLogic.calculateWay(1));
       else if(algRadioButtonGroup.getSelectedToggle() == rbBellmanFord)
           txtAreaRoute.setText(globalLogic.calculateWay(2));
       else if(algRadioButtonGroup.getSelectedToggle() == rbMinPlusMma)
           txtAreaRoute.setText(globalLogic.calculateWay(3));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        algRadioButtonGroup = new ToggleGroup();
        rbAStrern.setToggleGroup(algRadioButtonGroup);
        rbBellmanFord.setToggleGroup(algRadioButtonGroup);
        rbDijkstra.setToggleGroup(algRadioButtonGroup);
        rbMinPlusMma.setToggleGroup(algRadioButtonGroup);
        algRadioButtonGroup.selectToggle(rbDijkstra);

        File imageFile = new File("src/autobahnnetz_DE.png");
        Image autobahnNetworkImage = new Image(imageFile.toURI().toString());

        Node node1 = new Node(1, false, 49.407000,11.035000,  "");
        Node node2 = new Node(2, false,  48.4442300, 8.6913000, "");

        List<Node> listOfNodes = new ArrayList<>();
        listOfNodes.add(node1);
        listOfNodes.add(node2);

        imgViewAutobahn.setImage(MapManipulator.drawWayWithListOfNodes(autobahnNetworkImage, listOfNodes));

        globalLogic = new GlobalLogic(cbFrom, cbTo);

        new AutoCompleteComboBoxListener<>(cbFrom);
        new AutoCompleteComboBoxListener<>(cbTo);

        zoomer = new Zoomer(imgViewAutobahn, scrollPane);
    }

    @FXML
    public void pressedExit(ActionEvent actionEvent) {
        System.exit(0);
    }

    @FXML
    public void textChangedInComboBoxFrom (ActionEvent actionEvent) {
    }

    @FXML
    public void textChangedInComboBoxTo (ActionEvent actionEvent) {

    }

    @FXML
    public void pressedInformation (ActionEvent actionEvent) {
        InformationWindow informationWindow = new InformationWindow();
        informationWindow.setVisible(true);
    }

    @FXML
    public void pressedOptions (ActionEvent actionEvent) {
        this.globalLogic.getOptionWindow().setVisible(true);
    }
}

