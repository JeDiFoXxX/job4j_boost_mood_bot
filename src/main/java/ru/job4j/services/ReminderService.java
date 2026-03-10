package ru.job4j.services;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import ru.job4j.infastructur.LifecycleComponent;
import ru.job4j.model.Content;
import ru.job4j.repository.UserRepository;

@Service
public class ReminderService extends LifecycleComponent {
    private final TelegramBotService telegramBotService;
    private final UserRepository userRepository;

    public ReminderService(TelegramBotService telegramBotService, UserRepository userRepository) {
        this.telegramBotService = telegramBotService;
        this.userRepository = userRepository;
    }

    @Scheduled(fixedRateString = "${remind.period}")
    public void ping() {
        for (var user : userRepository.findAll()) {
            Content content = new Content(user.getChatId());
            content.setText("Ping");
            telegramBotService.sent(content);
        }
    }
}
