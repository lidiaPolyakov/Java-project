package il.ac.shenkar.model;

/* Custom exception class for modeling exceptions in the application */

public class ModelException extends Exception {
    public ModelException(String message) {
        super(message);
    }

    public ModelException(String message, Throwable cause) {
        super(message, cause);
    }
}
