package projectzero.exceptions;

// todo create ApplicationException as parent ex in you program
public class AlreadyExistsException extends ApplicationException {
    public AlreadyExistsException() {
        super();
    }

    public AlreadyExistsException(String message) {
        super(message);
    }
}
