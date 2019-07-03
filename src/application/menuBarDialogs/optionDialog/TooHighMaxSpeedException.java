package application.menuBarDialogs.optionDialog;

public class TooHighMaxSpeedException extends Exception {

    TooHighMaxSpeedException (){
        super();
    }

    @SuppressWarnings("unused")
    public TooHighMaxSpeedException(String message){
        super(message);
    }
}
