package application;

import application.starterProgressDialog.GraphCreater;
import javafx.application.Application;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application{

    private Stage dialog;
    public boolean creatingReady = false;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("application.fxml"));
        primaryStage.setTitle("Djikstravi");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.show();

        showCreatingDialog(primaryStage);
    }

    private  void showCreatingDialog(Stage primaryStage) {
        this.dialog = new Stage();
        dialog.setTitle("Graph berechnen");
        dialog.initStyle(StageStyle.DECORATED);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);
        VBox dialogVbox = new VBox(10);
        dialogVbox.setSpacing(10);
        dialogVbox.setPadding(new Insets(5));
        dialogVbox.setAlignment(Pos.CENTER);
        Text text = new Text("Die XML-Datei wird eingelesen");
        dialogVbox.getChildren().add(text);
        ProgressBar progressBar = new ProgressBar(0);
        progressBar.setMaxWidth(310);
        dialogVbox.getChildren().add(progressBar);
        Scene dialogScene = new Scene(dialogVbox, 350, 100);
        dialog.setScene(dialogScene);

        GraphCreater graphCreater = new GraphCreater(progressBar, text, dialog);
        graphCreater.start();
        dialog.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
