package server.cps.exception;

public class LoginPasswordException extends IllegalArgumentException{
    int status;
    public LoginPasswordException(String msg, int status) {
        super(msg);
        this.status = status;
    }
    public LoginPasswordException(String msg, int status, Throwable t) {
        super(msg,t);
        this.status = status;
    }


}
