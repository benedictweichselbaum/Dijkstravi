package application.imageManipulation;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;


public class Zoomer{

    private ImageView imageView;
    private ScrollPane scrollPane;
    private final DoubleProperty zoomProperty = new SimpleDoubleProperty(200);

    public Zoomer(ImageView imgView, ScrollPane scrollPane){

        this.imageView = imgView;
        this.scrollPane = scrollPane;

        zoomProperty.addListener(arg0 -> {
            imageView.setFitWidth(zoomProperty.get() * 4);
            imageView.setFitHeight(zoomProperty.get() * 3);
        });

        imageView.addEventFilter(ScrollEvent.ANY, event -> {
            if (event.getDeltaY() > 0) {
                zoomProperty.set(zoomProperty.get() * 1.1);
            } else if (event.getDeltaY() < 0 && imageView.fitWidthProperty().getValue() > 605) {
                zoomProperty.set(zoomProperty.get() / 1.1);
            }
        });

        scrollPane.addEventFilter(ScrollEvent.ANY, scrollEvent -> {
            //System.out.println(scrollPane.getTranslateX() + "<T  >" + scrollPane.getViewportBounds().toString());
        });

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