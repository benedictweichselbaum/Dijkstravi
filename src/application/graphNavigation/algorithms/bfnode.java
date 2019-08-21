package application.graphNavigation.algorithms;

public class bfnode {
    private static final int INFINITE = 2000000000;
    int dist;
    int predecessor;

    public bfnode(){
        dist = INFINITE;
        predecessor = INFINITE;
    }
}
