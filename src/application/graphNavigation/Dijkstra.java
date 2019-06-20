package application.graphNavigation;

public class Dijkstra implements Navigator{


    public void calculateShortestWay(Graph g, int startNode, int targetNode){

        startNode = g.getMatrixNodeNumberById(startNode);
        targetNode = g.getMatrixNodeNumberById(targetNode);

        int anzahlKnoten = g.nodes.length;

        Node[] knoten = g.nodes;
        int[][] matrix = g.autobahn;
        boolean[] besucht = new boolean[anzahlKnoten];
        int[] distanz = new int[anzahlKnoten];
        int[] kommtVon = new int[anzahlKnoten];

        {
            int knotenNummer, neueDistanz;
            String pfad;

            // Vorbereitung
            for (int i = 0; i < g.nodes.length; i++)
            {
                besucht[i]=false;
                distanz[i]=Integer.MAX_VALUE;
            }
            distanz[startNode] = 0;
            kommtVon[startNode] = startNode;

            // wiederhole bis alle Knoten besucht sind
            for (int i=0; i<anzahlKnoten; i++)
            {
                // der unbesuchte Knoten mit der minimalen Distanz wird zum aktiven Knoten

                knotenNummer = getPosMinNode(anzahlKnoten, besucht, distanz);
                System.out.println("Knoten mit min. Distanz zu Startknoten: " + knoten[knotenNummer].getId());
                //um nicht zu allen Knoten den kürzesten Weg vom Startknoten aus zu berechnen
                if(knoten[knotenNummer].getId() == knoten[targetNode].getId()){
                    break;
                }
                besucht[knotenNummer] = true;

                // f�r alle Abzweigungen vom aktiven Knoten zu unbesuchten Knoten
                for (int abzweigNummer = 0; abzweigNummer < anzahlKnoten; abzweigNummer++)
                {
                    if ((matrix[knotenNummer][abzweigNummer] > -1) && (!besucht[abzweigNummer]))
                    {
                        // die Distanz f�r den Weg �ber den aktiven Knoten berechnen
                        neueDistanz = distanz[knotenNummer] + matrix[knotenNummer][abzweigNummer];

                        // wenn diese Distanz kleiner ist als die des Knoten
                        if (neueDistanz < distanz[abzweigNummer])
                        {
                            // Distanz anpassen
                            distanz[abzweigNummer] = neueDistanz;
                            // g�nstige Richtung anpassen
                            kommtVon[abzweigNummer] = knotenNummer;

                            System.out.println("von " + knoten[knotenNummer].getId() + " zu " + knoten[abzweigNummer].getId() + " neue kuerzeste Distanz: " + neueDistanz);
                        }
                    }
                }
            }

            // Fertig! Die Entfernung ausgeben
            System.out.println("Entfernung: " + distanz[targetNode]);

            // Den Pfad des k�rzesten Weges r�ckw�rts, beim Ziel beginnend ausgeben
            pfad = String.valueOf(knoten[targetNode].getId());
            knotenNummer = targetNode;
            while (knotenNummer != startNode)
            {
                knotenNummer = kommtVon[knotenNummer];
                pfad = knoten[knotenNummer].getId() + "/" + pfad;
            }
            System.out.println("Weg: " + pfad);
        }
    }

    private int getPosMinNode(int anzahlKnoten, boolean[] besucht, int[] distanz) {
        int minPos = 0;
        int minWert = Integer.MAX_VALUE;

        for (int i=0; i<anzahlKnoten; i++)
        {
            if (!besucht[i] && (minWert > distanz[i]))
            {
                minWert = distanz[i];
                minPos = i;
            }
        }
        return minPos;
    }
}
