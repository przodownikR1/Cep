package pl.java.scalatech.exception;

/**
 * @author Sławomir Borowiec 
 * Module name : Cep
 * Creating time :  5 wrz 2014 11:13:58
 
 */
public class StreamOperationException extends RuntimeException {

    private static final long serialVersionUID = -2739398400237154735L;

    public StreamOperationException() {
        super();
    }

    public StreamOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public StreamOperationException(String message) {
        super(message);
    }

    public StreamOperationException(Throwable cause) {
        super(cause);
    }
    
}
