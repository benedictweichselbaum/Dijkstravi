package application.graphNavigation.algorithms.navigationExceptions;
/**
 * This exception is thrown if there is no way between start and end.
 */
public class NoWayFoundException extends Exception {

    public NoWayFoundException () {
        super();
    }

    @SuppressWarnings("unused")
    public NoWayFoundException (String message) {
        super(message);
    }
}