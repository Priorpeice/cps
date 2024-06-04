package server.cps.common;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ResponseBody<T> {
    private final String statusCode;
    private final String description;
    private final String dateTime;
    private final T object;
    @Builder
    public ResponseBody(String statusCode, String description, String dateTime, T object) {
        this.statusCode = statusCode;
        this.description = description;
        this.dateTime = dateTime;
        this.object= object;
    }

}
