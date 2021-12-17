package by.tms.aqvatoria.exception;

public class AqvatoriaThreadException extends Exception {
    public AqvatoriaThreadException() {
        super();
    }

    public AqvatoriaThreadException(String message) {
        super(message);
    }

    public AqvatoriaThreadException(String message, Throwable cause) {
        super(message, cause);
    }

    public AqvatoriaThreadException(Throwable cause) {
        super(cause);
    }
}
