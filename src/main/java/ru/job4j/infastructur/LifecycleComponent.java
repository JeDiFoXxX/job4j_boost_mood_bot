package ru.job4j.infastructur;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

public abstract class LifecycleComponent {
    @PostConstruct
    private void initMethod() {
        System.out.printf("Бин %s создан%n", this.getClass().getSimpleName());
    }

    @PreDestroy
    private void destroyMethod() {
        System.out.printf("Бин %s готов к удалению%n", this.getClass().getSimpleName());
    }
}
