package ru.job4j.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component
public class AppConfig {
    @Value("${telegram.bot.name:MyTestName}")
    private String botName;

    public void printConfig() {
        System.out.println(botName);
    }
}
