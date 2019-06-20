package application.graphNavigation;

public class NodeObj {
    private int knotenNummer;
    private int distanz;

    public NodeObj(int knotenNummer, int distanz){
        setKnotenNummer(knotenNummer);
        setDistanz(distanz);
    }

    public int getDistanz() {
        return distanz;
    }

    public void setDistanz(int distanz) {
        this.distanz = distanz;
    }

    public int getKnotenNummer() {
        return knotenNummer;
    }

    public void setKnotenNummer(int knotenNummer) {
        this.knotenNummer = knotenNummer;
    }
}
