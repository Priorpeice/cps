package server.cps.aop;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import server.cps.common.CpsResponse;
import server.cps.common.ResponseBody;
import server.cps.exception.DockerException;
import server.cps.exception.LoginIdException;
import server.cps.exception.LoginPasswordException;
import server.cps.exception.MemberException;


@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler
    public ResponseEntity<ResponseBody> entityError (final MemberException e )
    {
        return CpsResponse.toResponse(ErrorStatus.NOT_FOUND);
    }
    @ExceptionHandler
    public ResponseEntity<ResponseBody> dockerError (final DockerException e )
    {
        return CpsResponse.toResponse(ErrorStatus.DOCKER_ERROR);
    }
    @ExceptionHandler
    public ResponseEntity<ResponseBody> loginPasswordError (final LoginPasswordException e )
    {
        return CpsResponse.toResponse(ErrorStatus.NOT_MATCHES_PASSWORD,400);
    }
    @ExceptionHandler
    public ResponseEntity<ResponseBody> loginIdError (final LoginIdException e )
    {
        return CpsResponse.toResponse(ErrorStatus.NOT_MATCHES_ID,400);
    }
}
