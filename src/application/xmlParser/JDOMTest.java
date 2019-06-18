package application.xmlParser;
import java.io.*;
import java.util.*;
import org.jdom2.*;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class JDOMTest {

    public static void main(String[] args) {

        try {
            File inputFile = new File("german_autobahn.osm");
            SAXBuilder saxBuilder = new SAXBuilder();
            Document document = saxBuilder.build(inputFile);
            System.out.println("Root element :" + document.getRootElement().getName());
            Element classElement = document.getRootElement();

            List<Element> wayList = classElement.getChildren();
            System.out.println("----------------------------");

            System.out.println("Anzahl Wegpunkte:" + wayList.size());

            System.out.println("----------------------------");
            System.out.println("Erste 10 node Einträge");

            int ways = 0;
            int nodes = 0;
            int others = 0;

            for (int i = 0; i < wayList.size(); i++) {
                Element way = wayList.get(i);
                //System.out.println("\nAktuelles Element :"
                       // + way.getName());
                if(way.getName().equals("way"))
                    ways++;
                else if(way.getName().equals("node")){
                    nodes++;
                    if(nodes < 10) {
                        Attribute nodeid =  way.getAttribute("id");
                        Attribute nodelat =  way.getAttribute("lat");
                        Attribute nodelon =  way.getAttribute("lon");

                        System.out.println("ID: " + nodeid.getValue() + " lat: " + nodelat.getValue() + " lon: " + nodelon.getValue());
                    }
                    }
                else
                    others++;


            }
            System.out.println("Wege: " + ways + " Knoten: " + nodes + " Andere: " + others);



        } catch(JDOMException e) {
            e.printStackTrace();
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
