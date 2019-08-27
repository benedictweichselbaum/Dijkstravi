package application.graphNavigation.algorithms.exceptions;

public class NoWayFoundException extends Exception {

    public NoWayFoundException () {
        super();
    }

    @SuppressWarnings("unused")
    public NoWayFoundException (String message) {
        super(message);
    }
}