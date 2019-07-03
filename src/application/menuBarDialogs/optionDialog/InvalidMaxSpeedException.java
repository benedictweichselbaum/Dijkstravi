package application.menuBarDialogs.optionDialog;

public class InvalidMaxSpeedException extends Exception {

    InvalidMaxSpeedException () {
        super();
    }

    @SuppressWarnings("unused")
    public InvalidMaxSpeedException (String message) {
        super(message);
    }
}
