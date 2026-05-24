package com.prancibot.chatserver.monitoring.aspect;

import com.prancibot.common.logging.AppLogger;
import com.prancibot.common.monitoring.annotation.Timed;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExecutionTimeAspect {
    private final AppLogger logger = AppLogger.getLogger(getClass());

    @Around("@annotation(com.prancibot.common.monitoring.annotation.Timed)")
    public Object measureExecutionTime(
            ProceedingJoinPoint joinPoint
    ) throws Throwable {
        long start = System.currentTimeMillis();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Timed timed = signature.getMethod().getAnnotation(Timed.class);
        try {
            return joinPoint.proceed();
        } finally {
            long durationMs =
                    System.currentTimeMillis() - start;
            String message = String.format(
                    "%s.%s executed in %d ms",
                    signature.getDeclaringType().getSimpleName(), signature.getName(),
                    durationMs
            );
            logger.log(timed.level(), message);
        }
    }
}
