package com.example.excelfileuploading.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import java.time.Duration;
import java.time.Instant;

@Slf4j
@Aspect
@Component
public class LoggerAspect {

    @Around("@annotation(com.example.excelfileuploading.interfaces.LogAspect)")
    public void logWithAnnotation(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info(joinPoint.toString() + " method execution start");
        Instant start = Instant.now();
        joinPoint.proceed();
        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();
        log.info("Time took to execute the method : "+timeElapsed);
        log.info(joinPoint.getSignature().toString() + " method execution end");
    }

    @AfterThrowing(value = "execution(* com.example.excelfileuploading.*.*(..))",throwing = "ex")
    public void logException(JoinPoint joinPoint, Exception ex) {
        log.error(joinPoint.getSignature()+ " An exception happened due to : "+ex.getMessage());
    }

    @AfterReturning(value = "execution(* com.example.excelfileuploading.utility.*.*(..))",returning = "retVal")
    public void logStatus(JoinPoint joinPoint,Object retVal) {
        log.info(joinPoint.getSignature()+ " Method successfully processed with the status " +
                retVal.toString());
    }
}
