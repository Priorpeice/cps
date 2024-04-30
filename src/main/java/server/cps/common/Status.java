package server.cps.common;

import lombok.Getter;

@Getter
public enum Status implements MessageFormat {
    RUN("RU01", "execution clear"),
    SUCCESS("SU01","finish"),
    CREATE("CR01", "save clear"),
    READ("RE01", "find clear"),
    UPDATE("UP01","change clear"),
    DELETE("DE01","delete clear"),
    LLAMA("LL01", "verification clear"),
    ;
    String statusCode;
    String message;

    Status(String statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
