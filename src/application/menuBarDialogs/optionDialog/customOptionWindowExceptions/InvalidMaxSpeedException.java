package application.menuBarDialogs.optionDialog.customOptionWindowExceptions;

public class InvalidMaxSpeedException extends Exception {

    public InvalidMaxSpeedException () {
        super();
    }

    @SuppressWarnings("unused")
    public InvalidMaxSpeedException (String message) {
        super(message);
    }
}
