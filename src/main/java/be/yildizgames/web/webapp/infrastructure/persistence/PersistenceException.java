package be.yildizgames.web.webapp.infrastructure.persistence;

import be.yildizgames.common.exception.technical.TechnicalException;

public class PersistenceException extends TechnicalException{

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
