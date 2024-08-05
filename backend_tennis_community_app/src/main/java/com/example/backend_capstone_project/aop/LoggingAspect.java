package com.example.backend_capstone_project.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggingAspect {
    // Applies aspect to controller package
    @Pointcut("execution(* com.example.backend_capstone_project.controller..*.*(..))")
    public void controllerMethods() {}

    // Applies aspect to service package
    @Pointcut("execution(* com.example.backend_capstone_project.service..*.*(..))")
    public void serviceMethods() {}

    @Around("controllerMethods() || serviceMethods()")
    public Object logAroundControllerAndServiceMethods(ProceedingJoinPoint joinPoint) throws Throwable { // aspect applied at joinpoint
        // == BEFORE METHOD LOGGING ==
        long startTime = System.currentTimeMillis(); // setting the current time to a variable
        log.info("Before method: " + joinPoint.getSignature().toShortString() + "; Trigger time: " + startTime);

        // Proceed with the original method execution
        Object result = joinPoint.proceed();

        // == AFTER METHOD LOGGING ==
        long endTime = System.currentTimeMillis(); // sets time after method runs to variable
        log.info("After method: " + joinPoint.getSignature().toShortString() + "; Trigger time: " + endTime);

        // == METHOD EXECUTION TIME ==
        log.info("Method execution time: " + (endTime - startTime) + " milliseconds");

        return result;
    }

    @AfterReturning(pointcut = "controllerMethods() || serviceMethods()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        log.info("Method " + joinPoint.getSignature().toShortString() + " returned with value: " + result);
    }

    @AfterThrowing(pointcut = "controllerMethods() || serviceMethods()", throwing = "error")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
        log.info("Method " + joinPoint.getSignature().toShortString() + " threw an exception: " + error);
    }

}
