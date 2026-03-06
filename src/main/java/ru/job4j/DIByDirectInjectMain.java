package ru.job4j;

import ru.job4j.bot.BotCommandHandler;
import ru.job4j.bot.TelegramBotService;
import ru.job4j.model.Content;

public class DIByDirectInjectMain {
    public static void main(String[] args) {
        var handler = new BotCommandHandler();
        var tg = new TelegramBotService(handler);
        tg.receive(new Content(12345L));
    }
}
