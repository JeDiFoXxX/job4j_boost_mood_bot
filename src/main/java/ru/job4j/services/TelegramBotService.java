package ru.job4j.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendAudio;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import ru.job4j.bot.BotCommandHandler;
import ru.job4j.exception.SentContentException;
import ru.job4j.infastructur.OnRealCondition;
import ru.job4j.infastructur.TelegramBotHandler;
import ru.job4j.model.Content;

@Service
@Conditional(OnRealCondition.class)
public class TelegramBotService extends TelegramLongPollingBot implements TelegramBotHandler {
    private final BotCommandHandler handler;
    private final String botName;

    public TelegramBotService(@Value("${telegram.bot.name}") String botName,
                              @Value("${telegram.bot.token}") String botToken,
                              BotCommandHandler handler) {
        super(botToken);
        this.handler = handler;
        this.botName = botName;
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
        try {
            String chatId = content.getChatId().toString();
            if (content.getAudio() != null) {
                SendAudio sendAudio = new SendAudio();
                sendAudio.setChatId(chatId);
                sendAudio.setAudio(content.getAudio());
                if (content.getText() != null) {
                    sendAudio.setCaption(content.getText());
                }
                execute(sendAudio);
            }
            if (content.getText() != null && content.getMarkup() != null) {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(chatId);
                sendMessage.setText(content.getText());
                sendMessage.setReplyMarkup(content.getMarkup());
                execute(sendMessage);
            }
            if (content.getText() != null) {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(chatId);
                sendMessage.setText(content.getText());
                execute(sendMessage);
            }
            if (content.getPhoto() != null) {
                SendPhoto sendPhoto = new SendPhoto();
                sendPhoto.setChatId(chatId);
                sendPhoto.setPhoto(content.getPhoto());
                if (content.getText() != null) {
                    sendPhoto.setCaption(content.getText());
                }
                execute(sendPhoto);
            }
        } catch (TelegramApiException e) {
            throw new SentContentException("Ошибка при отправлении", e);
        }
    }

    @Override
    public void init(TelegramBotsApi botsApi) throws TelegramApiException {
        botsApi.registerBot(this);
        System.out.printf("Бот %s успешно зарегистрирован%n", getBotUsername());
    }
}
