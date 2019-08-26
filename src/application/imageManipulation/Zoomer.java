package application.imageManipulation;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;


public class Zoomer{

    private ImageView imageView;
    private ScrollPane scrollPane;
    private final DoubleProperty zoomProperty = new SimpleDoubleProperty(200);

    public Zoomer(ImageView imgView, ScrollPane scrollPane){

        this.imageView = imgView;
        this.scrollPane = scrollPane;

        zoomProperty.addListener(arg0 -> {
            imageView.setFitWidth(zoomProperty.get() * 40);
            imageView.setFitHeight(zoomProperty.get() * 3);
        });
/*
        imageView.addEventFilter(ScrollEvent.ANY, event -> {
            if (event.getDeltaY() > 0) {
                zoomProperty.set(zoomProperty.get() * 1.1);
            } else if (event.getDeltaY() < 0 && imageView.fitWidthProperty().getValue() > 605) {
                zoomProperty.set(zoomProperty.get() / 1.1);
            }
        });
*/
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


        scrollPane.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            //System.out.println(event.getCode());
            if(event.getCode().toString().equals("ADD") || event.getCode().toString().equals("PLUS") || event.getCode().toString().equals("P"))
                zoomProperty.set(zoomProperty.get() * 1.1);
            else if((event.getCode().toString().equals("SUBTRACT") || event.getCode().toString().equals("MINUS") || event.getCode().toString().equals("M"))&& imageView.fitWidthProperty().getValue() > 605)
                zoomProperty.set(zoomProperty.get() / 1.1);
            else if((event.getCode().toString().equals("N") || event.getCode().toString().equals("Z")))
                while(imageView.fitWidthProperty().getValue() > 605)
                    zoomProperty.set(zoomProperty.get() / 1.1);
                else if(event.getCode().toString().equals("5"))
                zoomProperty.setValue(5);

                //javafx.scene.input.KeyCode()
            //System.out.println(event.getCode().toString());
        });

        imageView.preserveRatioProperty().set(true);
        scrollPane.setContent(imageView);
    }
}