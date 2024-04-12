package server.cps.exception;

import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;

@Getter
public class MemberException extends EntityNotFoundException {
    private Integer status;

    public MemberException(Integer status) {
        this.status = status;
    }

    public MemberException(Exception cause, Integer status) {
        super(cause);
        this.status = status;
    }

    public MemberException(String message, Integer status) {
        super(message);
        this.status = status;
    }

    public MemberException(String message, Exception cause, Integer status) {
        super(message, cause);
        this.status = status;
    }
}
