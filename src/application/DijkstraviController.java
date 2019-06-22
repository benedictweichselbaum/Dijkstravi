package application;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import application.graphNavigation.Node;
import application.imageManipulation.MapManipulator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class DijkstraviController implements Initializable {

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
    private TextField txtFrom;

    @FXML
    private TextField txtTo;

    @FXML
    private ImageView imgViewAutobahn;

    @FXML
    private MenuItem btnExit;

    private ToggleGroup algRadioButtonGroup;
    private MapManipulator mapManipulator;

    @FXML
    void clickedCalcRoute(ActionEvent event) {
        txtAreaRoute.setText("Hello, you just have pressed a Button.\nYou are Great!");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        algRadioButtonGroup = new ToggleGroup();
        rbAStrern.setToggleGroup(algRadioButtonGroup);
        rbBellmanFord.setToggleGroup(algRadioButtonGroup);
        rbDijkstra.setToggleGroup(algRadioButtonGroup);
        rbMinPlusMma.setToggleGroup(algRadioButtonGroup);

        File imageFile = new File("src/autobahnnetz_DE.png");
        Image autobahnNetworkImage = new Image(imageFile.toURI().toString());
        imgViewAutobahn.setImage(autobahnNetworkImage);

        Node node1 = new Node(1, false, 11.035000, 49.407000);
        Node node2 = new Node(2, false, 8.6913000, 48.4442300);

        imgViewAutobahn.setImage(MapManipulator.drawWayWithTwoNodes(autobahnNetworkImage, node1, node2));
    }

    public void pressedExit(ActionEvent actionEvent) {
        System.exit(0);
    }
}

