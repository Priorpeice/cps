package server.cps.common;

import lombok.Getter;

@Getter
public enum Status implements MessageFormat {
    RUN("R01", "execution clear"),
    SUCCESS("S01","finish")
    ;
    String statusCode;
    String message;

    Status(String statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
