package com.LibraryManagement2.ProductionReadyLib.Utilities;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class AuditAspect {

    @AfterReturning(
            value = "execution(* com.LibraryManagement2.ProductionReadyLib.LibraryServiceImpl.BookService.create*(..)) || " +
                    "execution(* com.LibraryManagement2.ProductionReadyLib.LibraryServiceImpl.BookService.borrow*(..))"
    )
    public void audit(JoinPoint joinPoint) {

        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        Object[] args = joinPoint.getArgs();

        log.info(
                "user={} action={} args={}",
                username,
                joinPoint.getSignature().getName(),
                args
        );
    }
}

