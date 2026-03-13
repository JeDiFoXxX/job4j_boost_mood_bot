package ru.job4j.services.test;

import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Update;

import ru.job4j.bot.BotCommandHandler;
import ru.job4j.infastructur.OnFakeCondition;
import ru.job4j.infastructur.TelegramBotHandler;
import ru.job4j.model.Content;

@Service
@Conditional(OnFakeCondition.class)
public class TelegramFakeBotService extends TelegramLongPollingBot implements TelegramBotHandler {
    private final BotCommandHandler handler;
    String botName;

    public TelegramFakeBotService(BotCommandHandler handler) {
        super("TestToken");
        this.handler = handler;
        this.botName = "TestBot";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasCallbackQuery()) {
            handler.handleCallback(update.getCallbackQuery())
                    .ifPresent(this::sent);
        } else if (update.hasMessage() && update.getMessage().getText() != null) {
            handler.commands(update.getMessage())
                    .ifPresent(this::sent);
        }
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public void sent(Content content) {
        System.out.println("ID чата: " + content.getChatId());
        if (content.getAudio() != null) {
            System.out.print("Вывод аудио: " + content.getAudio());
            if (content.getText() != null) {
                System.out.println(" с подписью: " + content.getText());
            }
        }
        if (content.getText() != null && content.getMarkup() != null) {
            System.out.println("Вывод текста: " + content.getText() + " и кнопки: " + content.getMarkup());
        }
        if (content.getText() != null) {
            System.out.println("Вывод текста: " + content.getText());
        }
        if (content.getPhoto() != null) {
            System.out.print("Вывод фото в чат " + content.getPhoto());
            if (content.getText() != null) {
                System.out.println(" с подписью: " + content.getText());
            }
        }
    }

    @Override
    public void init(TelegramBotsApi botsApi) {
        System.out.printf("Бот %s успешно зарегистрирован%n", getBotUsername());
    }
}
