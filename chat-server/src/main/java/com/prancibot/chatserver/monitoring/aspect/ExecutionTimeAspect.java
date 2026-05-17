package com.prancibot.chatserver.monitoring.aspect;

import com.prancibot.common.logging.AppLogger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExecutionTimeAspect {
    private final AppLogger logger = AppLogger.getLogger(getClass());

    @Around(
            "@annotation(com.prancibot.common.monitoring.annotation.Timed)"
    )
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        try {
            return joinPoint.proceed();
        } finally {
            long durationMs = System.currentTimeMillis() - start;
            logger.info(
                    "{}.{} executed in {} ms",
                    joinPoint.getSignature().getDeclaringType(), joinPoint.getSignature().getName(),
                    durationMs
            );
        }
    }
}
