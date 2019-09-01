package application.routeDrawing;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;

/*
 * This class enables the zooming of the map.
 */
public class Zoomer{

    private ImageView imageView;
    private final DoubleProperty zoomProperty = new SimpleDoubleProperty(200);

    public Zoomer(ImageView imgView, ScrollPane scrollPane){

        this.imageView = imgView;

        zoomProperty.addListener(arg0 -> {
            imageView.setFitWidth(zoomProperty.get() * 4);
            imageView.setFitHeight(zoomProperty.get() * 3);
        });

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
        });

        imageView.preserveRatioProperty().set(true);
        scrollPane.setContent(imageView);
    }
}