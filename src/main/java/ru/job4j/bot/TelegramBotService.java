package ru.job4j.bot;

import org.springframework.stereotype.Service;

import ru.job4j.infastructur.LifecycleComponent;

@Service
public class TelegramBotService extends LifecycleComponent {
    private final BotCommandHandler handler;

    public TelegramBotService(BotCommandHandler handler) {
        this.handler = handler;
    }
}
