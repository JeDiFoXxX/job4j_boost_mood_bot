package ru.job4j.infastructur;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.job4j.content.SentContent;

public interface TelegramBotHandler extends SentContent {
    void init(TelegramBotsApi botsApi) throws TelegramApiException;
}
