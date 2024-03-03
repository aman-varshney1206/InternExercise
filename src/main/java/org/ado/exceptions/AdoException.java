package org.ado.exceptions;

/***
 * Throws the exception from REST API
 */
public class AdoException extends Exception {
    public AdoException() {
        super("Cannot validate the arguments passed for the parameters; Please pass the correct values and try again.");
    }

    public AdoException(Throwable cause) {
        super(cause);
    }

    public AdoException(String message) {
        super("An Error Occurred: " + message);
    }

    public AdoException(String exceptionType, String message) {
        super(exceptionType + ": " + message);
    }
}
