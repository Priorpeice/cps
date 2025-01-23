package server.cps.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CompilationLoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(CompilationLoggingAspect.class);

    @Pointcut("execution(* server.cps.compile.controller.IdeController.compileCode(..))")
    public void compileCodePointcut() {}

    @AfterReturning(pointcut = "compileCodePointcut()", returning = "result")
    public void logCompilationSuccess(Object result) {
        logger.info("Compilation completed successfully. Result: {}", result);
    }
}
