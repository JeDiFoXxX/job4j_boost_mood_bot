package ru.job4j.services;

import org.springframework.stereotype.Service;

import ru.job4j.engine.RecommendationEngine;
import ru.job4j.repository.AchievementRepository;
import ru.job4j.repository.MoodLogRepository;
import ru.job4j.repository.MoodRepository;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import ru.job4j.infastructur.LifecycleComponent;
import ru.job4j.model.*;

@Service
public class MoodService extends LifecycleComponent {
    private final MoodLogRepository moodLogRepository;
    private final RecommendationEngine recommendationEngine;
    private final AchievementRepository achievementRepository;
    private final MoodRepository moodRepository;
    private final DateTimeFormatter formatter = DateTimeFormatter
            .ofPattern("dd-MM-yyyy HH:mm")
            .withZone(ZoneId.systemDefault());

    public MoodService(MoodLogRepository moodLogRepository,
                       RecommendationEngine recommendationEngine,
                       AchievementRepository achievementRepository,
                       MoodRepository moodRepository) {
        this.moodLogRepository = moodLogRepository;
        this.recommendationEngine = recommendationEngine;
        this.achievementRepository = achievementRepository;
        this.moodRepository = moodRepository;
    }

    public Content chooseMood(User user, Long moodId) {
        moodRepository.findById(moodId).ifPresent(mood ->
                moodLogRepository.save(new MoodLog(
                        user,
                        mood,
                        System.currentTimeMillis() / 1000L
                )));
        return recommendationEngine.recommendFor(user.getChatId(), moodId);
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
