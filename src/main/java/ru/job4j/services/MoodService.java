package ru.job4j.services;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import ru.job4j.engine.RecommendationEngine;
import ru.job4j.infastructur.TimeProvider;
import ru.job4j.repository.AchievementRepository;
import ru.job4j.repository.MoodLogRepository;
import ru.job4j.repository.MoodRepository;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import ru.job4j.infastructur.LifecycleComponent;
import ru.job4j.model.event.UserEvent;
import ru.job4j.model.*;

@Service
public class MoodService extends LifecycleComponent implements TimeProvider {
    private final MoodLogRepository moodLogRepository;
    private final RecommendationEngine recommendationEngine;
    private final AchievementRepository achievementRepository;
    private final MoodRepository moodRepository;
    private final ApplicationEventPublisher publisher;
    private final DateTimeFormatter formatter = DateTimeFormatter
            .ofPattern("dd-MM-yyyy HH:mm")
            .withZone(ZoneId.systemDefault());

    public MoodService(MoodLogRepository moodLogRepository,
                       RecommendationEngine recommendationEngine,
                       AchievementRepository achievementRepository,
                       ApplicationEventPublisher publisher,
                       MoodRepository moodRepository) {
        this.moodLogRepository = moodLogRepository;
        this.recommendationEngine = recommendationEngine;
        this.achievementRepository = achievementRepository;
        this.publisher = publisher;
        this.moodRepository = moodRepository;
    }

    public Content chooseMood(User user, Long moodId) {
        final Content[] contents = new Content[1];
        var startOfDay = startOfDay();
        var endOfDay = endOfDay();
        moodRepository.findById(moodId).ifPresent(mood -> {
            moodLogRepository.findByUserIdAndCreatedAtBetween(user.getId(), startOfDay, endOfDay)
                    .ifPresentOrElse(
                            log -> {
                                Content content = new Content(user.getChatId());
                                content.setText("Сегодня вы уже выбирали настроение");
                                contents[0] = content;
                            },
                            () -> {
                                moodLogRepository.save(new MoodLog(user, mood, timeNow()));
                                publisher.publishEvent(new UserEvent(this, user));
                                contents[0] = recommendationEngine.recommendFor(user.getChatId(), moodId);
                            }
                    );
        });
        return contents[0];
    }

    public Optional<Content> weekMoodLogCommand(long chatId, Long clientId) {
        var content = new Content(chatId);
        var interimWeek = System.currentTimeMillis() / 1000L - (7 * 24 * 3600L);
        List<MoodLog> logs = moodLogRepository.findAllByUserClientIdAndCreatedAtGreaterThan(
                clientId,
                interimWeek
        );
        content.setText(formatMoodLogs(logs, "Лог настроений за прошедшую неделю"));
        return Optional.of(content);
    }

    public Optional<Content> monthMoodLogCommand(long chatId, Long clientId) {
        var content = new Content(chatId);
        var interimMonth = System.currentTimeMillis() / 1000L - (30 * 24 * 3600L);
        List<MoodLog> logs = moodLogRepository.findAllByUserClientIdAndCreatedAtGreaterThan(
                clientId,
                interimMonth
        );
        content.setText(formatMoodLogs(logs, "Лог настроений за прошедший месяц"));
        return Optional.of(content);
    }

    public Optional<Content> awards(long chatId, Long clientId) {
        var content = new Content(chatId);
        List<Award> awards = achievementRepository.findAllByUserClientId(clientId);
        if (!awards.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            for (Award award : awards) {
                builder.append(award.getTitle())
                        .append("\n")
                        .append(award.getDescription())
                        .append("\n")
                        .append("-------------")
                        .append("\n");
            }
            content.setText(builder.toString());
        } else {
            content.setText("У вас пока что нет наград");
        }
        return Optional.of(content);
    }

    private String formatMoodLogs(List<MoodLog> logs, String title) {
        if (logs.isEmpty()) {
            return title + ":\nNo mood logs found.";
        }
        var sb = new StringBuilder(title + ":\n");
        logs.forEach(log -> {
            String formattedDate = formatter.format(Instant.ofEpochSecond(log.getCreatedAt()));
            sb.append(formattedDate).append(": ").append(log.getMood().getText()).append("\n");
        });
        return sb.toString();
    }
}
