package server.cps.aop;

import lombok.Getter;
import server.cps.common.MessageFormat;
@Getter
public enum ErrorStatus implements MessageFormat {
    NOT_FOUND("ER01", "Not Found")
    ;

    String statusCode;
    String message;

    ErrorStatus(String statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
