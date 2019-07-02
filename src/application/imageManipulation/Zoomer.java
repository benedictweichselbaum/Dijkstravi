package application.imageManipulation;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.stage.Stage;


public class Zoomer{

    private ImageView imageView;
    private ScrollPane scrollPane;
    private final DoubleProperty zoomProperty = new SimpleDoubleProperty(200);

    public Zoomer(ImageView imgView, ScrollPane scrollPane){
        this.imageView = imgView;
        this.scrollPane = scrollPane;

        zoomProperty.addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable arg0) {
                imageView.setFitWidth(zoomProperty.get() * 4);
                imageView.setFitHeight(zoomProperty.get() * 3);
            }
        });

        imageView.addEventFilter(ScrollEvent.ANY, new EventHandler<>() {
            @Override
            public void handle(ScrollEvent event) {
                //System.out.println("Breite: " + imageView.fitWidthProperty().getValue());
                if (event.getDeltaY() > 0) {
                    zoomProperty.set(zoomProperty.get() * 1.1);
                } else if (event.getDeltaY() < 0 && imageView.fitWidthProperty().getValue() > 605) {
                    zoomProperty.set(zoomProperty.get() / 1.1);
                }
            }
        });

        scrollPane.addEventFilter(ScrollEvent.ANY, new EventHandler<>() {
            @Override
            public void handle(ScrollEvent scrollEvent) {
                //System.out.println(scrollPane.getTranslateX() + "<T  >" + scrollPane.getViewportBounds().toString());
            }
        });

        scrollPane.setDisable(true);
/* Noch nicht löschen
        scrollPane.addEventFilter(Event.ANY, new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                System.out.println(event.toString());
            }
        });
 */
        //scrollPane.getEventDispatcher()

        imageView.preserveRatioProperty().set(true);
        scrollPane.setContent(imageView);
    }
}