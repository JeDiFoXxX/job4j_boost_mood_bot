package ru.job4j.infastructur;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

import org.springframework.beans.factory.BeanNameAware;

public abstract class LifecycleComponent implements BeanNameAware {
    private String beanName;

    @Override
    public void setBeanName(String name) {
        this.beanName = name;
    }

    @PostConstruct
    private void initMethod() {
        System.out.printf("Бин %s создан%n", beanName);
    }

    @PreDestroy
    private void destroyMethod() {
        System.out.printf("Бин %s готов к удалению%n", beanName);
    }
}
