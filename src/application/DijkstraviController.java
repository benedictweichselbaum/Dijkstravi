package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;

import application.autocompleteComboBox.AutoCompleteComboBoxListener;
import application.globalLogic.GlobalLogic;
import application.routeDrawing.Zoomer;
import application.menuBarDialogs.instructionWindow.InstructionWindow;
import application.menuBarDialogs.informationWindow.InformationWindow;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;


public class DijkstraviController implements Initializable {

    @FXML
    public Button changeDir;
    public ToggleButton shortestPath;
    @FXML
    Label distance;
    @FXML
    public Label duration;

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
    private RadioButton rbSpfa;

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
        boolean fastestPath = !shortestPath.isSelected();
       if(algRadioButtonGroup.getSelectedToggle() == rbDijkstra)
           globalLogic.calculateWay(0, fastestPath);
       else if(algRadioButtonGroup.getSelectedToggle() == rbAStrern)
           globalLogic.calculateWay(1, fastestPath);
       else if(algRadioButtonGroup.getSelectedToggle() == rbBellmanFord)
           globalLogic.calculateWay(2, fastestPath);
       else if(algRadioButtonGroup.getSelectedToggle() == rbSpfa)
           globalLogic.calculateWay(3, fastestPath);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        algRadioButtonGroup = new ToggleGroup();
        rbAStrern.setToggleGroup(algRadioButtonGroup);
        rbBellmanFord.setToggleGroup(algRadioButtonGroup);
        rbDijkstra.setToggleGroup(algRadioButtonGroup);
        rbSpfa.setToggleGroup(algRadioButtonGroup);
        algRadioButtonGroup.selectToggle(rbDijkstra);
        File imageFile = null;
        try {
            Path temp = Files.createTempFile("picture-", "ext");
            Files.copy(this.getClass().getResourceAsStream("autobahnnetz_DE.png"), temp, StandardCopyOption.REPLACE_EXISTING);
            FileInputStream fileInputStream = new FileInputStream(temp.toFile());
            byte[] bytes = new byte[fileInputStream.available()];
            fileInputStream.read(bytes);
            fileInputStream.close();
            imageFile = new File ("tempPic.tmp");
            FileOutputStream outputStream = new FileOutputStream(imageFile);
            outputStream.write(bytes);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        javafx.scene.image.Image autobahnNetworkImage = new Image(imageFile.toURI().toString());
        imgViewAutobahn.setImage(autobahnNetworkImage);

        globalLogic = new GlobalLogic(this);

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
    public void pressedBeschreibung(ActionEvent actionEvent) {
        InstructionWindow instructionWindow = new InstructionWindow();
        instructionWindow.setVisible(true);
    }

    @FXML
    public void pressedOptions (ActionEvent actionEvent) {
        this.globalLogic.getOptionWindow().setVisible(true);
    }

    public Label getLblProgress() {
        return lblProgress;
    }

    @FXML
    public Label getLblDistance() {
        return distance;
    }

    @FXML
    public Label getLblDuration() {
        return duration;
    }

    public ProgressBar getPbAlgorithms() {
        return pbAlgorithms;
    }

    public TextArea getTxtAreaRoute() {
        return txtAreaRoute;
    }

    public ImageView getImgViewAutobahn() {
        return imgViewAutobahn;
    }

    public ComboBox getCbFrom() {
        return cbFrom;
    }

    public ComboBox getCbTo() {
        return cbTo;
    }

    public void clickedChangeDir(ActionEvent actionEvent) {
        globalLogic.changeDirection();
    }

    public void deleteDistanceAndDuration(){
        Platform.runLater(
                () -> {
                    getLblDistance().setText("");
                    getLblDuration().setText("");
                }
        );
    }

    public void disableFields(){
        getTxtAreaRoute().setDisable(true);
        btnCalcRoute.setDisable(true);
        getLblDistance().setDisable(true);
        getLblDuration().setDisable(true);

    }

    @FXML
    public void enableFields(){
        getTxtAreaRoute().setDisable(false);
        btnCalcRoute.setDisable(false);
        getLblDistance().setDisable(false);
        getLblDuration().setDisable(false);
    }
}

