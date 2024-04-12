package server.cps.exception;

import com.sun.jna.LastErrorException;
import lombok.Getter;

@Getter
public class DockerException extends LastErrorException {
    int status;

    public DockerException(String msg, int status) {
        super(msg);
        this.status = status;
    }

    public DockerException(int code, int status) {
        super(code);
        this.status = status;
    }

    public DockerException(int code, String msg, int status) {
        super(code, msg);
        this.status = status;
    }
}
