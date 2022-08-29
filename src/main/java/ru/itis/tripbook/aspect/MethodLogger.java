package ru.itis.tripbook.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.itis.tripbook.controller.AdminController;

@Aspect
@Component
public class MethodLogger {

    @Around("@annotation(ru.itis.tripbook.annotation.Loggable)")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        var start = System.currentTimeMillis();
        var result = point.proceed();
        var finish= System.currentTimeMillis();

        var className = point.getTarget().getClass().getCanonicalName();
        var LOGGER = LoggerFactory.getLogger(className);
        LOGGER.info(
                "#{}({}): {} in {}ms",
                ((MethodSignature) point.getSignature()).getMethod().getName(),
                point.getArgs(),
                result,
                finish - start
        );
        return result;
    }

    @Around("@annotation(ru.itis.tripbook.annotation.ResultLoggable)")
    public Object aroundResult(ProceedingJoinPoint point) throws Throwable {
        var start = System.currentTimeMillis();
        var result = point.proceed();
        var finish= System.currentTimeMillis();

        var className = point.getTarget().getClass().getCanonicalName();
        var LOGGER = LoggerFactory.getLogger(className);
        LOGGER.info(
                "#{}: {} in {}ms",
                ((MethodSignature) point.getSignature()).getMethod().getName(),
                result,
                finish - start
        );
        return result;
    }
    @Around("@annotation(ru.itis.tripbook.annotation.SignatureLoggable)")
    public Object aroundSignature(ProceedingJoinPoint point) throws Throwable {
        var start = System.currentTimeMillis();
        var result = point.proceed();
        var finish= System.currentTimeMillis();

        var className = point.getTarget().getClass().getCanonicalName();
        var LOGGER = LoggerFactory.getLogger(className);
        LOGGER.info(
                "#{}({}) in {}ms",
                ((MethodSignature) point.getSignature()).getMethod().getName(),
                point.getArgs(),
                finish - start
        );
        return result;
    }
    @Around("@annotation(ru.itis.tripbook.annotation.ExecutionLoggable)")
    public Object aroundExecution(ProceedingJoinPoint point) throws Throwable {
        var start = System.currentTimeMillis();
        var result = point.proceed();
        var finish= System.currentTimeMillis();

        var className = point.getTarget().getClass().getCanonicalName();
        var LOGGER = LoggerFactory.getLogger(className);
        LOGGER.info(
                "#{} in {}ms",
                ((MethodSignature) point.getSignature()).getMethod().getName(),
                finish - start
        );
        return result;
    }
}
