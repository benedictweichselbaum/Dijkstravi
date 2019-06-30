package application.imageManipulation;

import application.graphNavigation.Node;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

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

    private static Image drawWayWithTwoNodes (Image mapToManipulate, Node node1, Node node2) {
        List<Pixel> pixelWay = createListOfPixelsToMarkFromTwoCoordinates(node1.getLatitude(),
                                                                            node1.getLongitude(),
                                                                            node2.getLatitude(),
                                                                            node2.getLongitude());

        PixelReader pixelReader = mapToManipulate.getPixelReader();
        WritableImage writableImage = new WritableImage(
                (int) mapToManipulate.getWidth(),
                (int) mapToManipulate.getHeight()
        );
        PixelWriter pixelWriter = writableImage.getPixelWriter();

        System.out.println(mapToManipulate.getWidth());
        System.out.println(mapToManipulate.getHeight() + "H");

        for (int readY = 0; readY < mapToManipulate.getHeight(); readY++) {
            for (int readX = 0; readX < mapToManipulate.getWidth(); readX++) {
                Color color = pixelReader.getColor(readX, readY);
                pixelWriter.setColor(readX, readY, color);
            }
        }

        Color markingColor = Color.RED;

        for (Pixel pixel : pixelWay) {
            pixelWriter.setColor(pixel.getX(), pixel.getY(), markingColor);
            pixelWriter.setColor(pixel.getX(), pixel.getY()+1, markingColor);
            pixelWriter.setColor(pixel.getX(), pixel.getY()-1, markingColor);
            pixelWriter.setColor(pixel.getX()+1, pixel.getY(), markingColor);
            pixelWriter.setColor(pixel.getX()-1, pixel.getY(), markingColor);
        }

        return writableImage;
    }

    private static List<Pixel> createListOfPixelsToMarkFromTwoCoordinates (double lat1, double lon1, double lat2, double lon2) {
        int latPx1 = latitudeToPixel(lat1);
        int lonPx1 = lonitudeToPixel(lon1);
        int latPx2 = latitudeToPixel(lat2);
        int lonPx2 = lonitudeToPixel(lon2);

        List<Pixel> listOfPixels = new ArrayList<>();
        double m;
        double t;

        if (lonPx2 > lonPx1)
            m = (double) (latPx2 - latPx1)/(lonPx2 - lonPx1);
        else if (lonPx2 < lonPx1)
            m = (double) (latPx1 - latPx2)/(lonPx1 - lonPx2);
        else
            return claculateListOfPixelsIfAFunctionIsNotPossible(lonPx1, latPx1, latPx2, listOfPixels);

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

    private static List<Pixel> claculateListOfPixelsIfAFunctionIsNotPossible (int lon, int lat1, int lat2,
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

    private static int latitudeToPixel (double latitude) {
        return (int) Math.round((55.078 - latitude)/0.010955556);
    }

    private static int lonitudeToPixel (double longitude) {
        return (int) Math.round((longitude - 5.493)/0.016434926);
    }
}
