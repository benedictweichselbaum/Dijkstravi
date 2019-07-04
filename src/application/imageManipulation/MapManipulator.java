package application.imageManipulation;

import application.graphNavigation.Node;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * This Class provides a method to manipulate the map image that can be seen in the UI.
 * It takes a list of Nodes an draws a line between them.
 **/

public class MapManipulator {

    public static Image drawWayWithListOfNodes (Image image, List<Node> listOfNodes) {
        Image multipleManipulatedImage = image;
        for (int i = 0; i <= listOfNodes.size()-2; i++) {
            multipleManipulatedImage = drawWayWithTwoNodes(multipleManipulatedImage,
                                                            listOfNodes.get(i),
                                                            listOfNodes.get(i + 1));
        }
        return multipleManipulatedImage;
    }

    private static Image drawWayWithTwoNodes (Image imageToManipulate, Node node1, Node node2) {
        List<Pixel> pixelWay = createListOfPixelsToMarkFromTwoCoordinates(node1.getLatitude(),
                                                                            node1.getLongitude(),
                                                                            node2.getLatitude(),
                                                                            node2.getLongitude(),
                                                                            imageToManipulate);
        PixelReader pixelReader = imageToManipulate.getPixelReader();
        WritableImage writableImage = new WritableImage(
                pixelReader,
                (int) imageToManipulate.getWidth(),
                (int) imageToManipulate.getHeight()
        );
        PixelWriter pixelWriter = writableImage.getPixelWriter();

        Color markingColor = Color.RED;

        for (Pixel pixel : pixelWay) {
            pixelWriter.setColor(pixel.getX(), pixel.getY(), markingColor);
            for (int i = 1; i <= (int) (imageToManipulate.getWidth()*0.0025); i++) {
                pixelWriter.setColor(pixel.getX(), pixel.getY() + i, markingColor);
                pixelWriter.setColor(pixel.getX(), pixel.getY() - i, markingColor);
                pixelWriter.setColor(pixel.getX() + i, pixel.getY(), markingColor);
                pixelWriter.setColor(pixel.getX() - i, pixel.getY(), markingColor);
            }
        }
        return writableImage;
    }

    private static List<Pixel> createListOfPixelsToMarkFromTwoCoordinates (double lat1,
                                                                           double lon1,
                                                                           double lat2,
                                                                           double lon2,
                                                                           Image image) {
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();

        int latPx1 = latitudeToPixel(lat1, height);
        int lonPx1 = longitudeToPixel(lon1, width);
        int latPx2 = latitudeToPixel(lat2, height);
        int lonPx2 = longitudeToPixel(lon2, width);

        List<Pixel> listOfPixels = new ArrayList<>();
        double m;
        double t;
        /*
         * The goal is to calculate the remaining pixels between the two given points
         * by calculating the linear function that describes a line between the points.
         * f(x) = mx+t
         */
        if (lonPx2 > lonPx1)
            m = (double) (latPx2 - latPx1)/(lonPx2 - lonPx1);
        else if (lonPx2 < lonPx1)
            m = (double) (latPx1 - latPx2)/(lonPx1 - lonPx2);
        else
            return calculateListOfPixelsIfAFunctionIsNotPossible(lonPx1, latPx1, latPx2, listOfPixels);

        t = (double) latPx1-(m * lonPx1);

        if (lonPx1 > lonPx2) {
            for (int x = lonPx2; x <= lonPx1; x++) {
                int y = (int) Math.round(m * x + t);
                listOfPixels.add(new Pixel(x, y));
            }
        } else {
            for (int x = lonPx1; x <= lonPx2; x++) {
                int y = (int) Math.round(m * x + t);
                listOfPixels.add(new Pixel(x, y));
            }
        }

        return listOfPixels;
    }

    private static List<Pixel> calculateListOfPixelsIfAFunctionIsNotPossible(int lon, int lat1, int lat2,
                                                                             List<Pixel> listObject) {
        int difference;

        if (lat1 > lat2) {
            difference = lat1 - lat2;
            for (int i = 0; i <= difference; i++) {
                listObject.add(new Pixel(lon, lat2 + i));
            }
        } else {
            difference = lat2 - lat1;
            for (int i = 0; i <= difference; i++) {
                listObject.add(new Pixel(lon, lat1 + i));
            }
        }
        return listObject;
    }

    private static int latitudeToPixel (double latitude, int pixelY) {
        return (int) Math.round((55.095 - latitude)/((55.095 - 47.175)/pixelY));
    }

    private static int longitudeToPixel(double longitude, int pixelX) {
        return (int) Math.round((longitude - 5.485)/((15.447-5.485)/pixelX));
    }
}
