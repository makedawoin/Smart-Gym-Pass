public class InvalidPassException extends Exception {

    public InvalidPassException(String message) {
        super(message);
    }

    public InvalidPassException(String message, Throwable cause) {
        super(message, cause);
    }
}