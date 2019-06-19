package application.xmlParser;
import java.io.*;
import java.util.*;

import application.graphNavigation.Graph;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class JDOMTest {


    public static void main(String[] args) {
      //  XMLParser xp = new XMLParser();
       // xp.runParser();

        XMLaltTest xp = new XMLaltTest();
        xp.init();
       // -Xmx1024m
    //arraytest();
    }

    public static void arraytest(){
        for (int i = 100000000; i < 2100000000; i += 2000000)
        {
            System.out.println(i);
            double[] d = new double[i];
        }
    }

    @Deprecated
    public static void quellcodeVonDavor(){
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

            for (Element way : wayList) {
                //Element way = wayList.get(i);
                //System.out.println("\nAktuelles Element :"
                // + way.getName());
                if(way.getName().equals("way")) {
                    ways++;

                    Attribute wayid = way.getAttribute("id");
                    System.out.println("ID: " + wayid.getValue());
                    List<Element> ndtagList = way.getChildren();
                    for (int j = 0; j < ndtagList.size(); j++) {
                        //System.out.println(j);
                        Element ndtag = ndtagList.get(j);
                        if (ndtag.getName().equals("nd")) {
                            Attribute nd = ndtag.getAttribute("ref");
                            System.out.println("node " + j + ": " + nd.getValue());
                        }else if(ndtag.getName().equals("tag")){
                            Attribute k = ndtag.getAttribute("k");
                            Attribute v = ndtag.getAttribute("v");
                            if(k.getValue().equals("highway")) {
                                System.out.println("Typ: " + v.getValue());
                            }
                        }
                    }
                    System.out.println("---------------------");

                }
                else if(way.getName().equals("node")){
                    nodes++;

                    Attribute nodeid =  way.getAttribute("id");
                    Attribute nodelat =  way.getAttribute("lat");
                    Attribute nodelon =  way.getAttribute("lon");

                    // System.out.println("ID: " + nodeid.getValue() + " lat: " + nodelat.getValue() + " lon: " + nodelon.getValue());
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
