package ru.itis.tripbook.logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.LoggerFactory;

@Aspect
public class ExceptionLogger {
    @AfterThrowing(
            pointcut = "execution(* ru.itis.tripbook.service.UserServiceImpl.*(..))",
            throwing = "ex"
    )
    public void logAfterThrowingAllMethods(
            JoinPoint point,
            Exception ex
    ) {
        var className = point.getTarget().getClass().getCanonicalName();
        var LOGGER = LoggerFactory.getLogger(className);
        LOGGER.error(ex.getMessage());
    }
}
