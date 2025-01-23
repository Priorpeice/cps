package server.cps.aop;

import lombok.Getter;
import server.cps.common.MessageFormat;
@Getter
public enum ErrorStatus implements MessageFormat {
    NOT_FOUND("ER01", "Not Found"),
    DOCKER_ERROR("DK01", "Docker has Down"),
    EXP_JWT_ERROR("JW01", "Expired Jwt"),
    NOT_JWT_ERROR("JW02", "Don't have Jwt"),
    NOT_FOUND_JWT_USER("JW03","invalid User"),
    FILE_NOT_FOUND("ER02", "File Not Found"),
    NOT_MATCHES_ID("LG01", "Not matches ID or Password"),
    NOT_MATCHES_PASSWORD("LG02", "Not matches ID or Password"),
    ;

    String statusCode;
    String message;

    ErrorStatus(String statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
