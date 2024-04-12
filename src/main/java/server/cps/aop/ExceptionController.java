package server.cps.aop;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import server.cps.common.CpsResponse;
import server.cps.common.ResoponseBody;
import server.cps.exception.DockerException;
import server.cps.exception.MemberException;


@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler
    public ResponseEntity<ResoponseBody> entityError (final MemberException e )
    {
        return CpsResponse.toResponse(ErrorStatus.NOT_FOUND);
    }
    @ExceptionHandler
    public ResponseEntity<ResoponseBody> dockerError (final DockerException e )
    {
        return CpsResponse.toResponse(ErrorStatus.DOCKER_ERROR);
    }
}
