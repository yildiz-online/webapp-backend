package be.yildizgames.web.webapp.infrastructure.persistence;

public class PersistenceException extends IllegalStateException{

    PersistenceException(String message, Exception cause) {
        super(message, cause);
    }

    PersistenceException(Exception cause) {
        super(cause);
    }

    PersistenceException(String s) {
        super(s);
    }
}
