package ru.job4j.bot;

import org.springframework.stereotype.Service;

import ru.job4j.content.Content;
import ru.job4j.infastructur.LifecycleComponent;

@Service
public class BotCommandHandler extends LifecycleComponent {
    void receive(Content content) {
        System.out.println(content);
    }
}
