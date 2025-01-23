package server.cps.aop;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import server.cps.exception.DockerException;
import server.cps.exception.LoginIdException;
import server.cps.exception.LoginPasswordException;
import server.cps.exception.MemberException;

@Aspect
@Component
public class ExceptionLoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionLoggingAspect.class);

    @AfterThrowing(value = "execution(* server.cps.aop.ExceptionController.entityError(..))", throwing = "e")
    public void logMemberException(MemberException e) {
        logger.error("MemberException occurred: {}", e.getMessage(), e);
    }

    @AfterThrowing(value = "execution(* server.cps.aop.ExceptionController.dockerError(..))", throwing = "e")
    public void logDockerException(DockerException e) {
        logger.error("DockerException occurred: {}", e.getMessage(), e);
    }

    @AfterThrowing(value = "execution(* server.cps.aop.ExceptionController.loginPasswordError(..))", throwing = "e")
    public void logLoginPasswordException(LoginPasswordException e) {
        logger.error("LoginPasswordException occurred: {}", e.getMessage(), e);
    }

    @AfterThrowing(value = "execution(* server.cps.aop.ExceptionController.loginIdError(..))", throwing = "e")
    public void logLoginIdException(LoginIdException e) {
        logger.error("LoginIdException occurred: {}", e.getMessage(), e);
    }

}
