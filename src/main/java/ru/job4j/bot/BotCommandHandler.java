package ru.job4j.bot;

import org.springframework.stereotype.Service;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.Optional;

import ru.job4j.model.Content;
import ru.job4j.infastructur.LifecycleComponent;
import ru.job4j.model.User;
import ru.job4j.repository.UserRepository;
import ru.job4j.services.MoodService;

@Service
public class BotCommandHandler extends LifecycleComponent {
    private final UserRepository userRepository;
    private final MoodService moodService;
    private final TgUI tgUI;

    public BotCommandHandler(UserRepository userRepository,
                             MoodService moodService,
                             TgUI tgUI) {
        this.userRepository = userRepository;
        this.moodService = moodService;
        this.tgUI = tgUI;
    }

    public Optional<Content> commands(Message message) {
        var text = message.getText();
        var chatId = message.getChatId();
        var clientId = message.getFrom().getId();
        return switch (text) {
            case "/start" -> handleStartCommand(chatId, clientId);
            case "/week_mood_log" -> moodService.weekMoodLogCommand(chatId, clientId);
            case "/month_mood_log" -> moodService.monthMoodLogCommand(chatId, clientId);
            case "/award" -> moodService.awards(chatId, clientId);
            default -> Optional.empty();
        };
    }

    public Optional<Content> handleCallback(CallbackQuery callback) {
        var moodId = Long.valueOf(callback.getData());
        var user = userRepository.findByClientId(callback.getFrom().getId());
        return user.map(value -> moodService.chooseMood(value, moodId));
    }

    private Optional<Content> handleStartCommand(long chatId, Long clientId) {
        var user = new User(chatId, clientId);
        userRepository.save(user);
        var content = new Content(user.getChatId());
        content.setText("Как настроение?");
        content.setMarkup(tgUI.buildButtons());
        return Optional.of(content);
    }
}
