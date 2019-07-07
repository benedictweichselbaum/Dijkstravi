package application.menuBarDialogs.optionDialog.customOptionWindowExceptions;

public class TooHighMaxSpeedException extends Exception {

    public TooHighMaxSpeedException (){
        super();
    }

    @SuppressWarnings("unused")
    public TooHighMaxSpeedException(String message){
        super(message);
    }
}
