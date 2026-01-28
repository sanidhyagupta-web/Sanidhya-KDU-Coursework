package com.LibraryManagement2.ProductionReadyLib.Utilities;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class ExecutionTimeAspect {
    @Around("execution(* com.LibraryManagement2.ProductionReadyLib.LibraryServiceImpl..*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable{
        long start = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long duration = System.currentTimeMillis() - start;

        log.info("Method {} took {} ms",
                joinPoint.getSignature().toShortString(),
                duration
        );

        return result;
    }
}
