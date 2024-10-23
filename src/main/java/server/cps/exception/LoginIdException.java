package server.cps.exception;

import jakarta.persistence.EntityNotFoundException;

public class LoginIdException extends EntityNotFoundException {
    private Integer status;

    public LoginIdException(Integer status) {
        this.status = status;
    }

    public LoginIdException(Exception cause, Integer status) {
        super(cause);
        this.status = status;
    }

    public LoginIdException(String message, Integer status) {
        super(message);
        this.status = status;
    }

    public LoginIdException(String message, Exception cause, Integer status) {
        super(message, cause);
        this.status = status;
    }
}
