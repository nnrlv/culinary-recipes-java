package exceptions;

public class EmailAlreadyTakenException extends Throwable {
    public EmailAlreadyTakenException(String message) {
        super(message);
    }
}
