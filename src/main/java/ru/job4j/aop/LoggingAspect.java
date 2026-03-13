package ru.job4j.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import org.aspectj.lang.JoinPoint;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {
    @Before("execute(ru.job4j.service..*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        System.out.printf("Вызов метода: %s c аргументами %s%n",
                joinPoint.getSignature().getName(),
                Arrays.toString(joinPoint.getArgs()));
    }
}