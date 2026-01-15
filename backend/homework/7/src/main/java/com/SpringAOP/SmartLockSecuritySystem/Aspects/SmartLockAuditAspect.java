package com.SpringAOP.SmartLockSecuritySystem.Aspects;

import com.SpringAOP.SmartLockSecuritySystem.Exception.HardwareFailureException;
import com.SpringAOP.SmartLockSecuritySystem.Exception.UnauthorizedAccessException;
import com.SpringAOP.SmartLockSecuritySystem.Utilities.AppConstants;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class SmartLockAuditAspect {

    private final Logger logger = LoggerFactory.getLogger(SmartLockAuditAspect.class);

    @Around(AppConstants.secureUnlockPath)
    public Object secureUnlock(ProceedingJoinPoint joinPoint) throws Throwable {

        String user = (String) joinPoint.getArgs()[0];

        if(user == null || user.isEmpty()){
            logger.error(AppConstants.hardwareAlert);
            throw new HardwareFailureException(AppConstants.hardwareFailureException);
        }

        if (AppConstants.unknown.equalsIgnoreCase(user)) {
            logger.error(AppConstants.securityAlert);
            throw new UnauthorizedAccessException(AppConstants.unauthorizedAccessException);
        }

        logger.info(AppConstants.accessAttempt);

        //  Allowed user
        Object result = joinPoint.proceed();

        logger.info(AppConstants.success);

        return result;
    }

    @Around(AppConstants.measureBatteryCheckTime)
    public Object measureBatteryCheckTime(ProceedingJoinPoint joinPoint) throws Throwable {

        long startTime = System.currentTimeMillis();

        Object result = joinPoint.proceed(); // method executes here

        long endTime = System.currentTimeMillis();
        logger.info("Battery check took {} ms", endTime - startTime);

        return result;
    }


    @AfterThrowing(
            pointcut = AppConstants.triggerAlarm,
            throwing = "exception"
    )
    public void triggerAlarm(Exception exception) {

        logger.warn(
                "ALARM TRIGGERED: System error detected: " + exception.getMessage()
        );

        callEmergencyService();
    }

    private void callEmergencyService() {
        logger.info("Emergency Service Notified!");
    }

}