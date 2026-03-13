package ru.job4j.aop;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
@Component
public class ExceptionHandlingAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlingAspect.class);

    @AfterThrowing(pointcut = "execution(* ru.job4j..*.*(..))", throwing = "ex")
    public void handleException(Exception ex) {
        LOGGER.error("An error occurred: {}", ex.getMessage());
    }
}